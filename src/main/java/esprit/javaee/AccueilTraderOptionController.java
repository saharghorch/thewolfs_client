/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.javaee;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import notification.Notifications;
import notification.TrayNotification;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AccueilTraderOptionController implements Initializable {
	List<Options> arr = new ArrayList();
	List<Options> arrr = new ArrayList();
	public ObservableList<Options> list = FXCollections.observableArrayList();
	public ObservableList<Options> list1 = FXCollections.observableArrayList();
	public static int id_trader_co = 1;
	@FXML
    private TableView<Options> TableOptionsAcc;
	@FXML
    private TableColumn<Options, Float> StockPrice;

    @FXML
    private TableColumn<Options,Float> volatility;

    @FXML
    private TableColumn<Options, String> time_to_expiry;
    @FXML
    private TableColumn<Options, Date> date;
    @FXML
    private TableView<Options> TableOptionsAcc1;
	@FXML
    private TableColumn<Options, Float> StockPrice1;

    @FXML
    private TableColumn<Options,Float> volatility1;

    @FXML
    private TableColumn<Options, String> time_to_expiry1;
    @FXML
    private TableColumn<Options, Date> date1;
    @FXML
    private TableColumn<Options, Float> PremiumPrice1;

    @FXML
    private TableColumn<Options, Float> StrikePrice1;
    @FXML
    private TableColumn<Options, Integer> asset1;
    @FXML
    private TableColumn<Options, String> type1;
    
    @FXML
    private Label label;
    @FXML
    private TableColumn<Options, Float> PremiumPrice;

    @FXML
    private TableColumn<Options, Float> StrikePrice;
    @FXML
    private TableColumn<Options, Integer> asset;
    @FXML
    private TableColumn<Options, String> type;
    @FXML
    private Button BuyOption;
    @FXML
    private Label amount;

public static int i;
@FXML
private Button MyOptions;
@FXML
void OnActionMyOptions(ActionEvent event) throws IOException {
	Parent page = FXMLLoader.load(getClass().getResource("TraderOption.fxml"));
    Scene scene = new Scene(page);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.hide();
    stage.setScene(scene);
    stage.show();
}
    @FXML
    void OnActionBuyOption(ActionEvent event) {
    	i = TableOptionsAcc.getSelectionModel().getSelectedIndex();
    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			context = new InitialContext();
		} catch (NamingException e1) {
		
			e1.printStackTrace();
		}
		try {
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			if(arr.get(i).getCounterparty()==null && arr.get(i).getPremium_price() <= proxy.FindAmountTrader(id_trader_co))
			{
			
				proxy.UpdateOptionCounterparty(arr.get(i).getId(),proxy.findTraderById(id_trader_co));
				float am = (float) ((proxy.FindAmountTrader(id_trader_co)) - (arr.get(i).getPremium_price()));
				proxy.UpdateAmount(id_trader_co, am);
				afficher(proxy);
				label.setText("");
				String title = "Congratulations sir";
				String message = "You've bought the option!";
				Notifications notification = Notifications.SUCCESS;

				TrayNotification tray = new TrayNotification();
				tray.setTitle(title);
				tray.setMessage(message);
				tray.setNotification(notification);
				tray.showAndWait();
				
			}
			else if(arr.get(i).getPremium_price() > proxy.FindAmountTrader(id_trader_co))
			{
				label.setText("insufficient amount");	
			}
		
			else{
				label.setText("This option is already sold");
			}
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
    }
  /*  public void afficherSold(OptionsRemote proxy){
    	
      	 TableOptionsAcc1.getItems().clear();
      	//amount.setText(Float.toString(proxy.FindAmountTrader(id_trader_co)));
      	//	System.out.println("test");
      	 arr= proxy.findOptionsValid(Status.Valid);
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
          	
          	date1.setCellValueFactory(new PropertyValueFactory<Options, Date>("expiration_date"));
          	PremiumPrice1.setCellValueFactory(new PropertyValueFactory<Options, Float>("premium_price"));
          	StrikePrice1.setCellValueFactory(new PropertyValueFactory<Options, Float>("strike_price"));
          	type1.setCellValueFactory(new PropertyValueFactory<Options, String>("type"));
          	asset1.setCellValueFactory(new PropertyValueFactory<Options, Integer>("asset"));
           StockPrice1.setCellValueFactory(new PropertyValueFactory<Options,Float>("stock_price"));
       	volatility1.setCellValueFactory(new PropertyValueFactory<Options,Float>("volatility"));
       	time_to_expiry1.setCellValueFactory(new PropertyValueFactory<Options,String>("time_to_expiry"));
          	TableOptionsAcc1.setItems(list);
          } */
    public void afficher(OptionsRemote proxy){
    	
   	 TableOptionsAcc.getItems().clear();
   	amount.setText(Float.toString(proxy.FindAmountTrader(id_trader_co)));
   		System.out.println("test");

   	    arr= proxy.findOptionsValid(Status.Valid);
   		System.out.println(arr);
   	 arrr= proxy.findOptionsValidSold(Status.Valid);
   		System.out.println(arrr);


       	for (int i=0;i<arr.size();i++){
       	list.add(arr.get(i));	
       	}
//Commentaire2:
   /*		for (int i=0;i<arrr.size();i++){
   	    	String aa = proxy.TimeToExpiry(arrr.get(i).getExpiration_date());
   	    	if (Integer.parseInt(aa)>0){
   	        arrr.get(i).setTime_to_expiry(proxy.TimeToExpiry(arrr.get(i).getExpiration_date()));
   	    	}
   	    	else {
   	    		String e = "Expiré";
   	    	
   	    		arrr.get(i).setTime_to_expiry(e);
   	    		
   	    		
   	    	}
   	    	list.add(arrr.get(i));	
   	    	}
*/
       	date.setCellValueFactory(new PropertyValueFactory<Options, Date>("expiration_date"));
       	PremiumPrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("premium_price"));
       	StrikePrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("strike_price"));
       	type.setCellValueFactory(new PropertyValueFactory<Options, String>("type"));
       	asset.setCellValueFactory(new PropertyValueFactory<Options, Integer>("asset"));
        StockPrice.setCellValueFactory(new PropertyValueFactory<Options,Float>("stock_price"));
    	volatility.setCellValueFactory(new PropertyValueFactory<Options,Float>("volatility"));
    	time_to_expiry.setCellValueFactory(new PropertyValueFactory<Options,String>("time_to_expiry"));
       	TableOptionsAcc.setItems(list);
       	
       	TableOptionsAcc1.getItems().clear();
      	//amount.setText(Float.toString(proxy.FindAmountTrader(id_trader_co)));
      	//	System.out.println("test");
      	 arr= proxy.findOptionsValid(Status.Valid);
      		System.out.println(arr);
     //Commentaire1 		
   /*   		for (int i=0;i<arr.size();i++){
      	    	String aa = proxy.TimeToExpiry(arr.get(i).getExpiration_date());
      	    	if (Integer.parseInt(aa)>0){
      	        arr.get(i).setTime_to_expiry(proxy.TimeToExpiry(arr.get(i).getExpiration_date()));
      	    	}
      	    	else {
      	    		String e = "Expiré";
      	    	
      	    		arr.get(i).setTime_to_expiry(e);
      	    		
      	    		
      	    	}
      	    	list1.add(arr.get(i));	
      	    	}
          	*/
          	date1.setCellValueFactory(new PropertyValueFactory<Options, Date>("expiration_date"));
          	PremiumPrice1.setCellValueFactory(new PropertyValueFactory<Options, Float>("premium_price"));
          	StrikePrice1.setCellValueFactory(new PropertyValueFactory<Options, Float>("strike_price"));
          	type1.setCellValueFactory(new PropertyValueFactory<Options, String>("type"));
          	asset1.setCellValueFactory(new PropertyValueFactory<Options, Integer>("asset"));
           StockPrice1.setCellValueFactory(new PropertyValueFactory<Options,Float>("stock_price"));
       	volatility1.setCellValueFactory(new PropertyValueFactory<Options,Float>("volatility"));
       	time_to_expiry1.setCellValueFactory(new PropertyValueFactory<Options,String>("time_to_expiry"));
          	TableOptionsAcc1.setItems(list1);
       }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
			//afficherSold(proxy);
			
			
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
    }    
    
}
