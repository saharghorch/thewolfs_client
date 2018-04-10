/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.javaee;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import notification.Notifications;
import notification.TrayNotification;
import javafx.fxml.FXML;
import tn.esprit.thewolfs_server.entity.Asset;
import tn.esprit.thewolfs_server.entity.Options;
import tn.esprit.thewolfs_server.entity.Status;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.entity.Type;
import tn.esprit.thewolfs_server.entity.User;
import tn.esprit.thewolfs_server.services.OptionsRemote;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author sahar ghorch
 */
public class TraderOptionController implements Initializable {
	@FXML
    private Button ButtonAsTrader;

    @FXML
    private Button ButtonAsCounterparty;
	@FXML
    private TableView<Options> TableOptionsAcc;

    @FXML
    private TableColumn<Options, Date> date;

    @FXML
    private TableColumn<Options, Float> PremiumPrice;

    @FXML
    private TableColumn<Options, Float> StrikePrice;
    @FXML
    private TableColumn<Options, Float> StockPrice;

    @FXML
    private TableColumn<Options,Float> volatility;

    @FXML
    private TableColumn<Options, String> time_to_expiry;
    @FXML
    private TableColumn<Options, String> type;
    @FXML
    private TableColumn<Options, String> status;
    @FXML
    private DatePicker date_exp;

    @FXML
    private TableColumn<Options, Integer> asset;
    List<Options> arr = new ArrayList();
    List<Asset> assetType = new ArrayList();
    public ObservableList<Options> list = FXCollections.observableArrayList();
    public ObservableList<Type> comboList = FXCollections.observableArrayList(Type.Call,Type.Put);
    public ObservableList<Integer> comboListAsset = FXCollections.observableArrayList();
    @FXML
    private TextField PemiumPrice;
    
    @FXML
    private TextField StrikePricee;

    @FXML
    private ComboBox<Integer> ComboAsset;

    @FXML
    private ComboBox<Type> ComboType;
public static int id_trader_co = 1;
    @FXML
    private Button AddOption;
    @FXML
    void OnActionAsCounterparty(ActionEvent event) {
ComboType.setItems(comboList);
    	
    	
    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
		
			e.printStackTrace();
		}
		try {
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			afficherCounterparty(proxy);;
			ComboAsset.setItems(comboListAsset);
			
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
    }

    @FXML
    void OnActionAsTrader(ActionEvent event) {
ComboType.setItems(comboList);
    	
    	
    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		try {
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			afficher(proxy);
			ComboAsset.setItems(comboListAsset);
			
		} catch (NamingException e) {
	
			e.printStackTrace();
		}
		
    }
    @FXML
    void AddOption(ActionEvent event) {
    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			
			context = new InitialContext();
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
		try {
			
		
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			
			Options option = new Options();
			
option.setStrike_price(Float.parseFloat(StrikePricee.getText()));

option.setPremium_price(Float.parseFloat(PemiumPrice.getText()));
option.setType(ComboType.getValue());
option.setAsset(assetType.get(ComboType.getSelectionModel().getSelectedIndex()));
option.setExpiration_date(new java.util.Date(date_exp.getValue().getYear()-1900, date_exp.getValue().getMonthValue()-1, date_exp.getValue().getDayOfMonth()));
option.setStatus(Status.onHold);
option.setTrader(proxy.findTraderById(id_trader_co));
System.out.println(option.getAsset());
proxy.addOption(option);
System.out.println("ajout avec succes");
afficher(proxy);
String title = "Congratulations sir";
String message = "You've successfully added an option, it's now on hold of confirmation.";
Notifications notification = Notifications.SUCCESS;

TrayNotification tray = new TrayNotification();
tray.setTitle(title);
tray.setMessage(message);
tray.setNotification(notification);
tray.showAndWait();
}
		catch (NamingException e) {
		
			e.printStackTrace();
		}
    }

    @FXML
    void OnActionComboAsset(ActionEvent event) {

    }

    @FXML
    void OnActionComboType(ActionEvent event) {

    }
 public void afficher(OptionsRemote proxy){
    	
	 TableOptionsAcc.getItems().clear();
	 

	
		arr= proxy.findOptionsByTrader(id_trader_co);
		
		for (int i=0;i<arr.size();i++){
	    	String aa = proxy.TimeToExpiry(arr.get(i).getExpiration_date());
	    	if (Integer.parseInt(aa)>0){
	        arr.get(i).setTime_to_expiry(proxy.TimeToExpiry(arr.get(i).getExpiration_date()));
	    	}
	    	else {
	    		String e = "Expiré";
	    	
	    		arr.get(i).setTime_to_expiry(e);
	    		
	    		
	    	}
	    	list.add(arr.get(i));	
	    	}
    	assetType=proxy.findAssetType();
    	
    	for(int j=0 ; j<assetType.size() ; j++)
    	{
    		comboListAsset.add(assetType.get(j).getId());
    	}
    	
    	
    	date.setCellValueFactory(new PropertyValueFactory<Options, Date>("expiration_date"));
    	PremiumPrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("premium_price"));
    	StrikePrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("strike_price"));
    	type.setCellValueFactory(new PropertyValueFactory<Options, String>("type"));
    	asset.setCellValueFactory(new PropertyValueFactory<Options, Integer>("asset"));
    	status.setCellValueFactory(new PropertyValueFactory<Options, String>("status"));
    	StockPrice.setCellValueFactory(new PropertyValueFactory<Options,Float>("stock_price"));
     	volatility.setCellValueFactory(new PropertyValueFactory<Options,Float>("volatility"));
     	time_to_expiry.setCellValueFactory(new PropertyValueFactory<Options,String>("time_to_expiry"));
    	TableOptionsAcc.setItems(list);
    }
 public void afficherCounterparty(OptionsRemote proxy){
 	
	 TableOptionsAcc.getItems().clear();
	
		arr= proxy.findOptionsByCounterparty(id_trader_co);
		System.out.println(arr);
		for (int i=0;i<arr.size();i++){
	    	String aa = proxy.TimeToExpiry(arr.get(i).getExpiration_date());
	    	if (Integer.parseInt(aa)>0){
	        arr.get(i).setTime_to_expiry(proxy.TimeToExpiry(arr.get(i).getExpiration_date()));
	    	}
	    	else {
	    		String e = "Expiré";
	    	
	    		arr.get(i).setTime_to_expiry(e);
	    		
	    		
	    	}
	    	list.add(arr.get(i));	
	    	}
    	assetType=proxy.findAssetType();
    	
    	for(int j=0 ; j<assetType.size() ; j++)
    	{
    		comboListAsset.add(assetType.get(j).getId());
    	}
    	
    	
    	date.setCellValueFactory(new PropertyValueFactory<Options, Date>("expiration_date"));
    	PremiumPrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("premium_price"));
    	StrikePrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("strike_price"));
    	type.setCellValueFactory(new PropertyValueFactory<Options, String>("type"));
    	asset.setCellValueFactory(new PropertyValueFactory<Options, Integer>("asset"));
    	status.setCellValueFactory(new PropertyValueFactory<Options, String>("status"));
    	TableOptionsAcc.setItems(list);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	ComboType.setItems(comboList);
    	
    	
    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
	
			e.printStackTrace();
		}
		try {
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			afficher(proxy);
			ComboAsset.setItems(comboListAsset);
			
		} catch (NamingException e) {
		
			e.printStackTrace();
		}
		
    }    
    
}
