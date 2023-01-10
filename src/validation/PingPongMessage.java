package validation;

import actor.ActorInterface;
import actor.MessageInterface;

public class PingPongMessage implements MessageInterface {

    private ActorInterface actor;
    private int round;

    public PingPongMessage(ActorInterface actor,int round){
        this.actor = actor;
        this.round=round;
    }
    @Override
    public String getMsg() {
        return Integer.toString(this.round);
    }

    @Override
    public void setMsg(String msg) {
        this.round = Integer.parseInt(msg);
    }

    @Override
    public ActorInterface getActor() {
        return actor;
    }
}
