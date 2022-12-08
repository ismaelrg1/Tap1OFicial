package reflectionDinamicProxy;

import actor.*;

<<<<<<< HEAD
=======
import java.util.LinkedList;
import java.util.List;
>>>>>>> Ismael
import java.util.concurrent.LinkedBlockingQueue;

public class DynamicProxy implements ActorInterface {
    @Override
    public void send(MessageInterface msg) {

    }

    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return null;
    }

    @Override
<<<<<<< HEAD
=======
    public LinkedList<MessageInterface> getQueue() {
        return null;
    }

    @Override
>>>>>>> Ismael
    public void run() {

    }
}
