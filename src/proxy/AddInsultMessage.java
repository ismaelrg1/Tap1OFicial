package proxy;
import actor.*;
import helloWorld.*;
public class AddInsultMessage implements MessageInterface {

    private ActorInterface actor;
    private String msg;

    public AddInsultMessage(ActorInterface actor, String msg) {
        this.actor = actor;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public ActorInterface getActor() {
        return this.actor;
    }
}
