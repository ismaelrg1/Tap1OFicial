package helloWorld;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import actor.*;


public class RingActor implements Runnable, ActorInterface {


    private LinkedBlockingQueue<MessageInterface> msgQueue;
    private RingActor next;

    public RingActor(){
        this.next = null;
        this.msgQueue = new LinkedBlockingQueue<MessageInterface>();
    }

    // Sends messages to the Actor's Queue
    public void send(MessageInterface msg){
        ActorInterface aux = msg.getActor();    // take the recipient actor of the msg
        aux.getMsgQueue().add(msg);             // Send the msg to this Actor
    }

    @Override
    public void run() {
        while(true){
            try {
                // Retrieves and removes the head of this queue, waiting (Block) if necessary until a message becomes available.
                MessageInterface msg = msgQueue.take();
                if (msg instanceof QuitMessage)
                    break;
                System.out.println(msg.getMsg());
                
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        System.out.println("He muerto");
    }

    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return msgQueue;
    }

    @Override
    public LinkedList<MessageInterface> getQueue() {
        return null;
    }


    public RingActor getNext() {
        return next;
    }




}