package actor;

import helloWorld.*;
import proxy.*;

import java.util.*;


public class ActorContext extends Thread {
    private static ActorContext instance = new ActorContext();

    private static HashMap<String, ActorInterface> registry = new HashMap<String, ActorInterface>();

    private ActorContext() {};

    /**
     * Get the Instance of a Singleton class
     * @return ActorContext instance
     */
    public static ActorContext getInstance(){
        return instance;
    }

    /**
     * Registry the actor passed by parameter. It also creates Thread and a Proxy of that actor
     * @param name -> Actor's name
     * @param a -> ActorInterface instance
     * @return ActorProxy
     */
    public static ActorProxy spawnActor(String name, ActorInterface a){
        System.out.println("Me estoy creando");
        ActorProxy proxy = new ActorProxy(a);
        a.setName(name);
        registry.put(name, proxy);
        Thread hilo = new Thread(a);
        hilo.start();
        System.out.println("Tenemos: "+registry.size());
        return proxy;
    }

    /**
     * Registry the actor passed by parameter. It also creates Thread and a Proxy of that actor
     * @param name -> Actor's name
     * @param a -> ActorInterface instance
     * @return ActorProxy2
     */
    public static ActorProxy2 spawnActor2(String name, ActorInterface a){
        registry.put(name, a);
        Thread hilo = new Thread(a);
        hilo.start();
        return new ActorProxy2(a);
    }

    // Kill all the threads/actors: Iterate the HashMap and send a quitMessage to all the Actors

    /**
     * Remove all actors registered in the ActorContext HashMap
     */
    public void quitAll(){
        registry.forEach((k,v)-> v.send(new QuitMessage(v)));
        registry.clear();
    }

    /**
     * Find the actor passed by parameter in hte ActorContext HashMap
     * @param s -> Actor's name
     * @return ActorInterface instance or null
     */
    public ActorInterface lookup(String s){ return registry.get(s); }

    /**
     * Get all the names of the actors registered in ActorContext
     * @return String with all the actor's names
     */
    public String getNames(){
        return registry.keySet().toString().replace("[", "").replace("]", ""); }

    /**
     * Get the ActorContext actor registry
     * @return ActorContext actor registry
     */
    public HashMap<String, ActorInterface>  getRegistry(){
        return registry;
    }
}
