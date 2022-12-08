package helloWorld;
import actor.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorProxy implements ActorInterface {

    private ActorInterface actor;

    public ActorProxy(ActorInterface actor){
        this.actor=actor;
    }

    public void send (MessageInterface msg){
        actor.send(msg);
        //TODO: submits this message to the queue of the Actor
    }


    @Override
    public void run() {}


    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return actor.getMsgQueue();
    }

    @Override
    public LinkedList<MessageInterface> getQueue() {
        return null;
    }


}
