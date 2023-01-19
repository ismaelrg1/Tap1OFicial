package decorator;

import actor.*;
import helloWorld.Message;
import helloWorld.RingActor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class FireWallDecorator extends ActorDecorator {

    private ActorInterface actor;

    public FireWallDecorator(ActorInterface actor) {
        super(actor);
        this.actor = actor;
    }

    /**
     * Get the actor (sender) of the message and find if it is in the ActorContext's Registry
     * If it is, sends the message.
     * If it is not, discards the message
     * @param msg
     */
    @Override
    public void send(MessageInterface msg) {
        if (msg instanceof Message) {
            Boolean good = false;
            Set<String> actors = ActorContext.getInstance().getRegistry().keySet();
            Iterator<String> i = actors.iterator();
            ActorInterface sender = msg.getActor();

            if (sender == null)
                good = true;

            while (i.hasNext() && !good) {
                good = ActorContext.getInstance().getRegistry().get(i.next()).equals(sender);
            }


            if (good)
                actor.send(msg);

            if (sender == null)
                System.out.println("Main Are you good? " + good.toString());
            else
                System.out.println(sender.toString() + " Are you good? " + good.toString());
        }else{
            actor.send(msg);
        }
    }


    public String toString(){
        return actor.toString();
    }


}
