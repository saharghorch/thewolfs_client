package esprit.javaee;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Options;
import tn.esprit.thewolfs_server.entity.Status;
import tn.esprit.thewolfs_server.entity.Type;
import tn.esprit.thewolfs_server.services.OptionsRemote;
//import tn.esprit.thewolfs_server.services.HelloServiceRemote;
//import tn.esprit.thewolfs_server.services.OptionsRemote;
import tn.esprit.thewolfs_server.entity.*;
public class OptionManager {
	public static void main(String[] args) throws NamingException, ParseException {
	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context = new InitialContext();
		OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
		
		Options option = new Options();
		option.setStrike_price(0.2f);
		option.setPremium_price(0.4f);
		option.setExpiration_date(null);
		option.setType(Type.Call);
		option.setStatus(Status.Canceled);
		option.setAsset(null);
		option.setTrader(null);
		option.setCounterparty(null);
		option.setUser(null);
		

   //int OptionId=proxy.addOption(option);
    
    Options op = new Options();
    op.setStrike_price(22.0f);
	op.setPremium_price(0.55f);;
	op.setExpiration_date(null);
	op.setType(Type.Put);
	op.setStatus(Status.onHold);
	op.setAsset(null);
	op.setTrader(null);
	op.setCounterparty(null);
	op.setUser(null);

    //int OptionIdd=proxy.addOption(op);
	int OptionIddd= 1;
	
	//proxy.deleteOption(OptionIdd);
	
//Options opp = proxy.getOptionById(1);
//List<Options> arr = new ArrayList();
//arr= proxy.findAll();
//System.out.println(arr);
	Trader k = new Trader();
	int ii=1;

k=proxy.findTraderById(ii);
System.out.println(k);
//List<Options> arrs = new ArrayList();

	k=proxy.findTraderById(ii);
	//System.out.println(k);
List<Options> arrs = new ArrayList();
arrs = proxy.findOptionsByType(Type.Put);
System.out.println(arrs);

//arrs= proxy.findOptionsValid(Status.Valid);
//System.out.println(arrs);


//List<Asset> arrAsset = new ArrayList();
//arrAsset=proxy.findAssetType();
//System.out.println(arrAsset);
//proxy.UpdateOptionStatus(OptionIddd);	
	SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
	Date dd=dateFormat.parse("11/04/2018");
	String a = proxy.TimeToExpiry(dd);
	//System.out.println(a);
	
//Float am = proxy.FindAmountTrader(1);
//System.out.println(am);
	//proxy.UpdateAmount(1, 2500);
	
}
}