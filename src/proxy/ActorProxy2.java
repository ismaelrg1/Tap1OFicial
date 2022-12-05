package proxy;

import actor.*;
import helloWorld.Message;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorProxy2 implements ActorInterface {

    private ActorInterface actor;
    private LinkedList<MessageInterface> queue;


    public ActorProxy2(ActorInterface actor){
        this.actor=actor;
        queue = new LinkedList<MessageInterface>();
    }


    @Override
    public void send(MessageInterface msg) {
        actor.send(msg);
    }


    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return actor.getMsgQueue();
    }

    @Override
    public LinkedList<MessageInterface> getQueue() {
        return queue;
    }

    @Override
    public void run() {}

    public MessageInterface recieve() throws NoSuchElementException {
        return this.queue.pop();
    }



}
