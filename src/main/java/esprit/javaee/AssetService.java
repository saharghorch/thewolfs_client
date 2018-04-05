package esprit.javaee;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Asset;

import tn.esprit.thewolfs_server.entity.Watchlist;
import tn.esprit.thewolfs_server.services.AssetServiceRemote;
import tn.esprit.thewolfs_server.services.WatchlistServiceRemote;

public class AssetService {



	public static void main(String[] args)throws NamingException{
		String jndiname="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";	
		Context context = new InitialContext();
		AssetServiceRemote proxy=(AssetServiceRemote) context.lookup(jndiname);
		Asset as=new Asset(5);
		//Asset a=new Asset(5);
		//Asset as=new Asset(4);
		//proxy.addAsset(as);
		//proxy.addAsset(a);
		
		

		List <Asset> asset= proxy.displayAllAssets();
		for(Asset e: asset){
			System.out.println("le nombre" +e.getShares_number());
		}
	}


}
