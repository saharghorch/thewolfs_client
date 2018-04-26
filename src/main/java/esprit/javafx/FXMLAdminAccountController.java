package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import esprit.javafx.Session;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Activity;
import tn.esprit.thewolfs_server.entity.Currency;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class FXMLAdminAccountController  implements Initializable{

	  
	    
    @FXML
    private Button addAccountBtn;

    @FXML
    private Button deleteAccountBtn;

    @FXML
    private Button updateAccountBtn;

    @FXML
    private TextField amountTF;
    
    @FXML
    private TextField traderTF;
    
    

    @FXML
    private ComboBox<Currency> currencyCB;
    private ObservableList<Currency> liste1=FXCollections.observableArrayList(Currency.EUR,Currency.USD,Currency.SAR);

    @FXML
    private ComboBox<Activity> isActiveCB;
    private ObservableList<Activity> liste2=FXCollections.observableArrayList(Activity.Yes,Activity.No);

   
    @FXML
    private TableView<Account> tableview;

    @FXML
    private TableColumn<Account, ?> idAccountCol;

    @FXML
    private TableColumn<Account, ?> amountAccountCol;

    @FXML
    private TableColumn<Account, ?> currencyAccountCol;

    @FXML
    private TableColumn<Account, ?> isActiveAccountCol;
    
    @FXML
    private TableColumn<Account, ?> idTraderCol;
    
    @FXML
    private TextField searchAccountTF;
    
    @FXML
    private Button searchForAccountBtn;
    

   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
        	Context context;
			try {
				context = new InitialContext();
				AccountServiceRemote proxy;
				proxy = (AccountServiceRemote) context.lookup(jndiName);
			    List<Account> accounts= proxy.displayAllAccounts();
		        currencyCB.setItems(liste1);
	        	isActiveCB.setItems(liste2);
	        	idAccountCol.setCellValueFactory(new PropertyValueFactory<>("id"));
	        	amountAccountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        	currencyAccountCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
	        	isActiveAccountCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
	        	idTraderCol.setCellValueFactory(new PropertyValueFactory<>("trader"));
	            ObservableList<Account> items = FXCollections.observableArrayList(accounts);
		        tableview.setItems(items);
			} catch (NamingException e) {
				
				e.printStackTrace();
			}
                tableview.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                showdetails(newValue);}));
               
    }


    private void showdetails(Account newValue) {
        currencyCB.setValue(newValue.getCurrency());
    	amountTF.setText(Float.toString(newValue.getAmount()));
    	isActiveCB.setValue(newValue.getIsActive());
    	String trader=newValue.getTrader().getFirst_name()+" "+newValue.getTrader().getLast_name();
    	traderTF.setText(trader);
		
	}

    @FXML
    void findAccountByAmount(ActionEvent event) throws NamingException {
    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
    	Context context=new InitialContext();
    	AccountServiceRemote proxy=(AccountServiceRemote) context.lookup(jndiName);
    	if (searchAccountTF.getText().equals("")){
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Account searching");
    		alert.setHeaderText("you have to enter an amount");
    		alert.showAndWait();
        
        }
        else{
		List<Account> accounts = proxy.findAccountByAmount(Float.parseFloat(searchAccountTF.getText()));
		ObservableList<Account> items = FXCollections.observableArrayList(accounts);
		tableview.setItems(items);
		searchAccountTF.setText("");
		
        }

    }
    


    
 
    
}

