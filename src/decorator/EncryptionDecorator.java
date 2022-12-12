package decorator;

import actor.MessageInterface;
import helloWorld.QuitMessage;
import helloWorld.RingActor;

public class EncryptionDecorator extends RingActor {
    private RingActor actor;      // actor that will be decorated

    public EncryptionDecorator(RingActor a){
        super();
        actor=a;
    }

    @Override
    public void send(MessageInterface msg) {
        encrypt(msg);
        System.out.println("SEstoy en el decorador");
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
        System.out.println("encriptando...");


        msg.setMsg(encrypted);
    }

    private void desencrypt(MessageInterface msg){
        String original = msg.getMsg();

        if (original == null){
            original="";
        }

        String encrypted = "";
        for (int i = 0; i < original.length(); i++){
            int letterAscii = (int)original.charAt(i);
            encrypted = encrypted + (char)(letterAscii-1);
        }
        System.out.println("desecniptando");
        msg.setMsg(encrypted);
    }

    @Override
    public void process(MessageInterface msg){
        desencrypt(msg);
        System.out.println("PEstoy en el decorador");
        super.process(msg);

    }


}
