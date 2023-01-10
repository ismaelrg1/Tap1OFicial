package helloWorld;

import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;
import actor.*;
//import monitor.ActorListener;
import validation.*;


public class RingActor implements Runnable, ActorInterface {


    private LinkedBlockingQueue<MessageInterface> msgQueue;
   // private ActorListener listener;
    private ActorInterface next;

    public RingActor(){
       // listener = null;
       // notifyChange("c");
        this.next = null;
        this.msgQueue = new LinkedBlockingQueue<MessageInterface>();
    }
 /*   public RingActor(ActorListener aL){
        this.listener = aL;
        notifyChange("c");
        this.next = null;
        this.msgQueue = new LinkedBlockingQueue<MessageInterface>();
    }*/

    public void send(MessageInterface msg){
        //notifyChange("r");
        msgQueue.add(msg);
    }

    /**
     * It processes the message according to the type of class it is.
     * QuitMessage -> Eliminates the Actor Thread
     * RingMessage -> Creates a Ring Actor System
     * LoopMessage -> Send "n" time the message to all the Ring Actor system
     *
     * @param msg -> Message received
     */
    public void process(MessageInterface msg){
        long start = System.currentTimeMillis();
        if (msg instanceof QuitMessage){
            throw new InternalError();
        }
        else if(msg instanceof RingMessage){
            this.next=msg.getActor();
        }
        else if(msg instanceof LoopMessage){
            LoopMessage msgLoop = (LoopMessage) msg;
            if(msgLoop.getROUND()==0){
                //System.out.println(msg.getMsg()+" miliseconds");
            }else {
                if (this == msgLoop.getINITIAL()) {
                    msgLoop.setROUND(msgLoop.getROUND() - 1);
                   // System.out.println("lap: " + msgLoop.getROUND());
                }
                long end = System.currentTimeMillis();
                long result = (end - start);
                result = Long.parseLong(msg.getMsg())+result;
                msg.setMsg(String.valueOf(result));

                this.next.send(msg);
            }
        }
        //System.out.println(msg.getMsg());
    }


    @Override
    public void run() {
        try {
            while (true) {
                MessageInterface msg = msgQueue.take();
                this.process(msg);
                //System.out.println("I have processed");
            }
        }catch (InternalError e) {
            //notifyChange("d");
            //System.out.println("I died well");
        }catch (InterruptedException e){
            //notifyChange("f");
            //System.out.println("I died wrong");
        }
    }

    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return msgQueue;
    }

    @Override
    public LinkedList<MessageInterface> getQueue() {
        return null;
    }


    public ActorInterface getNext() {
        return next;
    }

    public MessageInterface receive() throws NoSuchElementException{return null;}

 /*   public void notifyChange(String s){
        if(listener!=null) {
            //listener.noty(s);
            if (s == "r") {
                // sends message received
            }
        }
    }
*/


}