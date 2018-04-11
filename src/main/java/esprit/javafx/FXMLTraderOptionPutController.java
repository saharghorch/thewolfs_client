package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
import tn.esprit.thewolfs_server.services.AccountServiceRemote;

public class FXMLTraderOptionPutController implements Initializable {
	
       @FXML
	    private TextField PutOptionPriceTF;

       @FXML
	    private TableView<Account> tableview;

	    @FXML
	    private TableColumn<Account, ?> amountAccountCol;

	    @FXML
	    private TableColumn<Account, ?> currencyAccountCol;

	    @FXML
	    private TableColumn<Account, ?> isActiveAccountCol;

	    @FXML
	    private Button ByPutOptionBtn;
	    
	    @FXML
	    private Button validateAccountBtn;

	    @FXML
	    private Button ignorePutOptionBtn;
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	PutOptionPriceTF.setText(FXMLTraderOptionController.putOptionPriceStatic.toString());
			
		}

	    @FXML
	    void ByPutOption(ActionEvent event) throws NamingException {
	    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
        	Context context;
        	context = new InitialContext();
			AccountServiceRemote proxy;
			proxy = (AccountServiceRemote) context.lookup(jndiName);
			Integer idTraderConnected=Session.getUser().getId();
			List<Account> accounts= proxy.findAllAccountByTrader(idTraderConnected);
			amountAccountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));;
        	currencyAccountCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
        	isActiveAccountCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
            ObservableList<Account> items = FXCollections.observableArrayList(accounts);
	        tableview.setItems(items);
	    }

	    @FXML
	    void ignorePutOption(ActionEvent event) throws IOException {
	    	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Put Option Pricing");
			alert.setHeaderText("No option traded");
			alert.showAndWait();
			Parent root = FXMLLoader.load(getClass().getResource("FXMLTraderOption.fxml"));
			Scene newScene = new Scene(root);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(newScene);
			window.show();

	    }

	    @FXML
	    void validateAccount(ActionEvent event) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
  			alert.setTitle("Buy Put Option");
  			alert.setHeaderText("Succesful :) A Confirmation Email will be sent ");
  			alert.showAndWait();
	    }

		
	  

	  
	
	

}
