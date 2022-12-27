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
    /////
    @Override
    public void send(MessageInterface msg) {
        //filter.forEach((p) -> this.send(new MessageInterface(p.test(msg.getMsg()).toString())));
        if(msg instanceof AddClosureMessage)
            filter.add(((AddClosureMessage) msg).getP());
        else {
            boolean closureTest = true;
            String name = msg.getMsg();
            for (Predicate<String> p : filter) {
                if (p.test(name)) {
                    closureTest = false;
                    System.out.println("No he pasado la Clousure");
                    break;
                }
            }
            if (closureTest) {
                super.send(msg);
            }
        }
    }






}
