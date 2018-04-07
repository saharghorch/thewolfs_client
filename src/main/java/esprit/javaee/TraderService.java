package esprit.javaee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Level;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;
//import tn.esprit.thewolfs_server.services.HelloServiceRemote;
//import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class TraderService {

	public TraderService() {
		// TODO Auto-generated constructor stub
	}
  
	public static void main(String[] args) throws NamingException {
		String jndiname="thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context context = new InitialContext();
		TraderServiceRemote proxy=(TraderServiceRemote) context.lookup(jndiname);
		//Trader trader2 = new Trader("Meriem", "dbibi", "meriem.dbibi@esprit.tn", "abc",Level.secondLevel);
	//System.out.println(proxy.Traderexiste(trader2));
		//System.out.println(proxy.Tr);
		//System.out.println(proxy.addTrader(trader2));
		//Trader trader4 = new Trader("yosra", "tarhouni", "yosra.tarhouni@esprit.tn", "nabeul", Level.firstLevel);
       //System.out.println(proxy.addTrader(trader4));
		//trader4.setFirst_name("first");
		//proxy.updateTrader(trader4);
		//Trader trader5= new Trader();
		//trader5.setId(11);
		//trader5.setFirst_name("enis");
		//trader5.setLast_name("boukadida");
		//trader5.setEmail("enis.boukadida@esprit.tn");
		//trader5.setPassword("azer");
		//trader5.setLevel(Level.firstLevel);
		//proxy.updateTrader(trader5);
		//proxy.deleteTraderById(3);
		//List<Trader> lst =proxy.dislayTrader();
		//for(Trader t: lst){
		//	System.out.println(t);
		//}
	}
}
