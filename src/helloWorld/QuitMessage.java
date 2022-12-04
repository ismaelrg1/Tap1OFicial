package helloWorld;
import actor.*;

public class QuitMessage implements MessageInterface {
    private ActorInterface actor;

    public QuitMessage(ActorInterface actor){
        this.actor=actor;
    }
    @Override
    public String getMsg() {
        return null;
    }

    @Override
    public ActorInterface getActor() {
        return actor;
    }
}
