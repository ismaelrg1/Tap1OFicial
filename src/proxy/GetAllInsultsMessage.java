package proxy;
import actor.*;
import helloWorld.*;
public class GetAllInsultsMessage implements MessageInterface {
    private ActorInterface actor;

    public GetAllInsultsMessage(ActorInterface actor) {
        this.actor = actor;
    }

    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public ActorInterface getActor() {
        return this.actor;
    }
}
