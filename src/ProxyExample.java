import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
 
 
interface Transactional {
   public boolean save();
}
 
class Person implements Transactional {
 
    @Override
    public boolean save() {
        System.out.println("Saving person information...");
        return false;
    }
}
 
class TransactionSupport implements InvocationHandler {
 
    private Transactional transactional;
    public TransactionSupport(Transactional transactional) {
        this.transactional = transactional;        
        
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("Starting a new transaction");
        Object result = method.invoke(transactional, args);
        System.out.println("Ending the transaction");
        return result;
    }
    
}
 
public class ProxyExample {
    
    public static void main(String[] args) {
        Person p = new Person();
        Transactional proxiedPerson = (Transactional) Proxy.newProxyInstance(ProxyExample.class.getClassLoader(), 
                new Class[] { Transactional.class }, new TransactionSupport(p));        
        proxiedPerson.save();        
    }
 
}