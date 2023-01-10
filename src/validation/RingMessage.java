package validation;

import actor.ActorInterface;
import actor.MessageInterface;

public class RingMessage implements MessageInterface {

    private ActorInterface actor;
    private String msg;

    public RingMessage(String msg, ActorInterface actor){
        this.actor = actor;
        this.msg=msg;
    }
    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg=msg;
    }

    @Override
    public ActorInterface getActor() {
        return actor;
    }
}
