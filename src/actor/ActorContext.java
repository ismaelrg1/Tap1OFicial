package actor;

import helloWorld.*;
import proxy.*;

import java.util.*;

public class ActorContext extends Thread {
    private static ActorContext instance = new ActorContext();

    private static HashMap<String, ActorInterface> registry = new HashMap<String, ActorInterface>();

    private ActorContext() {};

    public static ActorContext getInstance(){
        return instance;
    }

    public static ActorProxy spawnActor(String name, ActorInterface a){
        ActorProxy proxy = new ActorProxy(a);
        registry.put(name, proxy);
        Thread hilo = new Thread(a);
        hilo.start();
        return proxy;
    }

    public static ActorProxy2 spawnActor2(String name, ActorInterface a){
        registry.put(name, a);
        Thread hilo = new Thread(a);
        hilo.start();
        return new ActorProxy2(a);
    }

    // Kill all the threads/actors: Iterate the HashMap and send a quitMessage to all the Actors
   public void quitAll(){
        registry.forEach((k,v)->v.send(new QuitMessage(v)));
        registry.clear();
   }
    public ActorInterface lookup(String s){
        //return registry.get(s) instanceof ActorInterface;
        return registry.get(s);
    }

    public String getNames(){
        return registry.keySet().toString();
    }

    public HashMap<String, ActorInterface>  getRegistry(){
        return registry;
    }
}
