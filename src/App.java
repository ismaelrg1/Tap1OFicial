
import dynamic.*;
import helloWorld.*;
import actor.*;
import proxy.*;
import decorator.*;

import java.awt.event.MouseWheelEvent;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.NoSuchElementException;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import validation.*;

/**
 * @author Ismael & Mario
 */
public class App {
    public static void main(String[] args) throws Exception {
/*

                    */
/*HelloWorld Actor*//*


        ActorProxy hello = ActorContext.spawnActor("hello",new RingActor());

        ActorProxy t1 = ActorContext.spawnActor("t1", new RingActor());
        t1.send(new Message(hello, "Hello world (t1)"));

        ActorProxy t2 = ActorContext.spawnActor("t2", new RingActor());
        t2.send(new Message(hello, "Hello world (t2)"));

        ActorProxy t3 = ActorContext.spawnActor("t3", new RingActor());
        t3.send(new Message(t3, "Hello world (t3->t3)"));



        //Returns the actor names of the ActorContext Registry
        System.out.println(ActorContext.getInstance().getNames());

    //////////////////////////////////////////////////////////////////////////////
                    */
/* InsultActor with Actor Proxy *//*



        ActorProxy2 insult = ActorContext.spawnActor2("hola",new InsultActor());

        insult.send(new GetInsultMessage(insult));
        Message result = (Message) insult.receive();

        System.out.println("Random Insult: "+result.getMsg());


        insult.send(new GetAllInsultsMessage(insult));

        MessageInterface r1 = insult.receive();

        System.out.println("Initial Insults: "+r1.getMsg());


        insult.send(new AddInsultMessage(insult, "nerd"));

        insult.send(new GetAllInsultsMessage(insult));

        MessageInterface r2 = insult.receive();
        System.out.println("Final Insults "+r2.getMsg());

*/

    //////////////////////////////////////////////////////////////
                /* Encrypt/Decrypt Decorator Pattern */
/*

        ActorProxy pepe = ActorContext.spawnActor("pepe",new EncryptionDecorator(new RingActor()));
        ActorProxy manoli = ActorContext.spawnActor("manoli",new EncryptionDecorator(new RingActor()));

        pepe.send(new Message(pepe,"ABCD"));
        manoli.send(new Message(pepe,"14"));



    ////////////////////////////////////////////////////////////////
                 */
/*Firewall Decorator Pattern *//*


        ActorProxy evilCompilator = new ActorProxy(new RingActor());
        ActorProxy superman = ActorContext.spawnActor("superman", new RingActor());
        ActorProxy police = ActorContext.spawnActor("police", new FireWallDecorator(new RingActor()));

        police.send(new Message(evilCompilator, "Virus"));
        police.send(new Message(superman, "Super"));


    ////////////////////////////////////////////////////////////////
                 */
/*LambdaFirewall Decorator Pattern *//*


        ActorProxy filter = ActorContext.spawnActor("filter", new LambdaFirewallDecorator(new RingActor()));
        ActorProxy reciver = ActorContext.spawnActor("reciver", new LambdaFirewallDecorator(new RingActor()));

        reciver.send(new Message(filter,"Mario"));                  //Message sent

        reciver.send(new AddClosureMessage((String p) -> p.startsWith("p",3)));

        reciver.send(new Message(filter,"camp"));                  //Message NOT sent

        reciver.send(new AddClosureMessage((String p) -> p.startsWith("f")));
        reciver.send(new Message(filter,"fake"));                   // NO
        reciver.send(new Message(filter,"perfect fail"));           // YES
        reciver.send(new Message(filter,"Ismael"));                 // YES

*/

    /////////////////////////////////////////////////////////////////////////////////////////
                    /*Pipeling Decorators*/
        Thread.sleep(2000);
        System.out.println("\n\n\nPipeling");
        ActorProxy pipelin = ActorContext.spawnActor("pipe",new FireWallDecorator(new LambdaFirewallDecorator(new EncryptionDecorator(new RingActor()))));
        //ActorProxy pipelin2 = ActorContext.spawnActor("pipe2",new EncryptionDecorator(new LambdaFirewallDecorator(new FireWallDecorator(new RingActor()))));


        pipelin.send(new Message(null, "Encripted"));
 /*       Thread.sleep(500);
        pipelin2.send(new Message(reciver, "Version2"));
        Thread.sleep(500);

        pipelin.send(new AddClosureMessage((String p)->p.startsWith("i")));
        Thread.sleep(500);
        pipelin2.send(new AddClosureMessage((String p)->p.startsWith("p")));
        Thread.sleep(500);

        pipelin.send(new Message(filter, "ismale"));
        Thread.sleep(500);
        pipelin2.send(new Message(reciver, "paco")); //ERROR MESSAGE Sent because was encrypted before checking the closures
        Thread.sleep(500);
        pipelin.send(new Message(evilCompilator,"Virus"));
        Thread.sleep(500);
        pipelin2.send(new Message(evilCompilator,"Virus"));


    ////////////////////////////////////////////////////////////////
                    //Dynamic Proxy Pattern


        // Creating the Dynamic Proxy
        ActorProxy2 badSmell = ActorContext.spawnActor2("badSmell", new InsultActor());
        InsultService ins = (InsultService) Proxy.newProxyInstance(InsultService.class.getClassLoader(),
                new Class<?>[] {InsultService.class},
                new DynamicProxy(new InsultServiceImpl(), badSmell));

        System.out.println("Random Insult:" +ins.getInsult());
        System.out.println("All Insults: "+ins.getAllInsults());
        ins.addInsult("sneeze");
        System.out.println("All Insults (+1): "+ins.getAllInsults());

    ////////////////////////////////////////////////////////////////////////////////////////
        //mS.subscribe("pepe");

    //////////////////////////////////////////////////////////
                        //Unit Tests
*//*

        Result res = JUnitCore.runClasses(TestSuite.class);
        for (Failure failure : res.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("TEST Actor OK? " + res.wasSuccessful());
*//*
        ActorContext.getInstance().quitAll();
        Thread.sleep(2000);
        System.out.println("Hay "+ActorContext.getInstance().getNames().length()+" Actores y sus nombres son "+ActorContext.getInstance().getNames());

*/
    }
}
