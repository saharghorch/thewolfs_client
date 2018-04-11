package esprit.javafx;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ning.http.util.ProxyUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.entity.User;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;
import tn.esprit.thewolfs_server.services.UserServiceRemote;

public class ShowTradersController implements Initializable{
	
	@FXML
    private TableView<Trader> tableviewtrader;

    @FXML
    private TableColumn<Trader, ?> firstnamecol;

    @FXML
    private TableColumn<Trader, ?> lastnamecol;

   
    @FXML
    private Button viewBtn;
    @FXML
    private Button communicatebtn;
    @FXML
    private Label firsttf;
    @FXML
    private Label lasttf;
    @FXML
    private Label leveltf;
    @FXML
    private Label emailtf;
 
    ObservableList<Trader> data = FXCollections.observableArrayList();
 
    public static String firstname,lastname;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
        String jndinameU="thewolfs_server-ear/thewolfs_server-ejb/UserService!tn.esprit.thewolfs_server.services.UserServiceRemote";
		try {
			Context context = new InitialContext();
			Context contextU= new InitialContext();
			UserServiceRemote proxyUser=(UserServiceRemote) contextU.lookup(jndinameU);
			TraderServiceRemote proxy = (TraderServiceRemote) context.lookup(jndiname);
        	List<Trader> traders = proxy.dislayTrader();
			firstnamecol.setCellValueFactory(new PropertyValueFactory<>("first_name"));
			lastnamecol.setCellValueFactory(new PropertyValueFactory<>("last_name"));
			ObservableList<Trader> items = FXCollections.observableArrayList(traders);
			tableviewtrader.setItems(items);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		tableviewtrader.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue)->showdetails(newValue)));}
		public void showdetails(Trader trader) {
			firsttf.setText(trader.getFirst_name());
			lasttf.setText(trader.getLast_name());
			emailtf.setText(trader.getEmail());
			leveltf.setText(trader.getLevel().name());
			firstname=trader.getFirst_name();
			lastname=trader.getLast_name();
		}
	 @FXML
	    void OnCommunicate(ActionEvent event) throws IOException {
		 /*
		  Account account=new Account(Float.parseFloat(amountTF.getText()), currencyCB.getValue(), isActiveCB.getValue());
    			account.setTrader(proxyTrader.findTraderById(idTraderConnected));
    			int idAccount = proxy.addAccount(account); */
		   String jndinameU="thewolfs_server-ear/thewolfs_server-ejb/UserService!tn.esprit.thewolfs_server.services.UserServiceRemote";
			try {
		 Context contextU= new InitialContext();
			UserServiceRemote proxyUser=(UserServiceRemote) contextU.lookup(jndinameU);
		User client =proxyUser.findUserById(1);
		//System.out.println(client);
		Trader trader=tableviewtrader.getSelectionModel().getSelectedItem();
		
		client.setTrader(trader);
		  proxyUser.updateUser(client);}
		catch (NamingException e) {
			e.printStackTrace();}
		
		
		Parent root = FXMLLoader.load(getClass().getResource("ChoixTrader.fxml"));
 		Scene newScene= new Scene(root);
 		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
 		window.setScene(newScene);
 		window.show();
 		 }	
	}


