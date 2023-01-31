package helloWorld;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;
import actor.*;
//import monitor.ActorListener;
import monitor.*;
import validation.*;


public class RingActor extends Notify implements ActorInterface {

    private String name;
    private LinkedBlockingQueue<MessageInterface> msgQueue;
    private ActorInterface next;


    public RingActor(){
        notifyChange(Events.CREATED);
        this.next = null;
        this.msgQueue = new LinkedBlockingQueue<MessageInterface>();
    }
    public RingActor(String name){
        notifyChange(Events.CREATED);
        this.name = name;
        this.next = null;
        this.msgQueue = new LinkedBlockingQueue<MessageInterface>();
    }


    public void send(MessageInterface msg){
        notifyChange(Events.MESSAGE,msg);
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
        }else if(msg instanceof KillListenerMessage){
            aList.remove(name);
        }
        else if(msg instanceof LoopMessage){
            LoopMessage msgLoop = (LoopMessage) msg;
            if(msgLoop.getROUND()==0){
                System.out.println(msg.getMsg()+" miliseconds");
            }else {
                if (this == msgLoop.getINITIAL()) {
                    msgLoop.setROUND(msgLoop.getROUND() - 1);
                   System.out.println("lap: " + msgLoop.getROUND());
                }
                long end = System.currentTimeMillis();
                long result = (end - start);
                result = Long.parseLong(msg.getMsg())+result;
                msg.setMsg(String.valueOf(result));
                this.next.send(msg);
            }
        }
        System.out.println("Mensaje: "+msg.getMsg());
    }

    @Override
    public void addListener(Observer listener) {
        this.aList.put(name,listener);
        ((ActorListener)listener).setNumMessages(msgQueue.size());
    }


    @Override
    public void run() {
        try {
            while (true) {
                MessageInterface msg = msgQueue.take();
                process(msg);
                //System.out.println("I have processed");
            }
        }catch (InternalError e) {
            notifyChange(Events.STOPPED);
            //System.out.println("I died well");
        }catch (InterruptedException e){
            notifyChange(Events.ERROR);
            //System.out.println("I died wrong");
        }
        ActorContext.getInstance().getRegistry().remove(name);
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

    @Override
    public void setName(String s) {
        this.name=s;
    }

    @Override
    public String getName() {
        return name;
    }



    public String toString(){

        return name;
    }


}