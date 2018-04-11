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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.thewolfs_server.entity.Comment;
import tn.esprit.thewolfs_server.entity.StatusTrader;
import tn.esprit.thewolfs_server.services.CommentServiceRemote;
import tn.esprit.thewolfs_server.services.StatusTraderServiceRemote;

public class CommentsInterfaceController implements Initializable{

    @FXML
    private TextArea StatusTA;

    @FXML
    private TableView<Comment> tableview;

    @FXML
    private TableColumn<Comment, ?> descriptionCommentCol;

    @FXML
    private TableColumn<Comment, ?> dateCommentCol;

    @FXML
    private Button addCommentBtn;

    @FXML
    private TextField commentTF;
    
    @FXML
    private Button deleteCommentBtn;

    @FXML
    private Button updateCommentBtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/CommentService!tn.esprit.thewolfs_server.services.CommentServiceRemote";
        	Context context;
			try {
				context = new InitialContext();
				CommentServiceRemote proxy;
				proxy = (CommentServiceRemote) context.lookup(jndiName);
				List<Comment> allcomment= proxy.displayAllComment();
				descriptionCommentCol.setCellValueFactory(new PropertyValueFactory<>("description"));
				dateCommentCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
			 ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
		        tableview.setItems(items);
			} catch (NamingException e) {
				
				e.printStackTrace();
			}
                tableview.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
                showdetails(newValue);}));
               
    }

    private void showdetails(Comment newValue) {
		// TODO Auto-generated method stub
		
	}

	@FXML
    void addComment(ActionEvent event) throws NamingException {
		String jndiName="thewolfs_server-ear/thewolfs_server-ejb/CommentService!tn.esprit.thewolfs_server.services.CommentServiceRemote";
    	Context context;
    	context = new InitialContext();
		CommentServiceRemote proxy;
		proxy = (CommentServiceRemote) context.lookup(jndiName);
		Comment comment=new Comment(commentTF.getText());
		LocalDate today = LocalDate.now();
		java.sql.Date publicationDate = java.sql.Date.valueOf(today);
		comment.setPublicationDate(publicationDate);
        proxy.addComment(comment);
        Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Comment Posted");
		alert.setHeaderText("Succesful :) ");
		alert.showAndWait();
		List<Comment> allcomment= proxy.displayAllComment();
		ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
        tableview.setItems(items);
        commentTF.setText("");
    	}
	
    @FXML
    void deleteComment(ActionEvent event) throws NamingException {
    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/CommentService!tn.esprit.thewolfs_server.services.CommentServiceRemote";
    	Context context;
    	context = new InitialContext();
		CommentServiceRemote proxy;
		proxy = (CommentServiceRemote) context.lookup(jndiName);
		int idComment=tableview.getSelectionModel().getSelectedItem().getId();
    	proxy.removeComment(idComment);
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Comment Removing");
		alert.setHeaderText("Succesful :) ");
		alert.showAndWait();
		List<Comment> allcomment= proxy.displayAllComment();
		ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
        tableview.setItems(items);
        commentTF.setText("");
    }

    @FXML
    void updateComment(ActionEvent event) throws NamingException{
    	String jndiName="thewolfs_server-ear/thewolfs_server-ejb/CommentService!tn.esprit.thewolfs_server.services.CommentServiceRemote";
    	Context context;
    	context = new InitialContext();
		CommentServiceRemote proxy;
		proxy = (CommentServiceRemote) context.lookup(jndiName);
		Comment comment=new Comment(commentTF.getText());
		comment.setId(tableview.getSelectionModel().getSelectedItem().getId());
		proxy.updateComment(comment);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Comment Updating");
		alert.setHeaderText("Succesful :) ");
		alert.showAndWait();
		List<Comment> allcomment= proxy.displayAllComment();
		ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
        tableview.setItems(items);
        commentTF.setText("");
    }


}
