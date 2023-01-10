package validation;

import actor.ActorInterface;
import actor.MessageInterface;

public class LoopMessage implements MessageInterface {
    private static int ROUND;
    private static ActorInterface INITIAL;
    private ActorInterface actor;
    private String msg;

    public LoopMessage(String msg, ActorInterface actor){
        this.actor = actor;
        this.msg=msg;
    }
    @Override
    public String getMsg() { return this.msg;}

    @Override
    public void setMsg(String msg) { this.msg = msg; }

    @Override
    public ActorInterface getActor() {
        return actor;
    }


    /**
     * Get the ROUND of the Loop Message (times it will be sent int the Actor Ring System)
     * @return int ROUND
     */
    public static int getROUND() {
        return ROUND;
    }

    /**
     * Manually set the times the Message will be sent in the Actor Ring System
     * @param ROUND
     */
    public static void setROUND(int ROUND) {
        LoopMessage.ROUND = ROUND;
    }

    /**
     * Manually set the Initial Actor of the Actor Ring System
     * @param INITIAL
     */
    public static void setINITIAL(ActorInterface INITIAL) {
        LoopMessage.INITIAL = INITIAL;
    }

    /**
     * Get the "first" actor of the Actor Ring System
     * @return "first" actor from which we began to count in the Actor Ring System
     */
    public static ActorInterface getINITIAL() {
        return INITIAL;
    }
}
