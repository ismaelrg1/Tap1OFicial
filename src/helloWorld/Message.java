package helloWorld;
import actor.*;

public class Message implements MessageInterface {
    
    private String msg;
    private ActorInterface actor;

    public Message(ActorInterface actor, String msg){
        this.actor=actor;
        this.msg=msg;
    }

    
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public ActorInterface getActor() {
        return actor;
    }
    public void setActor(ActorInterface actor) {
        this.actor = actor;
    }


    





}
