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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Comment;
import tn.esprit.thewolfs_server.entity.StatusTrader;
import tn.esprit.thewolfs_server.services.CommentServiceRemote;
import tn.esprit.thewolfs_server.services.StatusTraderServiceRemote;

public class FXMLAdminForumCommentController implements Initializable {

	@FXML
	private TextArea StatusTA;

	@FXML
	private TableView<Comment> tableview;

	@FXML
	private TableColumn<Comment, ?> descriptionCommentCol;

	@FXML
	private TableColumn<Comment, ?> dateCommentCol;

	@FXML
    private Button returnBtn;
	
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/CommentService!tn.esprit.thewolfs_server.services.CommentServiceRemote";
		String jndiNameStatus = "thewolfs_server-ear/thewolfs_server-ejb/StatusTraderService!tn.esprit.thewolfs_server.services.StatusTraderServiceRemote";

		Context context;
		try {
			context = new InitialContext();
			StatusTraderServiceRemote proxyStatusTrader;
			proxyStatusTrader = (StatusTraderServiceRemote) context.lookup(jndiNameStatus);
			StatusTrader statusTrader = proxyStatusTrader.findStatusTraderById(FXMLAdminForumController.idStatus);
			StatusTA.setText(statusTrader.getDescription());
			CommentServiceRemote proxy;
			proxy = (CommentServiceRemote) context.lookup(jndiName);
			List<Comment> allcomment = proxy.findAllStatusComment(FXMLTraderForumController.idStatusTrader);
			descriptionCommentCol.setCellValueFactory(new PropertyValueFactory<>("description"));
			dateCommentCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
			ObservableList<Comment> items = FXCollections.observableArrayList(allcomment);
			tableview.setItems(items);
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	


    @FXML
    void onReturn(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("FXMLAdminForum.fxml"));
		Scene newScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(newScene);
		window.show();

    }

}
