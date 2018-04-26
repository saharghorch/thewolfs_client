package esprit.javaee;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Asset;
import tn.esprit.thewolfs_server.services.AssetServiceRemote;

public class AssetService {



	public static void main(String[] args)throws NamingException{
		String jndiname="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";	
		Context context = new InitialContext();
		AssetServiceRemote proxy=(AssetServiceRemote) context.lookup(jndiname);
	
		//Asset a=new Asset(5);
		//Asset as=new Asset(4);
		//proxy.addAsset(as);
		//proxy.addAsset(a);
		
		

		List <Asset> asset= proxy.displayAllAssets();
	
	}


}
