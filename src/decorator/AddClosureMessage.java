package decorator;

import actor.ActorInterface;
import actor.MessageInterface;
import proxy.AddInsultMessage;

import java.util.function.Predicate;

public class AddClosureMessage implements MessageInterface {
    private Predicate<String> p;
    public AddClosureMessage(Predicate<String> p){
        this.p=p;
    }

    /**
     * Get the predicate field of AddClousureMessage
     * @return
     */
    public Predicate<String> getP() {
        return p;
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public ActorInterface getActor() {
        return null;
    }


}
