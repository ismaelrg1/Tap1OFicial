package decorator;

import actor.ActorContext;
import actor.ActorInterface;
import actor.MessageInterface;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public class ActorDecorator implements ActorInterface {
    ActorInterface actor;

    public ActorDecorator(ActorInterface actor){
        this.actor = actor;
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
        return actor.getQueue();
    }

    @Override
    public MessageInterface receive() throws NoSuchElementException {
        return actor.receive();
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
    public void run() {
        try {
            while (true) {
                MessageInterface msg = actor.getMsgQueue().take();
                process(msg);
                //System.out.println("I have processed");
            }
        }catch (InternalError e) {
            //notifyChange("d");
            //System.out.println("I died well");
        }catch (InterruptedException e){
            //notifyChange("f");
            //System.out.println("I died wrong");
        }
        //ActorContext.getInstance().getRegistry().remove(name);
    }


}
