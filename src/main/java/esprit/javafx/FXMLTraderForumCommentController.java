
package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXTextArea;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Comment;
import tn.esprit.thewolfs_server.entity.StatusTrader;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.services.CommentServiceRemote;
import tn.esprit.thewolfs_server.services.StatusTraderServiceRemote;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class FXMLTraderForumCommentController implements Initializable {


    @FXML
    private TableView<Comment> tableview;

    @FXML
    private TableColumn<Comment, ?> descriptionCommentCol;

    @FXML
    private TableColumn<Comment, ?> dateCommentCol;

    @FXML
    private Button addCommentBtn;

    @FXML
    private Button updateCommentBtn;

    @FXML
    private Button deleteCommentBtn;

    @FXML
    private JFXTextArea statusTA;

    @FXML
    private JFXTextArea commentTA;

    @FXML
    private Button returnBtn;
    
    String jndiName="thewolfs_server-ear/thewolfs_server-ejb/CommentService!tn.esprit.thewolfs_server.services.CommentServiceRemote";
    String jndiNameTrader="thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
    String jndiNameStatus = "thewolfs_server-ear/thewolfs_server-ejb/StatusTraderService!tn.esprit.thewolfs_server.services.StatusTraderServiceRemote";
	
    Context context;
	String message="Succesful :)";
	Integer idTraderConnected = Session.getUser().getId();
   // Integer idStatus=FXMLTraderForumController.idStatusTrader;
    
    @Override
	public void initialize(URL url, ResourceBundle rb) {
    	try {
			context = new InitialContext();
			StatusTraderServiceRemote proxyStatusTrader;
			proxyStatusTrader = (StatusTraderServiceRemote) context.lookup(jndiNameStatus);
			StatusTrader statusTrader=proxyStatusTrader.findStatusTraderById(FXMLTraderForumController.idStatusTrader);
			statusTA.setText(statusTrader.getDescription());
			CommentServiceRemote proxy;
			proxy = (CommentServiceRemote) context.lookup(jndiName);
			List<Comment> allcomment= proxy.findAllStatusComment(FXMLTraderForumController.idStatusTrader);
			descriptionCommentCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		    dateCommentCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
			ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
	        tableview.setItems(items);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		
	}

    @FXML
    void addComment(ActionEvent event) throws NamingException {
    	context = new InitialContext();
    	CommentServiceRemote proxy;
		proxy = (CommentServiceRemote) context.lookup(jndiName);
		StatusTraderServiceRemote proxyStatusTrader;
		proxyStatusTrader = (StatusTraderServiceRemote) context.lookup(jndiNameStatus);
		TraderServiceRemote proxyTrader = (TraderServiceRemote) context.lookup(jndiNameTrader);
		StatusTrader statusTrader=proxyStatusTrader.findStatusTraderById(FXMLTraderForumController.idStatusTrader);
		Trader trader=proxyTrader.findTraderById(idTraderConnected);
		Comment comment=new Comment(commentTA.getText());
		LocalDate today = LocalDate.now();
		java.sql.Date publicationDate = java.sql.Date.valueOf(today);
		comment.setPublicationDate(publicationDate);
		comment.setStatusTrader(statusTrader);
		comment.setTrader(trader);
        proxy.addComment(comment);
        Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Comment Posted");
		alert.setHeaderText(message);
		alert.showAndWait();
		List<Comment> allcomment=proxy.findAllStatusComment(FXMLTraderForumController.idStatusTrader);
		ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
        tableview.setItems(items);
        commentTA.setText("");
    	

    }

    @FXML
    void deleteComment(ActionEvent event) throws NamingException {
    	context = new InitialContext();
		CommentServiceRemote proxy;
		proxy = (CommentServiceRemote) context.lookup(jndiName);
	    Trader trader=tableview.getSelectionModel().getSelectedItem().getTrader();
	  
	    
	    if(idTraderConnected == trader.getId()){
	    	int idComment=tableview.getSelectionModel().getSelectedItem().getId();
	     	 proxy.removeComment(idComment);
	     	 Alert alert = new Alert(AlertType.INFORMATION);
			 alert.setTitle("Comment Removing");
			 alert.setHeaderText(message);
			 alert.showAndWait();
	    }else{
	    	Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Status Removing Error ");
            alert.setHeaderText("You can't remove this Comment ");
            alert.showAndWait();
	    }
		
		List<Comment> allcomment=proxy.findAllStatusComment(FXMLTraderForumController.idStatusTrader);
		ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
        tableview.setItems(items);
        commentTA.setText("");
    }

    @FXML
    void onReturn(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("FXMLTraderForum.fxml"));
		Scene newScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(newScene);
		window.show();
    }

    @FXML
    void updateComment(ActionEvent event) throws NamingException {
    	 commentTA.setText(tableview.getSelectionModel().getSelectedItem().getDescription());
    	context = new InitialContext();
    	CommentServiceRemote proxy;
		proxy = (CommentServiceRemote) context.lookup(jndiName);
    	StatusTraderServiceRemote proxyStatusTrader;
		proxyStatusTrader = (StatusTraderServiceRemote) context.lookup(jndiNameStatus);
		StatusTrader statusTrader=proxyStatusTrader.findStatusTraderById(FXMLTraderForumController.idStatusTrader);
	    Trader trader=tableview.getSelectionModel().getSelectedItem().getTrader();
		   if(idTraderConnected == trader.getId()){
		        Comment comment=new Comment(commentTA.getText());
				comment.setId(tableview.getSelectionModel().getSelectedItem().getId());
				comment.setTrader(trader);
				comment.setPublicationDate(tableview.getSelectionModel().getSelectedItem().getPublicationDate());
				comment.setStatusTrader(statusTrader);
				proxy.updateComment(comment);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Comment Updating");
				alert.setHeaderText(message);
				alert.showAndWait();
		    }else{
		    	Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle(" Comment Updating Error ");
	            alert.setHeaderText("You can't update this Comment ");
	            alert.showAndWait();
		    }
		
		List<Comment> allcomment=proxy.findAllStatusComment(FXMLTraderForumController.idStatusTrader);
		ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
        tableview.setItems(items);
        commentTA.setText("");

    }

	

}

