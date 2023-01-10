package dynamic;

import actor.ActorInterface;
import helloWorld.Message;
import proxy.ActorProxy2;
import proxy.AddInsultMessage;
import proxy.GetAllInsultsMessage;
import proxy.GetInsultMessage;

import java.lang.reflect.*;

public class DynamicProxy implements InvocationHandler {
    private Object target = null;
    private ActorInterface actor;


    public DynamicProxy(Object impl, ActorInterface a){
        this.target=impl;
        this.actor=a;
    }

    /**
     * Create an Instance of a Dynamic Proxy
     * @param target -> Class that its methods will be intercepted
     * @param a -> Actor that will execute the methods intercepted
     * @return Dynamic Proxy Instance
     */
    public static Object newInstance(Object target, ActorInterface a){
        Class targetClass = target.getClass();
        Class interfaces[] = targetClass.getInterfaces();
        return Proxy.newProxyInstance(targetClass.getClassLoader(),interfaces, new DynamicProxy(target,a));
    }

    /**
     *
     * @param proxy the proxy instance that the method was invoked on
     *
     * @param method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if(Object.class  != method.getDeclaringClass()) {
            String name = method.getName();
            if("addInsult".equals(name)) {
                actor.send(new AddInsultMessage(actor,(String)args[0]));
            } else if("getInsult".equals(name)) {
                actor.send(new GetInsultMessage(actor));
                return actor.receive().getMsg();
            } else if("getAllInsults".equals(name)) {
                actor.send(new GetAllInsultsMessage(actor));
                return actor.receive().getMsg();
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
        return method.invoke(this.target,args);
    }
}
