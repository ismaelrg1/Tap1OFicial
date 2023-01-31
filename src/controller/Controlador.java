package controller;

import actor.ActorContext;
import actor.ActorInterface;
import actor.MessageInterface;
import decorator.AddClosureMessage;
import decorator.EncryptionDecorator;
import decorator.FireWallDecorator;
import decorator.LambdaFirewallDecorator;
import helloWorld.ActorProxy;
import helloWorld.Message;
import helloWorld.QuitMessage;
import helloWorld.RingActor;
import monitor.Events;
import monitor.MonitorService;
import monitor.Observer;
import proxy.*;
import validation.LoopMessage;
import validation.RingMessage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;


public class Controlador implements Observer {
    private static Controlador instance = new Controlador();
    private static int ACTOR_NUM = 0;
    private static int RING_NUM = 0;
    private MonitorService service = new MonitorService();
    private JTable jTable;
    List<String> listActors = new LinkedList<>();
    private Controlador(){};

    /**
     * Get the Instance of a Singleton class
     * @return Controlador instance
     */
    public static Controlador getInstance(){
        return instance;
    }

    //////Crea los metodos que te salgan de los huevos que hagan las creaciones de actore

    public String createActor(String actorType,String decoratorType){
        if(decoratorType.equals("EncryptDecorator")){
            ACTOR_NUM++;
            ActorContext.spawnActor2(actorType+"("+String.valueOf(ACTOR_NUM)+") Encrypted",new EncryptionDecorator(actorType.startsWith("R")?new RingActor():new InsultActor()));
            listActors.add(actorType+"("+String.valueOf(ACTOR_NUM)+") Encrypted");
            return actorType+"("+String.valueOf(ACTOR_NUM)+") Encrypted";
        }else if(decoratorType.equals("LambdaDecorator")){
            ACTOR_NUM++;
            ActorInterface a = ActorContext.spawnActor2(actorType+"("+String.valueOf(ACTOR_NUM)+") Lambda",new LambdaFirewallDecorator(actorType.startsWith("R")?new RingActor():new InsultActor()));
            // Create Closures
            a.send(new AddClosureMessage((String p) -> p.startsWith("i")));
            a.send(new AddClosureMessage((String p) -> p.startsWith("m")));
            listActors.add(actorType+"("+String.valueOf(ACTOR_NUM)+") Lambda");
            return actorType+"("+String.valueOf(ACTOR_NUM)+") Lambda";
        }else if(decoratorType.equals("FirewallDecorator")){
            ACTOR_NUM++;
            ActorContext.spawnActor2(actorType+"("+String.valueOf(ACTOR_NUM)+") Firewall",new FireWallDecorator(actorType.startsWith("R")?new RingActor():new InsultActor()));
            listActors.add(actorType+"("+String.valueOf(ACTOR_NUM)+") Firewall");
            return actorType+"("+String.valueOf(ACTOR_NUM)+") Firewall";
        }else{
            ACTOR_NUM++;
            ActorContext.spawnActor2(actorType+"("+String.valueOf(ACTOR_NUM)+")", actorType.startsWith("R")?new RingActor():new InsultActor());
            listActors.add(actorType+"("+String.valueOf(ACTOR_NUM)+")");
            return actorType+"("+String.valueOf(ACTOR_NUM)+")";
        }
}


    public void sendMessage(String receiver, String typeMsg, String msg){
        if(typeMsg.equals("QuitMessage")){
            subscribe(receiver);
        }
        ActorInterface actorAux = ActorContext.getInstance().lookup(receiver);
        System.out.println("Actor -> "+actorAux);
        actorAux.send(createMsg(actorAux,typeMsg,msg));
        if(typeMsg.equals("GetInsultMessage")||typeMsg.equals("GetAllInsultMessage")){
            MessageInterface msgImpl = actorAux.receive();
            actorAux.send(new Message(actorAux,msgImpl.getMsg()));
        }
    }

