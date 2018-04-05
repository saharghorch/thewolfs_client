package esprit.javafx;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Level;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class LogInOut  {

	
	private String email; 
	private String password ; 
	
	
	public LogInOut(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String  login()  {
		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";		
		try{
			Context context = new InitialContext();
			TraderServiceRemote logInProxy= (TraderServiceRemote) context.lookup(jndiname); 
			List <Trader> Ub = logInProxy.loginQuery(this.email,this.password) ;	
			if (Session.requestSession()){
				if (Ub.isEmpty()){
					return ("verifie your cridentials ");
				}
					else {
							Ub.get(0).setPassword(this.password);
							Session.createSession(Ub.get(0));
								if (Ub.get(0).getLevel()== Level.firstLevel){
									return ("level"); 
								}
								else if (Ub.get(0).getLevel()== Level.secondLevel){
									return ("level"); 
								}else if (Ub.get(0).getLevel()== Level.thirdLevel){
									return ("level"); 
								}
							}
						}
					
			
			else {
				return ("you are already connected "); 
			}
}
catch (NamingException e){
	e.printStackTrace(); 
	System.out.println("!!!!!!!!!!!!");
}	
return "Unkown Error accured";
	}

	public boolean logout() {
	//	Session s = new Session(); 
		Session.dropSession() ; 
		if (Session.requestSession()){
			System.out.println("Déconnectée !!!!");
		}
		return false;
	}

}
