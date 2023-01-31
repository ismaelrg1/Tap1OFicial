package monitor;

import actor.ActorContext;
import actor.ActorInterface;
import actor.MessageInterface;

import java.util.*;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonitorService extends Notify implements Subject {

    @Override
    public void subscribe(String name) {
        System.out.println("Subscribing");
        ActorListener aL = new ActorListener(name);
        ActorContext.getInstance().getRegistry().get(name).addListener(aL);
        aList.put(name,aL);
    }


    @Override
    public void unSubscribe(String name) {
        ActorContext.getInstance().getRegistry().get(name).send(new KillListenerMessage());
        aList.remove(name);
    }

    @Override
    public void subscribeAll(){
        HashMap<String, ActorInterface> registry = ActorContext.getInstance().getRegistry();
        ActorListener aL = null;
        for(ActorInterface a: registry.values()){
            if(aList.get(a.getName())==null) {
                aL = new ActorListener(a.getName());
                a.addListener(aL);
                aList.put(a.getName(), aL);
            }
        }
    }

    public void subscribeAllListener(){
        aList.forEach((k,v)->{if(((ActorListener)v).getName()==null) {((ActorListener)aList.get(k)).subscribe(k);}});
        //if(((ActorListener)o).getName()==null) ((ActorListener)aList.get(k)).subscribe(k);
    }

    public void subcribeListener(String name){
        ((ActorListener)aList.get(name)).subscribe(name);
    }

    public Map<String, List<String>> getTraffic(){
        List<String> low = aList.values().stream().filter(v -> v.getNumMessages()<5).map(Observer::getName).collect(Collectors.toList());
        List<String> medium = aList.values().stream().filter(v -> v.getNumMessages()>=5 && v.getNumMessages()<15).map(Observer::getName).collect(Collectors.toList());
        List<String> high = aList.values().stream().filter(v -> v.getNumMessages()>=15).map(Observer::getName).collect(Collectors.toList());

        Map<String, List<String>> result = new HashMap<>();

        result.put("LOW",low);
        result.put("MEDIUM",medium);
        result.put("HIGH",high);
        return result;
    }

    public Map<Events,Integer> getEvents(){
        int created=0;
        int stopped=0;
        int error=0;
        int msg=0;
        Map<Events,Integer> result = new HashMap<>();

        created = aList.values().stream().collect(Collectors.summingInt( o->((ActorListener)o).getEventsLog().get(Events.CREATED)));
        stopped = aList.values().stream().collect(Collectors.summingInt(o->((ActorListener)o).getEventsLog().get(Events.STOPPED)));
        error = aList.values().stream().collect(Collectors.summingInt(o->((ActorListener)o).getEventsLog().get(Events.ERROR)));
        msg = aList.values().stream().collect(Collectors.summingInt(o->((ActorListener)o).getEventsLog().get(Events.MESSAGE)));

        result.put(Events.CREATED, created);
        result.put(Events.STOPPED, stopped);
        result.put(Events.ERROR, error);
        result.put(Events.MESSAGE, msg);
        return result;
    }

    public Map<String, List<MessageInterface>> getSentMessages(){
        Map<String, List<MessageInterface>> result = new HashMap<>();
        for(Observer o : aList.values()){
            for(String o2 : ((ActorListener)o).getSenders().keySet()){
                if(result.get(o2)!=null){
                    ((ActorListener)o).getSenders().get(o2).forEach(x-> result.get(o2).add(x));
                }else{
                    List<MessageInterface> aux = new LinkedList<>();
                    (((ActorListener)o).getSenders().get(o2)).forEach(x->aux.add(x));
                    result.put(o2, aux);
                }
            }
        }
        return result;
    }

    public HashMap<String, List<MessageInterface>> getReceivedMessages(){
        HashMap<String, List<MessageInterface>> messagesReceived = new HashMap<>();
        for (Observer o: aList.values()){
            messagesReceived.put(o.getName(),((ActorListener)o).getMessagesReceived());
        }
        return messagesReceived;
    }


}
