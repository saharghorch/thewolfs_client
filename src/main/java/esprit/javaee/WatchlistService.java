package esprit.javaee;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Watchlist;
import tn.esprit.thewolfs_server.services.WatchlistServiceRemote;

public class WatchlistService {


	public static void main(String[] args) throws NamingException {
		
		String jndiname="thewolfs_server-ear/thewolfs_server-ejb/WatchlistService!tn.esprit.thewolfs_server.services.WatchlistServiceRemote";
		Context context = new InitialContext();
		WatchlistServiceRemote proxy=(WatchlistServiceRemote) context.lookup(jndiname);
		Watchlist w=new Watchlist("InTheMoney", 1.1, 2.2);
		Watchlist w1=new Watchlist("OutOfTheMoney", 5.9, 3.3);
		Watchlist w2=new Watchlist("OutOfTheMoney", 1.1, 8.96);
		Watchlist w3=new Watchlist("InTheMoney", 4.4, 7.32);
	
		Watchlist w5=new Watchlist();
	
		w5.setId(65);
		
		proxy.updateWatchlist(w5);
		//proxy.addWatchlist(w);
		//proxy.addWatchlist(w1);
		//proxy.addWatchlist(w2);
		//proxy.addWatchlist(w3);
		//proxy.addWatchlist(w4);
		//proxy.deleteWatchlist(w1);
		//proxy.deleteWatchlist(54);
		//Watchlist w6 = proxy.displayWatchlistById(65);
		
		
	
		
		List<Watchlist> watchlist= proxy.displayAllWatchlists();
		for(Watchlist a : watchlist)
		{
			System.out.println("Id: "+a.getId()+" pp"+a.getPremium_price()+" sp "+a.getStrike_price());
		}
	
		  
		

	}

}
