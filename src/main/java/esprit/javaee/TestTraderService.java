package esprit.javaee;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Level;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class TestTraderService {

	public TestTraderService() {
		// TODO Auto-generated constructor stub
	}
  
	public static void main(String[] args) throws NamingException {
		String jndiname="thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context context = new InitialContext();
		TraderServiceRemote proxy=(TraderServiceRemote) context.lookup(jndiname);
	   
		//Ajout d'un Trader
		/*Trader trader = new Trader("First", "Second", "First.Second@esprit.tn", "0000",Level.thirdLevel);
	    System.out.println(proxy.addTrader(trader));/*
		
	    //Mise A jour d'un Trader
	   /* Trader trader_update=new Trader();
	    trader_update.setId();
	    trader_update.setFirst_name("first_name");
	    trader_update.setLast_name("last_name");
	    trader_update.setEmail("email");
	    trader_update.setPassword("password");
	    trader_update.setLevel(Level.secondLevel);
		System.out.println(proxy.updateTrader(trader_update));*/
		
		//Suppression d'un Trader
		
		
		//Affichage d'un Trader
		/*List<Trader> traders =proxy.dislayTrader();
		for(Trader t: traders){
			System.out.println(t);
		}*/
		
	    //recherche d'une liste de trader
		//System.out.println(proxy.findTraderByName("e"));
		
		
		//existe ou nn 
		Trader trader2 = new Trader("Meriem", "dbibi", "meriem.dbibi@esprit.tn", "abc",Level.secondLevel);
		System.out.println(proxy.Traderexiste(trader2));
		//System.out.println(proxy.calculerLevel1());
	}
	
}
