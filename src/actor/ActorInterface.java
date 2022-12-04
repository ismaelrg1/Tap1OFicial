package actor;

import java.util.concurrent.LinkedBlockingQueue;

public interface ActorInterface extends Runnable {
    
    public void send(MessageInterface msg);

    public LinkedBlockingQueue<MessageInterface> getMsgQueue();


}
