package proxy;

import actor.*;
import monitor.Observer;

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
    public void run() {actor.run();}

    /**
     *
     * @return Get the Message received in the ActorProxy queue
     * @throws NoSuchElementException
     */
    public MessageInterface receive() throws NoSuchElementException {
        //runnable
        while(this.queue.isEmpty()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return this.queue.pop();
    }

    @Override
    public void setName(String s) {
        actor.setName(s);
    }

    @Override
    public String getName() {
        return actor.getName();
    }

    @Override
    public void process(MessageInterface msg) {
        actor.process(msg);
    }

    @Override
    public void addListener(Observer listener) {
        actor.addListener(listener);
    }

    public ActorInterface getActor(){
        return this.actor;
    }

    public String toString(){
        return actor.toString();
    }

}
