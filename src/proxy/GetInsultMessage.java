package proxy;
import actor.*;
import helloWorld.*;

public class GetInsultMessage implements MessageInterface {

    private ActorInterface actor;

    public GetInsultMessage(ActorInterface actor) {
        this.actor = actor;
    }

    @Override
    public String getMsg() { return null;}

    @Override
    public void setMsg(String msg) {}

    @Override
    public ActorInterface getActor() {
        return this.actor;
    }
}
