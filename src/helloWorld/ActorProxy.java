package helloWorld;
import actor.*;
import monitor.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorProxy implements ActorInterface {


    private ActorInterface actor;

    public ActorProxy(ActorInterface actor){
        this.actor=actor;
    }


    public void send (MessageInterface msg){
        actor.send(msg);
    }

    @Override
    public void run() {actor.run();}


    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return actor.getMsgQueue();
    }

    @Override
    public LinkedList<MessageInterface> getQueue() {
        return null;
    }

    public MessageInterface receive() throws NoSuchElementException {return null;}

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
