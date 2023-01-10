package decorator;

import actor.MessageInterface;
import helloWorld.RingActor;

import java.util.LinkedList;
import java.util.function.Predicate;

public class LambdaFirewallDecorator extends RingActor {
    private RingActor actor;      // actor that will be decorated
    private LinkedList<Predicate<String>> filter;


    public LambdaFirewallDecorator(RingActor a){
        super();
        filter = new LinkedList<>();
        actor = a;
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
        else {
            boolean closureTest = true;
            String name = msg.getMsg();
            for (Predicate<String> p : filter) {
                if (p.test(name)) {
                    closureTest = false;
                    System.out.println("I have not passed the Closure");
                    break;
                }
            }
            if (closureTest) {
                super.send(msg);
            }
        }
    }

}
