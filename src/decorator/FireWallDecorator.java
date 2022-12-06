package decorator;

import actor.*;
import helloWorld.RingActor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class FireWallDecorator extends RingActor {

    RingActor actor;

    public FireWallDecorator(RingActor actor) {
        super();
        this.actor = actor;
    }

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
