package esprit.javaee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.thewolfs_server.entity.TypeRate;
import tn.esprit.thewolfs_server.services.InterestServiceRemote;


public class MainInterest {

	public static void main(String[] args) throws NamingException {
		String jndiname="thewolfs_server-ear/thewolfs_server-ejb/InterestService!tn.esprit.thewolfs_server.services.InterestServiceRemote";
		Context context = new InitialContext();
		InterestServiceRemote proxy=(InterestServiceRemote) context.lookup(jndiname);	
		
	
		System.out.println(proxy.CalculSimpleInterestAmount(10000d, 0.05d, 5));
		System.out.println(proxy.CalculCompoundInterestAmount(10000d, 0.15d, 1, TypeRate.continuously));
	
		
	}

}
