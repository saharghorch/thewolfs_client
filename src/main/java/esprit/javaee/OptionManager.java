package esprit.javaee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Options;
import tn.esprit.thewolfs_server.entity.Status;
import tn.esprit.thewolfs_server.entity.Type;
//import tn.esprit.thewolfs_server.services.HelloServiceRemote;
//import tn.esprit.thewolfs_server.services.OptionsRemote;
import tn.esprit.thewolfs_server.entity.*;
public class OptionManager {
	public static void main(String[] args) throws NamingException {
	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context = new InitialContext();
		//OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			
}
}