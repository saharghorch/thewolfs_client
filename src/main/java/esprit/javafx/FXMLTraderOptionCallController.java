package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.mail.imap.protocol.MailboxInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Activity;
import tn.esprit.thewolfs_server.entity.Currency;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class FXMLTraderOptionCallController implements Initializable{
	  @FXML
	    private TextField CallOptionPriceTF;

	    @FXML
	    private TableView<Account> tableview;

	    @FXML
	    private TableColumn<Account, ?> amountAccountCol;

	    @FXML
	    private TableColumn<Account, ?> currencyAccountCol;

	    @FXML
	    private TableColumn<Account, ?> isActiveAccountCol;
	    
      @FXML
	    private Button validateAccountBtn;

	    @FXML
	    private Button ignoreCallOptionBtn;
	    
	    @FXML
	    private Button ByCallOptionBtn;
	    
	    SendMail mail=new SendMail();
	  
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	CallOptionPriceTF.setText(FXMLTraderOptionController.callOptionPriceStatic.toString());
			
		}

	    @FXML
	    void ByCallOption(ActionEvent event) throws NamingException {
	    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
        	Context context;
        	context = new InitialContext();
			AccountServiceRemote proxy;
			proxy = (AccountServiceRemote) context.lookup(jndiName);
			
			
			Integer idTraderConnected=Session.getUser().getId();
			List<Account> accounts= proxy.findAllAccountByTrader(idTraderConnected);
			List<Account> traderAccounts=new ArrayList<Account>();
			for(Account account:accounts){
				if(account.getIsActive().equals(Activity.Yes)){
					traderAccounts.add(account);
				}
			}
			
			amountAccountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));;
        	currencyAccountCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
        	isActiveAccountCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
            ObservableList<Account> items = FXCollections.observableArrayList(traderAccounts);
	        tableview.setItems(items);
	      //mail.send(email, amountCallOption);
	    
	    }

	  
	   
			
		

		@FXML
	    void ignoreCallOption(ActionEvent event) throws IOException {
	    	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Call Option Pricing");
			alert.setHeaderText("No option traded");
			alert.showAndWait();
			Parent root = FXMLLoader.load(getClass().getResource("FXMLTraderOption.fxml"));
			Scene newScene = new Scene(root);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(newScene);
			window.show();
	    }

	    @FXML
	    void validateAccount(ActionEvent event) throws NamingException {
	    	Account newValue=tableview.getSelectionModel().getSelectedItem();
			if(newValue.getCurrency().equals(Currency.EUR)){
				if(newValue.getAmount()<FXMLTraderOptionController.callOptionPriceStatic){
					Alert alert = new Alert(AlertType.WARNING);
	    			alert.setTitle(" Call Option Failed ");
	    			alert.setHeaderText("you don't have enough money to buy this option");
	    			alert.showAndWait();	
				}
				else{
					//notification Trader
					
					String jndiNameTrader="thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
			    	Context contextTrader =new InitialContext();
			    	TraderServiceRemote proxyTrader=(TraderServiceRemote) contextTrader.lookup(jndiNameTrader);
					Float lastAmount=newValue.getAmount();
					Float newAmount=(float) (lastAmount-FXMLTraderOptionController.callOptionPriceStatic);
				    Account account=new Account(newAmount, newValue.getCurrency(),newValue.getIsActive());
	    			Integer idTraderConnected=Session.getUser().getId();
	    			account.setId(newValue.getId());
	            	account.setTrader(proxyTrader.findTraderById(idTraderConnected));
	            	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
	            	Context context;
	            	context = new InitialContext();
	    			AccountServiceRemote proxy;
	    			proxy = (AccountServiceRemote) context.lookup(jndiName);
	            	proxy.updateAccount(account);
	                Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setTitle("Buy Call Option");
	    			alert.setHeaderText("Succesful :) A Confirmation Email will be sent ");
	    			alert.showAndWait();
				}
			}
			else if(newValue.getCurrency().equals(Currency.USD)){
				// Conversion en EURO et faire le même traitement précédent 
			}

	    }

}
