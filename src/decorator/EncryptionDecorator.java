package decorator;

import actor.MessageInterface;
import helloWorld.RingActor;

public class EncryptionDecorator extends RingActor {

    private RingActor actor;

    public EncryptionDecorator(RingActor actor){
        super();
        this.actor=actor;
    }

    @Override
    public void send(MessageInterface msg) {
        this.encrypt(msg);
        super.send(msg);
    }

    private void encrypt(MessageInterface msg){
        String original = msg.getMsg();

        if (original == null){
            original="";
        }

        String encrypted = "";
        for (int i = 0; i < original.length(); i++){
             int letterAscii = (int)original.charAt(i);
             encrypted = encrypted + (char)(letterAscii+1);
        }

        msg.setMsg(encrypted);
    }
}
