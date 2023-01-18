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


    public FireWallDecorator(ActorInterface actor) {
        super(actor);
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
            ActorInterface actor = msg.getActor();

            if (actor == null)
                good = true;

            while (i.hasNext() && !good) {
                good = ActorContext.getInstance().getRegistry().get(i.next()).equals(actor);
            }


            if (good)
                super.send(msg);

            if (actor == null)
                System.out.println("Main Are you good? " + good.toString());
            else
                System.out.println(actor.toString() + " Are you good? " + good.toString());
        }else{
            super.send(msg);
        }
    }


    public String toString(){
        return actor.toString();
    }


}
