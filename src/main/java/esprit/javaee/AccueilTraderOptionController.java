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

	public ObservableList<Options> list = FXCollections.observableArrayList();
	public static int id_trader_co = 1;
	@FXML
    private TableView<Options> TableOptionsAcc;

    @FXML
    private TableColumn<Options, Date> date;
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
			if(arr.get(i).getCounterparty()==null)
			{
			
				proxy.UpdateOptionCounterparty(arr.get(i).getId(),proxy.findTraderById(id_trader_co));
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
			else{
				label.setText("This option is already sold");
			}
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
    }
    
    public void afficher(OptionsRemote proxy){
    	
   	 TableOptionsAcc.getItems().clear();
   		System.out.println("test");
   	    arr= proxy.findOptionsValid(Status.Valid);
   		System.out.println(arr);
   		
       	for (int i=0;i<arr.size();i++){
       	list.add(arr.get(i));	
       	}
       	date.setCellValueFactory(new PropertyValueFactory<Options, Date>("expiration_date"));
       	PremiumPrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("premium_price"));
       	StrikePrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("strike_price"));
       	type.setCellValueFactory(new PropertyValueFactory<Options, String>("type"));
       	asset.setCellValueFactory(new PropertyValueFactory<Options, Integer>("asset"));
       	
       	TableOptionsAcc.setItems(list);
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
			
			
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
    }    
    
}
