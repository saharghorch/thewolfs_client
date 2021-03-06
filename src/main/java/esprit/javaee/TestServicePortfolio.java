package esprit.javaee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Options;
import tn.esprit.thewolfs_server.entity.Portfolio;
import tn.esprit.thewolfs_server.services.OptionsRemote;
import tn.esprit.thewolfs_server.services.PortfolioServiceRemote;
public class TestServicePortfolio {

	public static void main(String[] args) throws NamingException, ParseException{
		
		String jndiName="thewolfs_server-ear/thewolfs_server-ejb/PortfolioService!tn.esprit.thewolfs_server.services.PortfolioServiceRemote";
		Context context=new InitialContext();
		PortfolioServiceRemote proxy=(PortfolioServiceRemote) context.lookup(jndiName);
		SimpleDateFormat dateFormat =new SimpleDateFormat("dd/MM/yyyy");
		
		String jndiNameOptions="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context contextOptions=new InitialContext();
		OptionsRemote proxyOptions=(OptionsRemote) contextOptions.lookup(jndiNameOptions);
		
		//Ajout d'un Portfolio
/*		Date creation_date=dateFormat.parse("09/04/2018");
		Portfolio portfolio=new Portfolio(creation_date,942018f);*/
	   // System.out.println(proxy.addPortfolio(portfolio));
	    
	    //Mise A jour d'un Portfolio
		/*
		Portfolio portfolio=new Portfolio();
		portfolio.setId(11);
	    Date creation_dateUpdate=dateFormat.parse("07/03/2018");
	    portfolio.setCreation_date(creation_dateUpdate);
	    portfolio.setCash(56200f);
	    proxy.updatePortfolio(portfolio);
	    */
		
		//Suppression d'un Portfolio
	    //proxy.removePortfolio();
	    
	    //Affichage d'un Portfolio
	     //System.out.println(proxy.displayPortfolioById(8));
	    
	    //Affichage des Portfolios
	 /*  List<Portfolio> portfolios= proxy.displayAllPortfolios();
	    for(Portfolio p : portfolios)
	    {
	    	System.out.println(p);
	    }*/
	    
	  
	  /*Portfolio portfolio=new Portfolio();
	    for(Options op:portfolio.getAllOptions())
	    	System.out.println(op.getPremium_price());*/
	   /*Integer idTrader=proxy.getIdTraderByPortfolioId(9);
	   System.out.println(idTrader);*/
	    
	   // System.out.println(proxy.findPortfolioByCash(15500f));
	    
	    //Affichage de toutes les options d'un portfolio
	    //List<Options> allOptions=proxyOptions.findAll();
	/*    
	    Integer idTrader=1;
	    Integer idPortfolio =proxy.addPortfolio(portfolio);
	    proxy.assignPortfolioToTrader(idTrader, idPortfolio);
		String jndinameOption="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context contextOption = new InitialContext();
		OptionsRemote proxyOption=(OptionsRemote) context.lookup(jndinameOption);*/
		
		//Integer idTrader=4;
		System.out.println(proxy.getPortfolioById(8));
		
	    
	    
	}
	

}
