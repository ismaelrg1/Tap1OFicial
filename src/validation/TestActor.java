package validation;
import actor.ActorContext;
import actor.MessageInterface;
import helloWorld.ActorProxy;
import helloWorld.QuitMessage;
import helloWorld.RingActor;
import org.junit.Assert;
import org.junit.Test;
import proxy.ActorProxy2;
import proxy.InsultActor;

import java.awt.image.LookupOp;

public class TestActor {

    /**
     * Create 100 RingActors and make a Circular Linked List with them.
     * Then calculate how long it takes for a message to go around 100 times
     */
    @Test
    public void testTime(){

        ActorProxy actor2 = null;
        ActorProxy actorAux = ActorContext.spawnActor("0",new RingActor());
        ActorProxy primerActor = actorAux;
        //create 100 RingActor
        for (Integer i = 1; i < 100; i++){
            actor2 = ActorContext.spawnActor(i.toString(),new RingActor());
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
        msg10.setROUND(100);
        msg10.setINITIAL(primerActor.getActor());
        primerActor.send(msg10);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Two actors send 100 messages to each other
     */
    @Test
    public void pingPong(){
        int initialActors=ActorContext.getInstance().getNames().split(" ").length;

        ActorProxy ping = ActorContext.spawnActor("ping",new InsultActor());
        ActorProxy pong = ActorContext.spawnActor("pong",new InsultActor());

        int finalActors=ActorContext.getInstance().getNames().split(" ").length;

        ping.send(new PingPongMessage(pong,100));

        Assert.assertTrue(initialActors!=finalActors);

    }


}
