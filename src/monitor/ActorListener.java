package monitor;

import actor.ActorInterface;
import actor.MessageInterface;
import controller.Controlador;
import helloWorld.Message;
import helloWorld.QuitMessage;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorListener extends Notify implements Observer, Subject{
    private int numMessages=0;
    private Map<Events,Integer> eventsLog = new HashMap<>();
    private Map<String, List<MessageInterface>> senders = new HashMap<>();
    private List<MessageInterface> messagesReceived = new LinkedList<>();
    private String name;
    List<MessageInterface> list = null;

    public ActorListener(){
        for(Events e:Events.values()){
            eventsLog.put(e,0);
        }
    }

    public ActorListener(String name){
        for(Events e:Events.values()){
            eventsLog.put(e,0);
        }
        this.name=name;
    }
    public void setNumMessages(int i){
        this.numMessages=i;
    }
    @Override
    public void subjectNotification(Events e) {
        if(e.equals(Events.STOPPED)||e.equals(Events.ERROR)){
            eventsLog.merge(e, 1, Integer::sum);
            notifyChange(Events.KILL, new Message(null,name));
        }else{
            eventsLog.merge(e, 1, Integer::sum);
        }
    }

    @Override
    public void subjectNotification(Events e, MessageInterface msg) {
        eventsLog.merge(e, 1, Integer::sum);
        String actor="";
        if(msg.getActor()==null){
            actor = "main";
        }else{
            actor = msg.getActor().getName();
        }
        if(numMessages==0 || senders.get(actor)==null){
            list = new LinkedList<>();
            list.add(msg);
            senders.put(actor,list);

        }else{
            senders.get(actor).add(msg);
        }
        messagesReceived.add(msg);
        numMessages++;
        System.out.println("Tenemos : "+numMessages+" msgs.");
        notifyChange(Events.COUNT, new Message(null,name+"-"+Integer.toString(numMessages)));

    }

    public Map<Events,Integer>getEventsLog(){
        return eventsLog;
    }

    public Map<String, List<MessageInterface>> getSenders(){
        return senders;
    }
    @Override
    public String getName(){
        return name;
    }

    @Override
    public int getNumMessages(){
        return numMessages;
    }

    public List<MessageInterface> getMessagesReceived(){
        return messagesReceived;
    }

    @Override
    public void subscribe(String name) {
        aList.put(name, Controlador.getInstance());
        this.name=name;
    }

    @Override
    public void subscribeAll() {
        //
    }


    @Override
    public void unSubscribe(String name) {
        aList.remove(name);
    }


}
