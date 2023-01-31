package monitor;

import actor.MessageInterface;

import javax.sound.midi.SysexMessage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class Notify {
    protected Map<String, Observer> aList=new HashMap<>();


    public void notifyChange(Events e){
        aList.forEach((k,v)->v.subjectNotification(e));
    }

    public void notifyChange(Events e, MessageInterface msg){
        aList.forEach((k,v)->v.subjectNotification(e,msg));
    }
}
