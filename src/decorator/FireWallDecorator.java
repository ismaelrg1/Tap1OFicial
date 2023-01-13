package decorator;

import actor.*;
import helloWorld.RingActor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class FireWallDecorator extends RingActor {

    private RingActor actor;

    public FireWallDecorator(RingActor actor) {
        super();
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
        Boolean good = false;
        Set<String> actors = ActorContext.getInstance().getRegistry().keySet();
        Iterator<String> i = actors.iterator();
        ActorInterface actor = msg.getActor();
        while(i.hasNext() && !good){
            good=ActorContext.getInstance().getRegistry().get(i.next()).equals(actor);
        }
        if(good)
            super.send(msg);
        System.out.printf("Are you good? "+good.toString());
    }


    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return null;
    }


    @Override
    public LinkedList<MessageInterface> getQueue() {
        return null;
    }

}
