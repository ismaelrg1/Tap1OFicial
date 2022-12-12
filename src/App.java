
import helloWorld.*;
import actor.*;
import proxy.*;
import decorator.*;

import java.util.NoSuchElementException;


public class App {
    public static void main(String[] args) throws Exception {
        /*System.out.println("Hello, World!");

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
        t3.send(new Message(t3, "Hello world (t3->t3)"));

        //Thread.sleep(5000);
        //actor.ActorContext.getInstance().quitAll();
        //actor.ActorContext.getInstance().quitAll();

        //Devuelve nombres Actores en Actor.actor.ActorContext
        System.out.println(ActorContext.getInstance().getNames());


        //////////////////////////////////////////////////////////////////////////////

        ActorProxy2 insult = ActorContext.spawnActor2("hola",new InsultActor());
        // en getInsultMsg() dentro del () tendremos el from, que es quien pide al destiantario que le mande un
        // insulto. EL destinatario cogera uno aleatorio y se lo mandará a la cola del from para que lo pueda leer
        insult.send(new GetInsultMessage(insult));
        Thread.sleep(5000);
        Message result = (Message) insult.receive();
        System.out.println(result.getMsg());


        insult.send(new GetAllInsultsMessage(insult));
        Thread.sleep(7000);
        //Procesando todos los insultos

        try {
            while (true) {
                result = (Message) insult.receive();
                System.out.print(result.getMsg()+" ");
            }
        }catch (NoSuchElementException e){
            System.out.println("Todo bien");
        }

        insult.send(new AddInsultMessage(insult, "nerd"));
        Thread.sleep(1000);

        insult.send(new GetAllInsultsMessage(insult));
        Thread.sleep(7000);
        //Procesando todos los insultos

        try {
            while (true) {
                result = (Message) insult.receive();
                System.out.print(result.getMsg()+" ");
            }
        }catch (NoSuchElementException e){
            System.out.println("Todo bien");
        }*/

    //////////////////////////////////////////////////////////////

        ActorProxy pepe = ActorContext.spawnActor("pepe",new EncryptionDecorator(new RingActor()));
        ActorProxy manoli = ActorContext.spawnActor("manoli",new EncryptionDecorator(new RingActor()));

        pepe.send(new Message(pepe,"ABCD"));
        //pepe.send(new Message(hello,"ABCD"));



        //actor.ActorContext.getInstance().quitAll();
    }
}
