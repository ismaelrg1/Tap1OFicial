package proxy;

import helloWorld.*;
import actor.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class InsultActor implements ActorInterface, Runnable {

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

    private void process() throws InternalError{
        try {
            // Retrieves and removes the head of this queue, waiting (Block) if necessary until a message becomes available.
            MessageInterface msg = msgQueue.take();
            if (msg instanceof QuitMessage)
                throw new InternalError();
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
            }
            else {
                System.out.println(msg.getMsg());
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                process();
            }
        }catch (InternalError e){
            System.out.println("He muerto");
        }
    }





}
