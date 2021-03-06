package esprit.javaee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import esprit.javafx.SendMail;
import tn.esprit.thewolfs_server.services.PricingRemote;

public class TestPricing {

	public static void main(String[] args) throws NamingException {
		String jndiName="thewolfs_server-ear/thewolfs_server-ejb/Pricing!tn.esprit.thewolfs_server.services.PricingRemote";
		Context context=new InitialContext();
		PricingRemote proxy=(PricingRemote) context.lookup(jndiName);
		System.out.println("Price of CallOption: "+proxy.CallOptionPrice(12.00, 15.0, 0.2, 0.1, 0.3));
		System.out.println("Price of PutOption: "+proxy.PutOptionPrice(12.00, 15.0, 0.2, 0.1, 0.3));	
		 SendMail mail=new SendMail();
		 mail.send("ahmed.mdallel@esprit.tn", "500.0d");
		
	}

}
