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
    public void setMsg(String msg) { }

    @Override
    public ActorInterface getActor() {
        return actor;
    }

    public String toString(){
        return "QuitMessage";
    }
}
