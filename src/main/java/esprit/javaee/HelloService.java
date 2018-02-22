package esprit.javaee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.services.HelloServiceRemote;


public class HelloService {
	
public static void main(String[] args) throws NamingException {
	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/HelloService!tn.esprit.thewolfs_server.services.HelloServiceRemote";
	Context context = new InitialContext();
	HelloServiceRemote proxy=(HelloServiceRemote) context.lookup(jndiname);
	System.out.println(proxy.sayHello("Hello"));
}

}
