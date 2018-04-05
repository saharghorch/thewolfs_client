package esprit.javafx;

import tn.esprit.thewolfs_server.entity.Trader;

public class Session {

	private static Trader trader = null;

	/*
	 * This function allows us to set user credentials to a static User instance 
	 * */
	public static boolean createSession(Trader user) {
		Session.trader = user;
		return true;
	}
	 /*
	  * This function allows us to set user to a null value and log out  
	  * */
	public static boolean dropSession() {
		Session.trader =null ; 
		return true;
	}
	 /*
	  * This function allows us to see whether a session is open or not  
	  * */
	public static boolean requestSession() {
		return (Session.trader==null);	
	}
	 /*
	  * This function allows us to get the connceted user credentials 
	  * */
	public static Trader getUser() {
		return trader;
	}

}
