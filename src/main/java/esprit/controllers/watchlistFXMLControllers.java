
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.controllers;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import tn.esprit.thewolfs_server.entity.Watchlist;
import tn.esprit.thewolfs_server.services.WatchlistServiceRemote;


/* private String evaluation;
	private float premium_price;
	private float strike_price;
	*/
/**
 * FXML Controller class
 *
 * @author HP
 */
public class watchlistFXMLControllers implements Initializable {

	@FXML
    private Button deletebtn;

    @FXML
    private Button addbtn;

    @FXML
    private Button updatebtn;

    @FXML
    private TextField tfev;

    @FXML
    private TextField tfsp;

    @FXML
    private TextField tfpp;


    @FXML
    private TableColumn<Watchlist,Double> colpp;

    @FXML
    private TableColumn<Watchlist,Double> colsp;

    @FXML
    private TableColumn<Watchlist, String> colev;
   
    @FXML
    void mouse() {
	}

    private TableView<Watchlist> tableviewwatchlist = new TableView<Watchlist>();
    private ObservableList<Watchlist> data1 = FXCollections.observableArrayList();
   



    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
			try {
				afficher();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
      
    }    
   
     public void onclickadd(ActionEvent event) throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/WatchlistService!tn.esprit.thewolfs_server.services.WatchlistServiceRemote";
    Context ctx = new InitialContext();
    WatchlistServiceRemote proxy = (WatchlistServiceRemote) ctx.lookup(jndiName);
   Watchlist w=new Watchlist();
   Double a , b ;
   String res;
   res="";
   w.setPremium_price(Double.parseDouble(tfpp.getText()));
   w.setStrike_price(Double.parseDouble(tfsp.getText()));
   a=w.getPremium_price();
   b=w.getStrike_price();
   if(b>a){res="Out Of The Money";}
   else if (b<a){res="In the money";}
   else {res="At the Money";}
   w.setEvaluation(res);
   tfev.setText(res);
   
   proxy.addWatchlist(w);
  
   afficher();
  
    }

    public void afficher() throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/WatchlistService!tn.esprit.thewolfs_server.services.WatchlistServiceRemote";
       
    	
    	Context ctx = new InitialContext();
        WatchlistServiceRemote proxy = (WatchlistServiceRemote) ctx.lookup(jndiName);
        
        colpp.setCellValueFactory(new PropertyValueFactory<>("premium_price"));
        colsp.setCellValueFactory(new PropertyValueFactory<>("strike_price"));
        colev.setCellValueFactory(new PropertyValueFactory<>("evaluation"));
        
      
        Watchlist c = new Watchlist();
        List<Watchlist> o = proxy.displayAllWatchlists();
        
        
        
		for (Watchlist  watchlist : o) {
			data1.add(watchlist);
			System.out.print("qqqqqqqq");
		}
		
		tableviewwatchlist.setItems(data1);	
	}
    
    /*void update(ActionEvent event) throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/WatchlistService!tn.esprit.thewolfs_server.services.WatchlistServiceRemote";
    	Context ctx = new InitialContext();
    	WatchlistServiceRemote proxy=(WatchlistServiceRemote) ctx.lookup (jndiName);
            if (tableviewwatchlist.getSelectionModel().getSelectedItem() != null) {
               Watchlist w = tableviewwatchlist.getSelectionModel().getSelectedItem();
                w.setPremium_price(Double.parseDouble(pptf.getText()));
                w.setStrike_price(Double.parseDouble(sptf.getText()));
                w.setEvaluation(evtf.getText());
      
         	proxy.updateWatchlist(w);
            	 try {
            		 aficher();
       			} catch (NamingException e) {
       				// TODO Auto-generated catch block
       				e.printStackTrace();
       			}
                
    }

    }

    
    @FXML
     public void mouse(MouseEvent event) {
    	if (tableviewwatchlist.getSelectionModel().getSelectedItem() !=null)
    	{
    		Watchlist w = tableviewwatchlist.getSelectionModel().getSelectedItem();
    		sptf.setText(w.getStrike_price()+"");
    		pptf.setText(w.getPremium_price()+"");
    		evtf.setText(w.getEvaluation()+"");
    	}

    }
   
  public void delete(ActionEvent event) throws NamingException {
   	 String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/WatchlistService!tn.esprit.thewolfs_server.services.WatchlistServiceRemote";
   		Context ctx = new InitialContext();
   		WatchlistServiceRemote proxy=(WatchlistServiceRemote) ctx.lookup (jndiName);
   		int idWatchlist = tableviewwatchlist.getSelectionModel().getSelectedItem().getId();
   		proxy.deleteWatchlist(idWatchlist);
   		List<Watchlist> list = proxy.displayAllWatchlists();
   		ObservableList<Watchlist> items = FXCollections.observableArrayList(list);
   		tableviewwatchlist.setItems(items);
   		
  

}*/
} 