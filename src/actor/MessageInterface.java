package actor;

import actor.ActorInterface;

public interface MessageInterface{

    /**
     * Get String message field of the MessageInterface
     * @return String message
     */
    public String getMsg();

    /**
     * Set String message field of the MessageInterface
     * @param msg
     */
    public void setMsg(String msg);

    /**
     * Get the Actor (sender) of the message
     * @return ActorInterface actor
     */
    public ActorInterface getActor();


}