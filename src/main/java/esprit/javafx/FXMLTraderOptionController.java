package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Status;
import tn.esprit.thewolfs_server.entity.StockOption;
import tn.esprit.thewolfs_server.entity.Type;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.PricingRemote;
import tn.esprit.thewolfs_server.services.StockOptionServiceRemote;
import javafx.scene.Node;

public class FXMLTraderOptionController implements Initializable{


    @FXML
    private Button pricingOptionBTN;

    @FXML
    private DatePicker expirationDateDP;

    @FXML
    private TextField strikePriceTF;

    @FXML
    private TableView<StockOption> tableview;

    @FXML
    private TableColumn<StockOption, ?> symboleCol;

    @FXML
    private TableColumn<StockOption, ?> underlyingPriceCol;

    @FXML
    private TableColumn<StockOption, ?> volatilityCol;

    @FXML
    private TableColumn<StockOption, ?> interestRateCol;

    @FXML
    private TableColumn<StockOption, ?> typeCol;

    
    public  static Double callOptionPriceStatic=0.0d;
    public  static Double putOptionPriceStatic=0.0d;
    
    
    @Override
	public void initialize(URL url, ResourceBundle rb)  {
    	String jndiNameStockOption="thewolfs_server-ear/thewolfs_server-ejb/StockOptionService!tn.esprit.thewolfs_server.services.StockOptionServiceRemote";
    	Context contextStockOption;
		try {
			contextStockOption = new InitialContext();
			StockOptionServiceRemote proxyStockOption=(StockOptionServiceRemote) contextStockOption.lookup(jndiNameStockOption);
			symboleCol.setCellValueFactory(new PropertyValueFactory<>("symbole"));
			underlyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("underlyingPrice"));
			volatilityCol.setCellValueFactory(new PropertyValueFactory<>("volatility"));
			interestRateCol.setCellValueFactory(new PropertyValueFactory<>("riskFreeInterestRate"));
		    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
			
			List<StockOption> stockOptions=proxyStockOption.displayAllStockOptions();
			List<StockOption> options=new ArrayList<>();
			for(StockOption stock:stockOptions){
				if (stock.getStatus()==null){
				options.add(stock);
				}
			}
			ObservableList<StockOption> items = FXCollections.observableArrayList(options);
		    tableview.setItems(items);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
    
	}
  

      @FXML
      void pricingOption(ActionEvent event) throws IOException, NamingException {
      	LocalDate today = LocalDate.now();
  		LocalDate dateExpiration = expirationDateDP.getValue();
    	  if (today.isAfter(dateExpiration)) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("  ERROR ");
            al.setHeaderText("Inconvenable Expiration Date ");
            al.showAndWait();
            expirationDateDP.setValue(null);
            strikePriceTF.setText("");
  	  }
    	  else{
    	
    	String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/Pricing!tn.esprit.thewolfs_server.services.PricingRemote";
  		Context context = new InitialContext();
  		PricingRemote proxy = (PricingRemote) context.lookup(jndiName);
  		Calendar cal = Calendar.getInstance ();
        Date todayDate = cal.getTime();
  		LocalDate n = expirationDateDP.getValue();
  		Date date = Date.from(n.atStartOfDay(ZoneId.systemDefault()).toInstant());
  		long z = proxy.getDateDiff(todayDate, date, TimeUnit.DAYS);
  		double timeToExpiration = (double) z / 365;
  		Double s = tableview.getSelectionModel().getSelectedItem().getUnderlyingPrice();
  		Double x = Double.parseDouble(strikePriceTF.getText());
  		Double sigma = tableview.getSelectionModel().getSelectedItem().getVolatility()/100;
  		Double r = tableview.getSelectionModel().getSelectedItem().getRiskFreeInterestRate()/100;
  		Double t =timeToExpiration;
  		if(tableview.getSelectionModel().getSelectedItem().getType().equals(Type.Call)){
  		callOptionPriceStatic = proxy.CallOptionPrice(s, x, sigma, r, t);
    	Parent root = FXMLLoader.load(getClass().getResource("FXMLTraderOptionCall.fxml"));
  		Scene newScene = new Scene(root);
  		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
  		window.setScene(newScene);
  		window.show();	
  		}else{
  			putOptionPriceStatic = proxy.PutOptionPrice(s, x, sigma, r, t);
      		Parent root = FXMLLoader.load(getClass().getResource("FXMLTraderOptionPut.fxml"));
    		Scene newScene = new Scene(root);
    		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		window.setScene(newScene);
    		window.show(); 
  			
  		}
  	
    		  
    	  }
    	
	
		
    }


	

}
