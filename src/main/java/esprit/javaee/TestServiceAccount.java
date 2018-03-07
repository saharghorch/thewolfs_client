package esprit.javaee;


//import java.awt.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Currency;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import java.util.List;


public class TestServiceAccount {

	public static void main(String[] args) throws NamingException {
String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
Context context=new InitialContext();
AccountServiceRemote proxy=(AccountServiceRemote) context.lookup(jndiName);
//Account account1=new Account(50f, Currency.EUR);
//System.out.println(proxy.addAccount(account1));
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
/*
List<Account> accounts= proxy.displayAllAccounts();
for(Account a : accounts)
{
	System.out.println(a);
}
*/

//System.out.println(proxy.findAccountByAmount(6000f));









	}

}
