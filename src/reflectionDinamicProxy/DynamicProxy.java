package reflectionDinamicProxy;

import actor.*;

import java.util.LinkedList;
import java.util.List;
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
    public LinkedList<MessageInterface> getQueue() {
        return null;
    }

    @Override
    public void run() {

    }
}
