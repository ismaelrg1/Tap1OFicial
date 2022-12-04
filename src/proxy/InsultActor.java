package proxy;

import helloWorld.*;
import actor.*;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class InsultActor implements ActorInterface {

    private List<MessageInterface> insultQueue;
    private LinkedBlockingQueue<MessageInterface> msgQueue;



    @Override
    public void send(MessageInterface msg) {

    }

    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return null;
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
}
