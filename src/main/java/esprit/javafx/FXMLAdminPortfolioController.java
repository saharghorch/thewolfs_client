package esprit.javafx;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.thewolfs_server.entity.Portfolio;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.services.PortfolioServiceRemote;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class FXMLAdminPortfolioController implements Initializable {
	 
	    @FXML
	    private TableView<Portfolio> tableview;
	    @FXML
	    private TableColumn<Portfolio, ?> idPortfolioCol;
	    @FXML
	    private TableColumn<Portfolio, ?> creationDatePortfolioCol;
	    @FXML
	    private TableColumn<Portfolio, ?> cashPortfolioCol;
	   
        @FXML
	    private TextField findPortfolioTF;
	    @FXML
	    private Button findPortfolioBtn;

	    /**
	     * Initializes the controller class.
	     */
	    @Override
	    public void initialize(URL url, ResourceBundle rb){
	    	Context context;
			try {
				String jndiName="thewolfs_server-ear/thewolfs_server-ejb/PortfolioService!tn.esprit.thewolfs_server.services.PortfolioServiceRemote";
				context = new InitialContext();
				PortfolioServiceRemote proxy=(PortfolioServiceRemote) context.lookup(jndiName);
				List<Portfolio> portfolios;
				portfolios=proxy.displayAllPortfolios();
				idPortfolioCol.setCellValueFactory(new PropertyValueFactory<>("id"));
				creationDatePortfolioCol.setCellValueFactory(new PropertyValueFactory<>("creation_date"));
				cashPortfolioCol.setCellValueFactory(new PropertyValueFactory<>("cash"));
				
				ObservableList<Portfolio> items = FXCollections.observableArrayList(portfolios);
		        tableview.setItems(items); 
		       
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
			tableview.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {showdetails(newValue);}));
	    }    

	    private void showdetails(Portfolio newValue) {
	 	
		}

	    @FXML
	    void findPortfolioByCash(ActionEvent event)throws NamingException {
	    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/PortfolioService!tn.esprit.thewolfs_server.services.PortfolioServiceRemote";
			Context context=new InitialContext();
			PortfolioServiceRemote proxy=(PortfolioServiceRemote) context.lookup(jndiName);
			if (findPortfolioTF.getText().equals("")){
	        	Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("Portfolio searching");
	    		alert.setHeaderText("you have to enter a portfolio's cash");
	    		alert.showAndWait();
	        
	        }
	        else{
			List<Portfolio> portfolios = proxy.findPortfolioByCash(Float.parseFloat(findPortfolioTF.getText()));
			ObservableList<Portfolio> items = FXCollections.observableArrayList(portfolios);
			tableview.setItems(items);
			findPortfolioTF.setText("");
	        }

	    }
	    
	}



