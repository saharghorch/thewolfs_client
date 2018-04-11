package esprit.javaee;




import java.awt.Window.Type;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Status;
import tn.esprit.thewolfs_server.entity.StockOption;

import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.StockOptionServiceRemote;


public class TestServiceAccount {

	public static void main(String[] args) throws NamingException {
String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
Context context=new InitialContext();
AccountServiceRemote proxy=(AccountServiceRemote) context.lookup(jndiName);
/*Account account1=new Account(1000f, Currency.USD);
System.out.println(proxy.addAccount(account1));*/
//account1.setAmount(2000f);
//proxy.updateAccount(account1);
//proxy.removeAccount();
//Account account = new Account();
//System.out.println(proxy.addAccount(account));
//account.setId(16);
//account.setAmount(6000f);
//account.setCurrency(Currency.SAR);
//proxy.updateAccount(account);
//Account accountDisplay=proxy.displayAccountById(16);
//System.out.println(accountDisplay);

List<Account> accounts= proxy.displayAllAccounts();
for(Account a : accounts)
{
	System.out.println(a);
}


//System.out.println(proxy.findAccountByAmount(6000f));

List<Account> accountsTrader= proxy.findAllAccountByTrader(1);
for(Account a : accountsTrader)
{
	System.out.println(a);
}


String jndiNameStockOption="thewolfs_server-ear/thewolfs_server-ejb/StockOptionService!tn.esprit.thewolfs_server.services.StockOptionServiceRemote";
Context contextStockOption=new InitialContext();
StockOptionServiceRemote proxyStockOption=(StockOptionServiceRemote) context.lookup(jndiNameStockOption);

StockOption stockOption=new StockOption(98250d,150d, 88d,"mst",tn.esprit.thewolfs_server.entity.Type.Put,Status.Valid);
System.out.println(proxyStockOption.addStockOption(stockOption));



	}

}
