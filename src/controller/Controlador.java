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
import proxy.AddInsultMessage;
import proxy.GetAllInsultsMessage;
import proxy.GetInsultMessage;
import proxy.InsultActor;
import validation.LoopMessage;
import validation.RingMessage;


public class Controlador {
    private static int ACTOR_NUM = 0;
    public Controlador(){};

    //////Crea los metodos que te salgan de los huevos que hagan las creaciones de actore

    public void createActor(String actorType,String decoratorType){
        if(actorType.equals("Ring Actor")){
           testDecorator(decoratorType);
        }else if(actorType.equals("Insult Actor")){
            if(decoratorType.equals("No Decorator")){
                ACTOR_NUM++;
                ActorContext.spawnActor("Insult Actor ("+String.valueOf(ACTOR_NUM)+")",new InsultActor());
            }
        }
    }

    private void testDecorator(String decoratorType){
        if(decoratorType.equals("EncryptDecorator")){
            ACTOR_NUM++;
            ActorContext.spawnActor("Ring Actor ("+String.valueOf(ACTOR_NUM)+") Encrypted",new EncryptionDecorator(new RingActor()));
        }else if(decoratorType.equals("LambdaDecorator")){
            ACTOR_NUM++;
            ActorInterface a = ActorContext.spawnActor("Ring Actor ("+String.valueOf(ACTOR_NUM)+") Lambda",new LambdaFirewallDecorator(new RingActor()));
            // Create Closures
            a.send(new AddClosureMessage((String p) -> p.startsWith("i")));
            a.send(new AddClosureMessage((String p) -> p.startsWith("m")));
        }else if(decoratorType.equals("FirewallDecorator")){
            ACTOR_NUM++;
            ActorContext.spawnActor("Ring Actor ("+String.valueOf(ACTOR_NUM)+") Firewall",new FireWallDecorator(new RingActor()));
        }else{
            ACTOR_NUM++;
            ActorContext.spawnActor("Ring Actor ("+String.valueOf(ACTOR_NUM)+")",new  RingActor());
        }
    }

    public void sendMessage(String receiver, String typeMsg, String msg){
        ActorInterface actorAux = ActorContext.getInstance().lookup(receiver);
        actorAux.send(createMsg(actorAux,typeMsg,msg));
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
        }
        else{
            return null;
        }
    }


    public String[] getAllActors(){
        return ActorContext.getInstance().getNames().split(", ");
    }

    public void ringTest(String r){
        String[] aux = r.split("/");

        ActorProxy actor2 = null;
        ActorProxy actorAux = ActorContext.spawnActor("Ring Actor (0) RingMode",new RingActor());
        ActorProxy primerActor = actorAux;
        //create 100 RingActor
        for (Integer i = 1; i < Integer.parseInt(aux[1]); i++){
            actor2 = ActorContext.spawnActor("Ring Actor ("+i.toString()+") RingMode",new RingActor());
            actorAux.send(new RingMessage(i.toString(),actor2));
            actorAux=actor2;
        }
        actor2.send(new RingMessage("0", primerActor));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Send a 10 Loop Message "Hola!"
        LoopMessage msg10 = new LoopMessage("0", null);
        msg10.setROUND(Integer.parseInt(aux[1]));
        msg10.setINITIAL(primerActor.getActor());
        primerActor.send(msg10);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
