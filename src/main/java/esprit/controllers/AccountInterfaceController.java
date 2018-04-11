package esprit.controllers;
//import java.awt.Button;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Activity;
import tn.esprit.thewolfs_server.entity.Currency;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class AccountInterfaceController implements Initializable{

	  
	    
    @FXML
    private Button addAccountBtn;

    @FXML
    private Button deleteAccountBtn;

    @FXML
    private Button updateAccountBtn;

    @FXML
    private TextField amountTF;

    @FXML
    private ComboBox<Currency> currencyCB;
    private ObservableList<Currency> liste1=FXCollections.observableArrayList(Currency.EUR,Currency.USD,Currency.SAR);

    @FXML
    private ComboBox<Activity> isActiveCB;
    private ObservableList<Activity> liste2=FXCollections.observableArrayList(Activity.Yes,Activity.No);

    @FXML
    private ComboBox<Integer> idTraderCB;
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
    
    @FXML
    private Button assignAccountToTraderBtn;
    
    @FXML
    private TextField idTraderTF;
    
    
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
	        	amountAccountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));;
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
       
		
	}


	@FXML
    void addAccount(ActionEvent event) throws NamingException {
    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
    	Context context=new InitialContext();
    	AccountServiceRemote proxy=(AccountServiceRemote) context.lookup(jndiName);
    	String jndiNameTrader="thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
    	Context contextTrader =new InitialContext();
    	TraderServiceRemote proxyTrader=(TraderServiceRemote) context.lookup(jndiNameTrader);
    
    	if(tableview.getSelectionModel().getSelectedItem() != null){
    		Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Account Adding Error ");
            alert.setHeaderText("Account already exists ");
            alert.showAndWait();
    	}
    	else{
    		if(amountTF.getText().equals("") || currencyCB.getValue()== null || isActiveCB.getValue()== null ){
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle(" Account Adding Error ");
    			alert.setHeaderText("you have at least an empty field");
    			alert.showAndWait();
    		}
    		else{
    			Account account=new Account(Float.parseFloat(amountTF.getText()), currencyCB.getValue(), isActiveCB.getValue());
    	       // DéclarationStatique
    			Integer idTrader=1;
    			account.setTrader(proxyTrader.findTraderById(idTrader));
    			int idAccount = proxy.addAccount(account);
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Account Adding");
    			alert.setHeaderText("Succesful :) ");
    			alert.showAndWait();
    			/*String jndiNameTrader="thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
    	    	Context contextTrader=new InitialContext();
    	    	TraderServiceRemote proxyTrader=(TraderServiceRemote) context.lookup(jndiNameTrader);
    	    	List<Trader> trader=proxyTrader.dislayTrader();*/
    	       
    		}
    		
    	}
            
    	 List<Account> list = proxy.displayAllAccounts();
        ObservableList<Account> items = FXCollections.observableArrayList(list);
        tableview.setItems(items);
        amountTF.setText("");
        currencyCB.setValue(null);
        isActiveCB.setValue(null);

    }

    @FXML
    void removeAccount(ActionEvent event) throws NamingException {
    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
    	Context context=new InitialContext();
    	AccountServiceRemote proxy=(AccountServiceRemote) context.lookup(jndiName);
    	if(tableview.getSelectionModel().getSelectedItem() == null){
    		Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Account Removing Error ");
            alert.setHeaderText("Account does not exist ");
            alert.showAndWait();
    	}
    	else{
    		int idAccount=tableview.getSelectionModel().getSelectedItem().getId();
        	proxy.removeAccount(idAccount);
        	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Account Removing");
			alert.setHeaderText("Succesful :) ");
			alert.showAndWait();
    		
    	}
    	
        List<Account> list = proxy.displayAllAccounts();
        ObservableList<Account> items = FXCollections.observableArrayList(list);
        tableview.setItems(items);
        amountTF.setText("");
        currencyCB.setValue(null);
        isActiveCB.setValue(null);

    }

    @FXML
    void updateAccount(ActionEvent event) throws NamingException {
    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
    	Context context=new InitialContext();
    	AccountServiceRemote proxy=(AccountServiceRemote) context.lookup(jndiName);
    	if(tableview.getSelectionModel().getSelectedItem() == null){
    		Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Account Updating Error ");
            alert.setHeaderText("Account does not exist ");
            alert.showAndWait();
    	}
    	
    	else{
    		if(amountTF.getText().equals("") || currencyCB.getValue()== null || isActiveCB.getValue()== null ){
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle(" Account Updating Error ");
    			alert.setHeaderText("you have at least an empty field");
    			alert.showAndWait();
    		}
    		else{
    			Account account=new Account(Float.parseFloat(amountTF.getText()), currencyCB.getValue(), isActiveCB.getValue());
            	account.setId(tableview.getSelectionModel().getSelectedItem().getId());
                proxy.updateAccount(account);
                Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Account Updating");
    			alert.setHeaderText("Succesful :) ");
    			alert.showAndWait();
    		}
    		
    	}
    	
        List<Account> list = proxy.displayAllAccounts();
        ObservableList<Account> items = FXCollections.observableArrayList(list);
        tableview.setItems(items);
        amountTF.setText("");
        currencyCB.setValue(null);
        isActiveCB.setValue(null);
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
    
    @FXML
    void assignAccountToTrader(ActionEvent event) throws NamingException{
    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
    	Context context=new InitialContext();
    	AccountServiceRemote proxy=(AccountServiceRemote) context.lookup(jndiName);
    	/*if(tableview.getSelectionModel().getSelectedItem() == null){
    		Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Assign Account To Trader Error ");
            alert.setHeaderText("Account does not exist ");
            alert.showAndWait();
    	}
    	else{
    	    String jndinameTrader = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
    		Context contextTrader = new InitialContext();
    		TraderServiceRemote proxyTrader = (TraderServiceRemote) context.lookup(jndinameTrader);
    		Trader trader=proxyTrader.findTraderById(Integer.parseInt(idTraderTF.getText()));
    		if(trader==null)
    		{System.out.println("nuuulll");
    		Alert alertErr = new Alert(Alert.AlertType.WARNING);
            alertErr.setTitle(" Assign Account To Trader Error ");
            alertErr.setHeaderText("Trader does not exist ");
            alertErr.showAndWait();	
    		}
    		else{
    			//proxy.assignAccountToTrader(tableview.getSelectionModel().getSelectedItem().getId(), Integer.parseInt(idTraderTF.getText()));
    			Integer idTrader=Integer.parseInt(idTraderTF.getText());
    			Trader traderF=proxyTrader.findTraderById(Integer.parseInt(idTraderTF.getText()));
    			Account account=tableview.getSelectionModel().getSelectedItem();
    			account.setTrader(traderF);
    			
    			Alert alertSucc = new Alert(AlertType.INFORMATION);
    			alertSucc.setTitle("Assign Account To Trader");
    			alertSucc.setHeaderText("Succesful :) ");
    			alertSucc.showAndWait();
    			
    		}
    	}*/
    	List<Account> list = proxy.displayAllAccounts();
        ObservableList<Account> items = FXCollections.observableArrayList(list);
        tableview.setItems(items);
        amountTF.setText("");
        idTraderTF.setText("");
        currencyCB.setValue(null);
       isActiveCB.setValue(null);
    }
    
}
