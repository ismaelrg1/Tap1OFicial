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

    /**
     * Calls the encrypt function and then sends the Message
     * @param msg
     */
    @Override
    public void send(MessageInterface msg) {
        encrypt(msg);
        //System.out.println("I'm in the decorator");
        super.send(msg);
    }

    /**
     * Encrypts the message received by parameter by adding 1 to the ascii code
     * @param msg
     */
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
        //System.out.println("encrypting...");


        msg.setMsg(encrypted);
    }

    /**
     * Decrypts the message received by parameter by subtracting 1 from the ascii code
     * @param msg
     */
    private void decrypt(MessageInterface msg){
        String original = msg.getMsg();

        if (original == null){
            original="";
        }

        String encrypted = "";
        for (int i = 0; i < original.length(); i++){
            int letterAscii = (int)original.charAt(i);
            encrypted = encrypted + (char)(letterAscii-1);
        }
        //System.out.println("decrypting");
        msg.setMsg(encrypted);
    }

    /**
     * Calls the Decrypt function and then process the Message
     * @param msg
     */
    @Override
    public void process(MessageInterface msg){
        System.out.println("Mensaje Encriptado: "+msg.getMsg());
        decrypt(msg);
        //System.out.println("I'm in the Decorator");
        super.process(msg);
    }


}
