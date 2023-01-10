package monitor;

import actor.ActorContext;
import actor.ActorInterface;
import actor.MessageInterface;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class MonitorService {
    LinkedList<Observer> subs;
  //  HashMap<String,LinkedList<ActorListener>> traffic;

  /*  public HashMap<String,LinkedList<ActorListener>> getTraffic(){
        return traffic;
    }*/

    public int getNumberofMessaged(ActorInterface actor){

        return 0;
    }

    public void logAllMessages(LinkedList<ActorInterface>list){

    }

    public void logAllEvents(LinkedList<ActorInterface>list){

    }

    public HashMap<ActorInterface, MessageInterface> getSentMessages(){
        return null;
    }

    public HashMap<ActorInterface, MessageInterface> getReceivedMessages(){
        return null;
    }
 /*   public subs(){
        new ActorListener(mS)
        pepe(ActorListener)
    }
    */

}
