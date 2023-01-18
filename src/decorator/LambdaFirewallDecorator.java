package decorator;

import actor.ActorInterface;
import actor.MessageInterface;
import helloWorld.Message;
import helloWorld.RingActor;

import java.util.LinkedList;
import java.util.function.Predicate;

public class LambdaFirewallDecorator extends ActorDecorator {
    private LinkedList<Predicate<String>> filter;


    public LambdaFirewallDecorator(ActorInterface actor){
        super(actor);
        filter = new LinkedList<>();
    }

    /**
     * If the message is an instance of AddClosureMessage, it adds the Predicated to the LambdaFirewallDecorator
     * Predicated list.
     * If the message is not an instance of AddClosureMessage, it runs the message through all the Predicated List
     * Closures. If the message passes the tests, it will be sent.
     * @param msg
     */
    @Override
    public void send(MessageInterface msg) {
        if(msg instanceof AddClosureMessage)
            filter.add(((AddClosureMessage) msg).getP());
        else if (msg instanceof Message) {
            boolean closureTest = true;
            String name = msg.getMsg();
            for (Predicate<String> p : filter) {
                if (p.test(name)) {
                    closureTest = false;
                    System.out.println("The Message "+ msg.getMsg()+" I have not passed the Closure");
                    break;
                }
            }
            if (closureTest) {
                System.out.println("The Message "+ msg.getMsg()+" I have passed the Closures");
                super.send(msg);
            }

        }else{
            super.send(msg);
        }
    }

    public String toString(){
        return actor.toString();
    }

}
