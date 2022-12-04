package decorator;

import actor.*;

import java.util.concurrent.LinkedBlockingQueue;

public class FireWallDecorator implements ActorInterface {
    @Override
    public void send(MessageInterface msg) {

    }

    @Override
    public LinkedBlockingQueue<MessageInterface> getMsgQueue() {
        return null;
    }

    @Override
    public void run() {

    }
}
