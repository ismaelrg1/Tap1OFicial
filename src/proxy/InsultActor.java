package proxy;

import helloWorld.*;
import actor.*;
import monitor.Events;
import monitor.KillListenerMessage;
import monitor.Notify;
import monitor.Observer;
import validation.PingPongMessage;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class InsultActor extends Notify implements ActorInterface {

    private String name;
    private LinkedList<MessageInterface> insultQueue;
    private LinkedBlockingQueue<MessageInterface> msgQueue;

    public InsultActor() {
        String insults []= {"airhead","arsehole","ass-kisser","bastard","bimbo","bugger","chicken","dag","deadbeat","dickhead","donkey","dork","dweeb","flake","floozy","freak","fruitcake","git"};
        notifyChange(Events.CREATED);
        insultQueue = new LinkedList<MessageInterface>();
        msgQueue = new LinkedBlockingQueue<MessageInterface>();

        for(String s:insults){
            insultQueue.add(new Message(null, s));
        }
    }
    public InsultActor(String name) {
        String insults []= {"airhead","arsehole","ass-kisser","bastard","bimbo","bugger","chicken","dag","deadbeat","dickhead","donkey","dork","dweeb","flake","floozy","freak","fruitcake","git"};
        notifyChange(Events.CREATED);
        insultQueue = new LinkedList<MessageInterface>();
        msgQueue = new LinkedBlockingQueue<MessageInterface>();
        this.name=name;
        for(String s:insults){
            insultQueue.add(new Message(null, s));
        }
    }

    @Override
    public void send(MessageInterface msg) {
        msgQueue.add(msg);
        notifyChange(Events.MESSAGE,new Message(msg.getActor(), msg.getMsg()));
        //ActorInterface aux = msg.getActor();    // take the recipient actor of the msg
        //aux.getMsgQueue().add(msg);             // Send the msg to this Actor
    }

    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return msgQueue;
    }

    @Override
    public LinkedList<MessageInterface> getQueue() {
        return null;
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

    /**
     * It processes the message according to the type of class it is.
     * QuitMessage -> Eliminates the Actor Thread
     * AddInsultMessage -> Adds a new InsultMessage to the ActorInsult insult list
     * GetInsultMessage -> Return (send) a random insult of the ActorInsult insult list
     * GetAllInsultsMessage -> Return a String with all the insults of the ActorInsult insult list
     * PingPongMessage -> Sends "n" time a message to its mate
     */
    @Override
    public void process(MessageInterface msg) throws InternalError {
        // Retrieves and removes the head of this queue, waiting (Block) if necessary until a message becomes available.
        if (msg instanceof QuitMessage) {
            throw new InternalError();
        }
        else if(msg instanceof AddInsultMessage){
            insultQueue.add(new Message(null, msg.getMsg()));
        }
        else if(msg instanceof GetInsultMessage) {
            Random rand = new Random();
            System.out.println("Queue de "+msg.getActor());
            msg.getActor().getQueue().add(this.insultQueue.get(rand.nextInt(insultQueue.size())));
        }else if(msg instanceof KillListenerMessage){
            aList.remove(name);
        }
        else if(msg instanceof GetAllInsultsMessage){
            // Option 1
            /*String s = "";
            for(MessageInterface m:insultQueue){
                s+=m.getMsg();
            }
            msg.getActor().getQueue().add(new Message(null,s));
            // Option 2
            //insultQueue.forEach((m)->msg.getActor().getQueue().add(m));*/
            // Option 3
            msg.getActor().getQueue().add(new Message(null,insultQueue.toString()));
        } else if (msg instanceof PingPongMessage) {
            int counter = Integer.parseInt(msg.getMsg());

            if (counter--==0){
                return ;
            }
            //System.out.println("I'm in the Actor "+this+" and I'm the number "+counter);
            msg.getActor().send(new PingPongMessage(this, counter));

        } else {
            //System.out.println(msg.getMsg());
        }
        System.out.println("Mensaje: "+msg.getMsg());

    }

    @Override
    public void addListener(Observer listener) {
        this.aList.put(name,listener);
    }


    @Override
    public void run() {
        try {
            while (true) {
                MessageInterface msg = msgQueue.take();
                process(msg);
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

}
