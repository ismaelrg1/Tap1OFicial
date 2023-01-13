package proxy;

import dynamic.*;
import helloWorld.*;
import actor.*;
import validation.PingPongMessage;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class InsultActor implements ActorInterface, Runnable {

    private String name;
    private LinkedList<MessageInterface> insultQueue;
    private LinkedBlockingQueue<MessageInterface> msgQueue;

    public InsultActor() {
        String insults []= {"airhead","arsehole","ass-kisser","bastard","bimbo","bugger","chicken","dag","deadbeat","dickhead","donkey","dork","dweeb","flake","floozy","freak","fruitcake","git"};
        insultQueue = new LinkedList<MessageInterface>();
        msgQueue = new LinkedBlockingQueue<MessageInterface>();

        for(String s:insults){
            insultQueue.add(new Message(null, s));
        }
    }

    @Override
    public void send(MessageInterface msg) {
        msgQueue.add(msg);
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

    /**
     * It processes the message according to the type of class it is.
     * QuitMessage -> Eliminates the Actor Thread
     * AddInsultMessage -> Adds a new InsultMessage to the ActorInsult insult list
     * GetInsultMessage -> Return (send) a random insult of the ActorInsult insult list
     * GetAllInsultsMessage -> Return a String with all the insults of the ActorInsult insult list
     * PingPongMessage -> Sends "n" time a message to its mate
     */
    private void process() throws InternalError{
        try {
            // Retrieves and removes the head of this queue, waiting (Block) if necessary until a message becomes available.
            MessageInterface msg = msgQueue.take();
            if (msg instanceof QuitMessage) {
                throw new InternalError();
            }
            else if(msg instanceof AddInsultMessage){
                insultQueue.add(new Message(null, msg.getMsg()));
            }
            else if(msg instanceof GetInsultMessage) {
                Random rand = new Random();
                msg.getActor().getQueue().add(this.insultQueue.get(rand.nextInt(insultQueue.size())));
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

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public MessageInterface receive() throws NoSuchElementException{return null;}

    @Override
    public void setName(String s) {
        this.name=s;
    }

    ;

    @Override
    public void run() {
        try {
            while (true) {
                process();
            }
        }catch (InternalError e){
            //System.out.println("I died");
        }
        ActorContext.getInstance().getRegistry().remove(name);
    }

}
