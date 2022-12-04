
import actor.ActorContext;
import helloWorld.*;
import actor.*;


public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        // Cogemos instancia unica de Actor.actor.ActorContext para poder llamar a sus metodos
        // Actor.actor.ActorContext actorContext = Actor.actor.ActorContext.getInstance();

        ActorProxy hello = ActorContext.spawnActor("hello",new RingActor());
        //hello.send(new Actor.helloWorld.Message(null, "Hello world"));

        ActorProxy t1 = ActorContext.spawnActor("t1", new RingActor());
        t1.send(new Message(hello, "Hello world (t1)"));
        System.out.println("entre mensajes");

        ActorProxy t2 = ActorContext.spawnActor("t2", new RingActor());
        t2.send(new Message(hello, "Hello world (t2)"));

        ActorProxy t3 = ActorContext.spawnActor("t3", new RingActor());
        t3.send(new Message(t1, "Hello world (t3->t1)"));

        //Thread.sleep(5000);
        //actor.ActorContext.getInstance().quitAll();
        //actor.ActorContext.getInstance().quitAll();

        //Devuelve nombres Actores en Actor.actor.ActorContext
        System.out.println(ActorContext.getInstance().getNames());
       
    }
}
