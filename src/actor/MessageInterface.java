package actor;

import actor.ActorInterface;

public interface MessageInterface{
    
    public String getMsg();

    public void setMsg(String msg);

    public ActorInterface getActor();


}