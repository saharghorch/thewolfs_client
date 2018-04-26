package esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Options;
import tn.esprit.thewolfs_server.entity.Portfolio;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.WatchlistServiceRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
public class FXMLTraderWatchlistController implements Initializable {



    @FXML
    private TableColumn<Options, ?> StrikepriceCol;
    
    @FXML
    private TextField optionStrikePriceTF;

    @FXML
    private DatePicker creationDateOptionTF;
    
    @FXML
    private TextField evaluationTF;

    @FXML
    private Button evaluateOptionBtn;

    @FXML
    private TableView<Options> tableview;

    @FXML
    private TableColumn<Options, ?> underlyingPriceCol;

    @FXML
    private TableColumn<Options, ?> volatilityCol;

    @FXML
    private TableColumn<Options, ?> interestRateCol;
    

    @FXML
    private TableColumn<Options, ?> ExpiryCol;

    @FXML
    private TableColumn<Options, ?> statusCol;

    @FXML
    private TableColumn<Options, ?> evaluationCol;

    @FXML
    private TableColumn<Options, ?> probSuccessCol;

    @FXML
    private TableColumn<Options, ?> timeMoneyCol;
    
    @FXML
    private TableColumn<Options, ?> typeCol;

    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/WatchlistService!tn.esprit.thewolfs_server.services.WatchlistServiceRemote";
		Context context;
		try {
		    context = new InitialContext();
			WatchlistServiceRemote proxy;
			proxy = (WatchlistServiceRemote) context.lookup(jndiName);
			
		   List<Options> options=new ArrayList<Options>();
		   options=proxy.findOptionsOnHold();
		   for(Options op:options){
			   proxy.calculprobabilite(op.getId());
			   proxy.evaluer(op.getId());
		   }
		  
		   
			underlyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock_price"));
			volatilityCol.setCellValueFactory(new PropertyValueFactory<>("volatility"));;
			statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
			evaluationCol.setCellValueFactory(new PropertyValueFactory<>("evaluation"));
			probSuccessCol.setCellValueFactory(new PropertyValueFactory<>("successProbability"));
			timeMoneyCol.setCellValueFactory(new PropertyValueFactory<>("timeMoney"));
			typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
			StrikepriceCol.setCellValueFactory(new  PropertyValueFactory<>("strike_price"));
			ExpiryCol.setCellValueFactory(new  PropertyValueFactory<>("time_to_expiry"));

			ObservableList<Options> items = FXCollections.observableArrayList(options);
	        tableview.setItems(items);
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
		
	}


	
	

   

	}


