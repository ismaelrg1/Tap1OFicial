package proxy;

import helloWorld.*;
import actor.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class InsultActor implements ActorInterface {

    private LinkedList<MessageInterface> insultQueue;
    private LinkedBlockingQueue<MessageInterface> msgQueue;

    public InsultActor() {
        String insults []= {"airhead","arsehole","ass-kisser","bastard","bimbo","bugger","chicken","dag","deadbeat","dickhead","donkey","dork","dweeb","flake","floozy","freak","fruitcake","git"};
        insultQueue = new LinkedList<MessageInterface>();
        msgQueue = new LinkedBlockingQueue<MessageInterface>();

        for (int i = 0; i < insults.length; i++) {
            insultQueue.add(new Message(null, insults[i]));
        }




    }

    @Override
    public void send(MessageInterface msg) {
        ActorInterface aux = msg.getActor();    // take the recipient actor of the msg
        aux.getMsgQueue().add(msg);             // Send the msg to this Actor
    }

    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return msgQueue;
    }

    @Override
    public LinkedList<MessageInterface> getQueue() {
        return null;
    }

    @Override
    public void run() {
        while(true){

            //TODO: Crear la funcion Process(Para todos los actores ActorInterface) y Poner todo esto dentro de la funcion
            try {
                // Retrieves and removes the head of this queue, waiting (Block) if necessary until a message becomes available.
                MessageInterface msg = msgQueue.take();
                if (msg instanceof QuitMessage)
                    break;
                else if(msg instanceof AddInsultMessage){
                    insultQueue.add(new Message(null, msg.getMsg()));
                }
                else if(msg instanceof GetInsultMessage) {
                    Random rand = new Random();
                    msg.getActor().getQueue().add(this.insultQueue.get(rand.nextInt(insultQueue.size())));
                }
                else if(msg instanceof GetAllInsultsMessage){
                    for( int i = 0; i < insultQueue.size(); i++){
                        msg.getActor().getQueue().add(this.insultQueue.get(i));
                    }
                }
                else {
                    System.out.println(msg.getMsg());
                }

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        System.out.println("He muerto");
    }
}