    private MessageInterface createMsg(ActorInterface actorAux, String typeTest, String msg){
        if(typeTest.equals("Message")){
            return new Message(null, msg);
        }else if(typeTest.equals("AddInsultMessage")){
            return new AddInsultMessage(null,msg);
        }else if(typeTest.equals("GetInsultMessage")){
            return new GetInsultMessage(actorAux);
        }else if(typeTest.equals("GetAllInsultMessage")){
            return new GetAllInsultsMessage(actorAux);
        }else if(typeTest.equals("QuitMessage")){
            return new QuitMessage(null);
        } else{
            return null;
        }
    }


    public String[] getAllActors(){
        return ActorContext.getInstance().getNames().split(", ");
    }

    public void ringTest(String r){
        String[] aux = r.split("/");
        ActorProxy actor2 = null;
        ActorProxy actorAux = ActorContext.spawnActor("Ring Actor ("+Integer.toString(RING_NUM)+") RingMode",new RingActor());
        addtoTable(actorAux.getName());
        service.subscribe(actorAux.getName());
        service.subcribeListener(actorAux.getName());
        listActors.add(actorAux.getName());
        ActorProxy primerActor = actorAux;
        //create 100 RingActor
        for (Integer i = 1; i < Integer.parseInt(aux[0]); i++){
            RING_NUM++;
            actor2 = ActorContext.spawnActor("Ring Actor ("+Integer.toString(RING_NUM)+") RingMode",new RingActor());
            actorAux.send(new RingMessage(i.toString(),actor2));
            actorAux=actor2;
            service.subscribe(actor2.getName());
            service.subcribeListener(actor2.getName());
            listActors.add(actor2.getName());
            addtoTable(actor2.getName());
        }
        RING_NUM++;
        actor2.send(new RingMessage("0", primerActor));

        // Send a 10 Loop Message "Hola!"
        LoopMessage msg10 = new LoopMessage("0", primerActor);
        msg10.setROUND(Integer.parseInt(aux[1]));
        msg10.setINITIAL(primerActor.getActor());

        primerActor.send(msg10);

    }
    public void subscribe(String actor){
        System.out.println(actor);
        System.out.println(actor);
        service.subscribe(actor);
        service.subcribeListener(actor);
    }
    public void unSubscribe(String actor){
        service.unSubscribe(actor);
        updateTableUnsub(actor);
    }
    public void subscribeAll(){
        service.subscribeAll();
        service.subscribeAllListener();
    }
    @Override
    public void subjectNotification(Events e) {

    }

    @Override
    public void subjectNotification(Events e, MessageInterface msg) {
        if (e.equals(Events.COUNT)) {
            updateTable(msg);
        }else if(e.equals(Events.KILL)){
            updateKillTable(msg);
        }
    }

    @Override
    public int getNumMessages(){
        return 0;
    }

    public Map<String, List<MessageInterface>> getReceivedMsg(){
        return service.getReceivedMessages();
    }

    public Map<String, List<MessageInterface>> getSentMsg(){
        return service.getSentMessages();
    }

    public Map<String,List<String>> getTraffic(){
        return service.getTraffic();
    }

    public Map<Events,Integer> getEvents(){
        return service.getEvents();
    }
    @Override
    public String getName(){
        return null;
    }
    public void setTableModel(JTable jTable){
        this.jTable=jTable;
    }

    public void addtoTable(String name){
        ((DefaultTableModel) jTable.getModel()).addRow(new Object[]{name,0});
    }

    public void updateTable (MessageInterface msg){
        String[] parts = msg.getMsg().split("-",0);
        System.out.println(parts[1]);
        int row = listActors.indexOf(parts[0]);
        //parts[0] -> actor name
        //parts[1] -> numMessages
        jTable.setValueAt(parts[1],row,1);

    }

    public void updateKillTable (MessageInterface msg){
        int row = listActors.indexOf(msg.getMsg());
        ((DefaultTableModel) jTable.getModel()).removeRow(row);
        listActors.remove(row);
    }

    public void updateTableUnsub (String msg){
        int row = listActors.indexOf(msg);
        jTable.setValueAt(0,row,1);
    }

}
