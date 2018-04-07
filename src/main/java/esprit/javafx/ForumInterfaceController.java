package esprit.javafx;

import java.io.IOException;
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
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.StatusTrader;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.StatusTraderServiceRemote;

public class ForumInterfaceController implements Initializable{

    @FXML
    private TextArea StatusTA;

    @FXML
    private TableView<StatusTrader> tableview;

    @FXML
    private TableColumn<StatusTrader, ?> descriptionStatusCol;

    @FXML
    private TableColumn<StatusTrader, ?> dateStatusCol;

    @FXML
    private TableColumn<StatusTrader, ?> likesStatusCol;

    @FXML
    private TableColumn<StatusTrader, ?> dislikesStatusCol;

    @FXML
    private TableColumn<StatusTrader, ?> viewsStatusCol;

    @FXML
    private Button addStatusBtn;
    
    @FXML
    private Button deleteStatusBtn;

    @FXML
    private Button updateStatusBtn;

    @FXML
    private Button allCommentsBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/StatusTraderService!tn.esprit.thewolfs_server.services.StatusTraderServiceRemote";
        	Context context;
			try {
				context = new InitialContext();
				StatusTraderServiceRemote proxy;
				proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
				List<StatusTrader> allStatus= proxy.displayAllStatus();
				descriptionStatusCol.setCellValueFactory(new PropertyValueFactory<>("description"));
				dateStatusCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
				likesStatusCol.setCellValueFactory(new PropertyValueFactory<>("likesNumber"));
				dislikesStatusCol.setCellValueFactory(new PropertyValueFactory<>("dislikesNumber"));
				viewsStatusCol.setCellValueFactory(new PropertyValueFactory<>("viewsNumber"));
	            ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
		        tableview.setItems(items);
			} catch (NamingException e) {
				
				e.printStackTrace();
			}
                tableview.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                showdetails(newValue);}));
               
    }


    
    private void showdetails(StatusTrader newValue){
    	/*Parent root = FXMLLoader.load(getClass().getResource("CommentInterface.fxml"));
    	Scene newScene= new Scene(root);
    	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    	window.setScene(newScene);
    	window.show();*/
		
	}



	@FXML
    void addStatus(ActionEvent event) throws NamingException{
		String jndiName="thewolfs_server-ear/thewolfs_server-ejb/StatusTraderService!tn.esprit.thewolfs_server.services.StatusTraderServiceRemote";
    	Context context;
    	context = new InitialContext();
		StatusTraderServiceRemote proxy;
		proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
		StatusTrader statusTrader=new StatusTrader(StatusTA.getText());
		LocalDate today = LocalDate.now();
		java.sql.Date publicationDate = java.sql.Date.valueOf(today);
		statusTrader.setPublicationDate(publicationDate);
        proxy.addStatusTrader(statusTrader);
        Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Status Posted");
		alert.setHeaderText("Succesful :) ");
		alert.showAndWait();
		List<StatusTrader> allStatus= proxy.displayAllStatus();
		ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
        tableview.setItems(items);
        StatusTA.setText("");
  }
	
	 @FXML
	    void deleteStatus(ActionEvent event) throws NamingException {
		 String jndiName="thewolfs_server-ear/thewolfs_server-ejb/StatusTraderService!tn.esprit.thewolfs_server.services.StatusTraderServiceRemote";
	     Context context;
	     context = new InitialContext();
		 StatusTraderServiceRemote proxy;
		 proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
		 int idStatusTrader=tableview.getSelectionModel().getSelectedItem().getId();
     	 proxy.removeStatusTrader(idStatusTrader);
     	 Alert alert = new Alert(AlertType.INFORMATION);
		 alert.setTitle("Status Removing");
		 alert.setHeaderText("Succesful :) ");
		 alert.showAndWait();
		 List<StatusTrader> allStatus= proxy.displayAllStatus();
		 ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
	     tableview.setItems(items);
	     StatusTA.setText("");
	    }

	    @FXML
	    void seeAllComments(ActionEvent event)throws NamingException , IOException{
	    	Parent root = FXMLLoader.load(getClass().getResource("CommentsInterface.fxml"));
			Scene newScene= new Scene(root);
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(newScene);
			window.show();
		

	    }

	    @FXML
	    void updateStatus(ActionEvent event)throws NamingException {
	    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/StatusTraderService!tn.esprit.thewolfs_server.services.StatusTraderServiceRemote";
	    	Context context;
	    	context = new InitialContext();
			StatusTraderServiceRemote proxy;
			proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
			StatusTrader statusTrader=new StatusTrader(StatusTA.getText());
			statusTrader.setId(tableview.getSelectionModel().getSelectedItem().getId());
			proxy.updateStatusTrader(statusTrader);
			Alert alert = new Alert(AlertType.INFORMATION);
 			alert.setTitle("Status Updating");
 			alert.setHeaderText("Succesful :) ");
 			alert.showAndWait();
			List<StatusTrader> allStatus= proxy.displayAllStatus();
			ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
	        tableview.setItems(items);
	        StatusTA.setText("");

	    }
    

}
