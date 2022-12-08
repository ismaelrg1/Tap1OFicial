package decorator;

import actor.*;
<<<<<<< HEAD

import java.util.concurrent.LinkedBlockingQueue;

public class FireWallDecorator implements ActorInterface {
=======
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

>>>>>>> Ismael
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
