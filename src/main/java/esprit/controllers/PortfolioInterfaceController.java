package esprit.controllers;
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

public class PortfolioInterfaceController implements Initializable {
	    @FXML
	    private Button addPortfolioBtn;
	    @FXML
	    private Button deletePortfolioBtn;
	    @FXML
	    private Button updatePortfolioBtn;
	    @FXML
	    private DatePicker creationDatePortfolioTF;
	    @FXML
	    private TableView<Portfolio> tableview;
	    @FXML
	    private TableColumn<Portfolio, ?> idPortfolioCol;
	    @FXML
	    private TableColumn<Portfolio, ?> creationDatePortfolioCol;
	    @FXML
	    private TableColumn<Portfolio, ?> cashPortfolioCol;
	    @FXML
	    private TextField cashPortfolioTF;
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
				idPortfolioCol.setCellValueFactory(new PropertyValueFactory<>("id"));
				creationDatePortfolioCol.setCellValueFactory(new PropertyValueFactory<>("creation_date"));
				cashPortfolioCol.setCellValueFactory(new PropertyValueFactory<>("cash"));
				List<Portfolio> portfolios;
				portfolios=proxy.displayAllPortfolios();
				ObservableList<Portfolio> items = FXCollections.observableArrayList(portfolios);
		        tableview.setItems(items); 
		       
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
			tableview.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {showdetails(newValue);}));
	    }    

	    private void showdetails(Portfolio newValue) {
	     cashPortfolioTF.setText(Float.toString(newValue.getCash()));
	    //creationDatePortfolioTF.setValue(newValue.getCreation_date());
	    	
		}

		@FXML
	    private void addPortfolio(ActionEvent event) throws NamingException {
			String jndiName="thewolfs_server-ear/thewolfs_server-ejb/PortfolioService!tn.esprit.thewolfs_server.services.PortfolioServiceRemote";
			Context context=new InitialContext();
			PortfolioServiceRemote proxy=(PortfolioServiceRemote) context.lookup(jndiName);
			LocalDate today = LocalDate.now();
			
			String jndiNameTrader="thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
	    	Context contextTrader =new InitialContext();
	    	TraderServiceRemote proxyTrader=(TraderServiceRemote) context.lookup(jndiNameTrader);
    
			if(tableview.getSelectionModel().getSelectedItem() != null){
	    		Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle(" Portfolio Adding Error ");
	            alert.setHeaderText("Portfolio already exists ");
	            alert.showAndWait();
	    	}
			else{
				if(cashPortfolioTF.getText().equals("") || creationDatePortfolioTF.getValue()== null){
	    			Alert alert = new Alert(AlertType.WARNING);
	    			alert.setTitle(" Portfolio Adding Error ");
	    			alert.setHeaderText("you have at least an empty field");
	    			alert.showAndWait();
	    		}
				else{
					  if (today.isAfter(creationDatePortfolioTF.getValue())) {
			                Alert al = new Alert(Alert.AlertType.WARNING);
			                al.setTitle("  Portfolio Adding Error ");
			                al.setHeaderText("Inconvenable Creation Date ");
			                al.showAndWait();
					  }
					  else{
						    LocalDate localDate =creationDatePortfolioTF.getValue();
					        java.sql.Date creationDate = java.sql.Date.valueOf(localDate);
							Portfolio portfolio=new Portfolio(creationDate,Float.parseFloat(cashPortfolioTF.getText()));
							//Ajout statique de trader
							Integer idTrader=1;
						    Integer idPortfolio =proxy.addPortfolio(portfolio);
						    //proxy.assignPortfolioToTrader(idTrader, idPortfolio);
							
							Alert alert = new Alert(AlertType.INFORMATION);
				    		alert.setTitle("Portfolio Adding");
				    		alert.setHeaderText("Succesful :) ");
				    		alert.showAndWait();
						  
					  }
					
					
				}
			}
			
	        List<Portfolio> list = proxy.displayAllPortfolios();
	        ObservableList<Portfolio> items = FXCollections.observableArrayList(list);
	        tableview.setItems(items);
	        creationDatePortfolioTF.setValue(null);
	        cashPortfolioTF.setText("");
	    }

	    @FXML
	    private void removePortfolio(ActionEvent event) throws NamingException {
	    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/PortfolioService!tn.esprit.thewolfs_server.services.PortfolioServiceRemote";
			Context context=new InitialContext();
			PortfolioServiceRemote proxy=(PortfolioServiceRemote) context.lookup(jndiName);
			if(tableview.getSelectionModel().getSelectedItem() == null){
	    		Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle(" Portfolio Removing Error ");
	            alert.setHeaderText("Portfolio does not exist ");
	            alert.showAndWait();
	    	}
			else{
				int idPortfolio=tableview.getSelectionModel().getSelectedItem().getId();
		    	proxy.removePortfolio(idPortfolio);
		    	Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Portfolio Removing");
				alert.setHeaderText("Succesful :) ");
				alert.showAndWait();
			}
			
	    	List<Portfolio> list = proxy.displayAllPortfolios();
	        ObservableList<Portfolio> items = FXCollections.observableArrayList(list);
	        tableview.setItems(items);
	        creationDatePortfolioTF.setValue(null);
	        cashPortfolioTF.setText("");
	    }

	    @FXML
	    private void updatePortfolio(ActionEvent event) throws NamingException {
	    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/PortfolioService!tn.esprit.thewolfs_server.services.PortfolioServiceRemote";
			Context context=new InitialContext();
			PortfolioServiceRemote proxy=(PortfolioServiceRemote) context.lookup(jndiName);
			LocalDate today = LocalDate.now();
			if(tableview.getSelectionModel().getSelectedItem() == null){
	    		Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle(" Portfolio Updating Error ");
	            alert.setHeaderText("Portfolio does not exist ");
	            alert.showAndWait();
	    	}
		
			else{
				if(cashPortfolioTF.getText().equals("") || creationDatePortfolioTF.getValue()== null){
	    			Alert alert = new Alert(AlertType.WARNING);
	    			alert.setTitle(" Portfolio Updating Error ");
	    			alert.setHeaderText("you have at least an empty field");
	    			alert.showAndWait();
	    		}
				
				else{
					 if (today.isAfter(creationDatePortfolioTF.getValue())) {
			                Alert al = new Alert(Alert.AlertType.WARNING);
			                al.setTitle("  Portfolio Updating Error ");
			                al.setHeaderText("Inconvenable Creation Date ");
			                al.showAndWait();
					  }
					 else{
							LocalDate localDate =creationDatePortfolioTF.getValue();
					        java.sql.Date creationDate = java.sql.Date.valueOf(localDate);
							Portfolio portfolio=new Portfolio(creationDate,Float.parseFloat(cashPortfolioTF.getText()));
							portfolio.setId(tableview.getSelectionModel().getSelectedItem().getId());
					        proxy.updatePortfolio(portfolio);
					        Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Portfolio Updating");
							alert.setHeaderText("Succesful :) ");
							alert.showAndWait(); 
					 }
				
				}
			}
			
	        List<Portfolio> list = proxy.displayAllPortfolios();
	        ObservableList<Portfolio> items = FXCollections.observableArrayList(list);
	        tableview.setItems(items);
	        creationDatePortfolioTF.setValue(null);
	        cashPortfolioTF.setText("");
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

