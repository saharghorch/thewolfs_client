package esprit.javafx;
import notification.Notifications;
import notification.TrayNotification;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Asset;
import tn.esprit.thewolfs_server.entity.Trader;
import javafx.scene.control.Label;
import tn.esprit.thewolfs_server.entity.Options;
import tn.esprit.thewolfs_server.entity.Portfolio;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.AssetServiceRemote;
import tn.esprit.thewolfs_server.services.WatchlistServiceRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
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
import javax.persistence.EntityManager;


import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
public class FXMLTraderWatchlistController implements Initializable {
    @FXML
    private Label head;


    @FXML
    private TableColumn<Options, ?> StrikepriceCol;

    @FXML
    private Label labelproba;

    @FXML
    private Button proba;
    @FXML
    private Button backbtn;



    @FXML
    private Button chart;
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
    Integer idtrader=Session.getUser().getId();

    

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
			   System.out.println( idtrader);
		   }
		  
		   
			underlyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock_price"));
			volatilityCol.setCellValueFactory(new PropertyValueFactory<>("volatility"));;
			statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
			evaluationCol.setCellValueFactory(new PropertyValueFactory<>("evaluation"));
			probSuccessCol.setCellValueFactory(new PropertyValueFactory<>("successProbability"));
			typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
			StrikepriceCol.setCellValueFactory(new  PropertyValueFactory<>("strike_price"));
			ExpiryCol.setCellValueFactory(new  PropertyValueFactory<>("time_to_expiry"));

			ObservableList<Options> items = FXCollections.observableArrayList(options);
	        tableview.setItems(items);
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
		
	}
	

	 @FXML
	    void add(ActionEvent event) throws NamingException {
		 String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/WatchlistService!tn.esprit.thewolfs_server.services.WatchlistServiceRemote";
		 Context ctx = new InitialContext();
	WatchlistServiceRemote proxy = (WatchlistServiceRemote) ctx.lookup(jndiName);

if (tableview.getSelectionModel().getSelectedItem() != null) {
	Options op = tableview.getSelectionModel().getSelectedItem();
	Integer  idaccount , idport;
	idport=0;
	
	idaccount=proxy.getAccountbyTrader(idtrader);
Account acc=proxy.getAccountById(idaccount);

if (acc.getAmount()>op.getStrike_price())
	{idport=proxy.getPortfoliobyTrader(idtrader);
	String title = "Congratulations ";
	String message = "this has been added to your Portfolio";
	Notifications notification = Notifications.SUCCESS;
	TrayNotification tray = new TrayNotification();
	tray.setTitle(title);
	tray.setMessage(message);
	tray.setNotification(notification);
	tray.showAndWait();
//System.out.println( op);
System.out.println( "ADDED with success");

proxy.Updateopport(op.getId(), idport);}

else {String title = "Error ";
String message = "Sorry your money amout is not enough ";
Notifications notification = Notifications.SUCCESS;
TrayNotification tray = new TrayNotification();
tray.setTitle(title);
tray.setMessage(message);
tray.setNotification(notification);
tray.showAndWait();}
} 

	    }
	
	    @FXML
	    void back(ActionEvent event) {

	    	 Parent root;
	    		try {
	    			root = FXMLLoader.load(getClass().getResource("/esprit/javafx/Accueil.fxml"));
	    			 Scene scene=new Scene(root);
	    			 Stage st=new Stage();
	    			 st.setScene(scene);
	    			 st.show();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}

	    		 
	    		 Stage stage=(Stage) backbtn.getScene().getWindow();
	    		 stage.close();

	    	 }
	    @FXML
	    void chart(ActionEvent event) {

	    	 Parent root;
	    		try {
	    			root = FXMLLoader.load(getClass().getResource("/esprit/javafx/Statistics.fxml"));
	    			 Scene scene=new Scene(root);
	    			 Stage st=new Stage();
	    			 st.setScene(scene);
	    			 st.show();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}

	    		 
	    		 Stage stage=(Stage) chart.getScene().getWindow();
	    		 stage.close();

	    	 }
	 
	}


