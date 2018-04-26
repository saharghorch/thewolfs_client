package esprit.javafx;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.w3c.dom.events.MouseEvent;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import notification.Notifications;
import notification.TrayNotification;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Asset;
import tn.esprit.thewolfs_server.services.AssetServiceRemote;



public class FXMLTrader2Asset  implements Initializable {
    @FXML
    private Label sharesnumberlabel;
    @FXML
    private Button calcul;
    

    @FXML
    private Label tradersharesvalue;
 
    @FXML
    private Label head;
    @FXML
    private TableColumn<Asset,java.sql.Date> expirationdatecol;
    @FXML
    private TableColumn<Asset,java.sql.Date> startdatecol;
    @FXML
    private TableColumn<Asset, Integer> sharesnumbercol;
    @FXML
    private TableColumn<Asset, Double> totalvaluecol;
    @FXML
    private TextField TFtradersharesnumber;
    @FXML
    private TextField TFtotalvalue;
    @FXML
    private Button addbtn;
    @FXML
    private Label startdatelabel;
    @FXML
    private Label expirationdatelabel;
    @FXML
    private Button backbtn;
    @FXML
    private DatePicker picstartdate;
    @FXML
    private TextField recherche;
    Integer idtrader=Session.getUser().getId();

    @FXML
    private Button buy;
    @FXML
    void mouse(MouseEvent event) {
    }
    @FXML
    private DatePicker picexpirationdate;
    @FXML
    private TableView<Asset> tableview = new TableView<Asset>();
    private ObservableList<Asset> data1 = FXCollections.observableArrayList();
    private ObservableList<Asset> data2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	try {
			remplir();
			
			String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
	        Context ctx = new InitialContext();
	        AssetServiceRemote proxy = (AssetServiceRemote) ctx.lookup(jndiName);

			List<Asset> asset = proxy.displayAllAssets();
			for (Asset ass : asset) {
				data2.add(ass);
			}
			
	    	recherche.textProperty().addListener((observable, oldValue, newValue) -> {
				
				Integer v = new Integer(newValue);
				data2.clear();
				data2.setAll(asset.stream().filter(e -> e.getSharesNumber()>= v).collect(Collectors.toList()));
				tableview.setItems(data2);
				System.out.println("done");
	     });
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }    

  @FXML
    public void remplir() throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
        Context ctx = new InitialContext();
        AssetServiceRemote proxy = (AssetServiceRemote) ctx.lookup(jndiName);
        sharesnumbercol.setCellValueFactory(new PropertyValueFactory<>("sharesNumber"));
        expirationdatecol.setCellValueFactory(new PropertyValueFactory<>("optionExpirationDate"));
        startdatecol.setCellValueFactory(new PropertyValueFactory<>("optionStartDate"));
        totalvaluecol.setCellValueFactory(new PropertyValueFactory<>("totalValue"));
      Asset c = new Asset();
        List<Asset> o = proxy.displayAllAssets();
        
		for (Asset  asset : o) {
			data1.add(asset);
			System.out.print("qqqqqqqq");
		}
		//tableviewasset.refresh();
		tableview.setItems(data1);	
	}
    
    @FXML
    void calculer(ActionEvent event) throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
    	Context ctx = new InitialContext();
    	AssetServiceRemote proxy=(AssetServiceRemote) ctx.lookup (jndiName);
            if (tableview.getSelectionModel().getSelectedItem() != null) {
                Asset a = tableview.getSelectionModel().getSelectedItem();
                Integer cc;
                cc=Integer.valueOf(TFtradersharesnumber.getText());
                if(cc>a.getSharesNumber()){
                	Alert alert = new Alert(AlertType.WARNING);
	    			alert.setTitle(" you cannot calculate shares value  ");
	    			alert.setHeaderText("Enter a valid numbers");
	    			alert.showAndWait();
                }
                else if (cc<a.getSharesNumber())
                {Integer aa;
                aa=a.getSharesNumber();
                Double bb;
                bb=a.getTotalValue();
               ;
                Double dd;
                dd=(cc*bb)/aa;
                System.out.println(dd);
                String  dds=Double.toString(dd);
                tradersharesvalue.setText(" Entered shares value is "+dds);}
    }
    }
 public void mouse() {
	if (tableview.getSelectionModel().getSelectedItem() !=null)
	{
		Asset a = tableview.getSelectionModel().getSelectedItem();
	//	TFtradersharesnumber.setText(a.getShares_number()+"");
	}
}
 
 @FXML
 void back(ActionEvent event) {

 	 Parent root;
 		try {
 			root = FXMLLoader.load(getClass().getResource("/esprit/javafx/FXMLTraderAsset.fxml"));
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
 void buy(ActionEvent event) throws NamingException{
	 String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
 	Context ctx = new InitialContext();
 	AssetServiceRemote proxy=(AssetServiceRemote) ctx.lookup (jndiName);
         if (tableview.getSelectionModel().getSelectedItem() != null) {
             Asset a = tableview.getSelectionModel().getSelectedItem();
             Integer idaccount;
             idaccount=proxy.getAccountbyTrader(idtrader);
             Account acc=proxy.getAccountById(idaccount);
             Integer cc;
             Integer aa;
             aa=a.getSharesNumber();
             Double bb;
             bb=a.getTotalValue();
             cc=Integer.valueOf(TFtradersharesnumber.getText());
             Double dd;
             dd=(cc*bb)/aa;
             if(acc.getAmount()>dd)
            	 
             {  
            	 Double diff;
       	      diff=(double) (acc.getAmount()-Integer.valueOf(TFtradersharesnumber.getText()));
       	      a.setSharesNumber(a.getSharesNumber()-Integer.valueOf(TFtradersharesnumber.getText()));
       	   sharesnumbercol.setCellValueFactory(new PropertyValueFactory<>("Shares_number"));
       	      acc.setAmount(Float.parseFloat(diff.toString()));
            	 String res=diff.toString();
            	 String title = "Congratulations ";
         	String message = "this Asset is bought you still have " + res  ;
        	Notifications notification = Notifications.SUCCESS;
        	TrayNotification tray = new TrayNotification();
        	tray.setTitle(title);
        	tray.setMessage(message);
        	tray.setNotification(notification);
        	tray.showAndWait(); 
        	     
        	  	            } 
        	     
             else if (acc.getAmount()<dd)
             {   String title = "Sorry ";
          	String message = "You cannot buy this asset";
         	Notifications notification = Notifications.SUCCESS;
         	TrayNotification tray = new TrayNotification();
         	tray.setTitle(title);
         	tray.setMessage(message);
         	tray.setNotification(notification);
         	tray.showAndWait(); }
                  }
         

}

}