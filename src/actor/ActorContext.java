package actor;

import helloWorld.ActorProxy;
import helloWorld.QuitMessage;

import java.util.*;

public class ActorContext extends Thread {
    private static ActorContext instance = new ActorContext();

    private static HashMap<String, ActorInterface> registry = new HashMap<String, ActorInterface>();

    private ActorContext() {};

    public static ActorContext getInstance(){
        return instance;
    }

    public static ActorProxy spawnActor(String name, ActorInterface a){
        registry.put(name, a);
        new Thread(a).start();
        return new ActorProxy(a);
    }

    // Kill all the threads/actors: Iterate the HashMap and send a quitMessage to all the Actors
   public void quitAll(){
        registry.forEach((k,v)->v.send(new QuitMessage(v)));
        registry.clear();
   }
    // public lookup()

    public String getNames(){
        return registry.keySet().toString();
    }
}
