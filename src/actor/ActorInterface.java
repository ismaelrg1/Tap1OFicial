package actor;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;

public interface ActorInterface extends Runnable {
    /**
     * Send the message to the actor
     * @param msg
     */
    public void send(MessageInterface msg);

    /**
     * Get the entire list of messages from the Actor
     * @return List of messages
     */
    public LinkedBlockingQueue<MessageInterface> getMsgQueue();

    /**
     * Get the entire list of messages from the Proxy
     * @return List of messages
     */
    public LinkedList<MessageInterface> getQueue();

    /**
     * Get the Message from the Proxy Queue
     * @return Message
     * @throws NoSuchElementException
     */
    public MessageInterface receive() throws NoSuchElementException;


}
