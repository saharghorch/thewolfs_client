package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.http.impl.io.SocketOutputBuffer;

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
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.StatusTraderServiceRemote;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class FXMLTraderForumController implements Initializable {

	@FXML
	private TextArea statusTA;

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

	@FXML
	private Button likeStatusBtn;

	@FXML
	private Button dislikeStatusBtn;

	@FXML
	private Button returnBtn;
	
	public static Integer idStatusTrader;
    private String message="Succesful :) ";

	String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/StatusTraderService!tn.esprit.thewolfs_server.services.StatusTraderServiceRemote";
	String jndiNameTrader = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
	Context context;
	Integer idTraderConnected = Session.getUser().getId();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		try {
			context = new InitialContext();
			StatusTraderServiceRemote proxy;
			proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
			List<StatusTrader> allStatus = proxy.displayAllStatus();
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
			try {
				showdetails(newValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));

	}

	private void showdetails(StatusTrader newValue) throws NamingException {
		context = new InitialContext();
		StatusTraderServiceRemote proxy;
		proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
		Trader trader = newValue.getTrader();
		
	
		if (idTraderConnected != trader.getId()){
		System.out.println("ce n est pas le meme trader");
			newValue.setViewsNumber(newValue.getViewsNumber() + 1);
			newValue.setId(newValue.getId());
			newValue.setTrader(trader);
			proxy.updateStatusTrader(newValue);
		}
		List<StatusTrader> allStatus = proxy.displayAllStatus();
		ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
		tableview.setItems(items);
		statusTA.setText("");

	}

	@FXML
	void addStatus(ActionEvent event) throws NamingException {
		context = new InitialContext();
		StatusTraderServiceRemote proxy;
		proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
		TraderServiceRemote proxyTrader = (TraderServiceRemote) context.lookup(jndiNameTrader);
		
		StatusTrader statusTrader = new StatusTrader(statusTA.getText());
		LocalDate today = LocalDate.now();
		java.sql.Date publicationDate = java.sql.Date.valueOf(today);
		statusTrader.setPublicationDate(publicationDate);
		statusTrader.setTrader(proxyTrader.findTraderById(idTraderConnected));

		proxy.addStatusTrader(statusTrader);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Status Posted");
		alert.setHeaderText(message);
		alert.showAndWait();
		List<StatusTrader> allStatus = proxy.displayAllStatus();
		ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
		tableview.setItems(items);
		statusTA.setText("");
	}

	@FXML
	void deleteStatus(ActionEvent event) throws NamingException {
		context = new InitialContext();
		StatusTraderServiceRemote proxy;
		proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
		Trader trader = tableview.getSelectionModel().getSelectedItem().getTrader();

		if ( (idTraderConnected == trader.getId())) {
			proxy.removeStatusTrader(tableview.getSelectionModel().getSelectedItem().getId());
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Status Removing");
			alert.setHeaderText(message);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(" Status Removing Error ");
			alert.setHeaderText("You can't remove this Status ");
			alert.showAndWait();
		}

		List<StatusTrader> allStatus = proxy.displayAllStatus();
		ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
		tableview.setItems(items);
		statusTA.setText("");
	}

	@FXML
	void seeAllComments(ActionEvent event) throws IOException {

		Integer id = tableview.getSelectionModel().getSelectedItem().getId();
		idStatusTrader=id;
		

		Parent root = FXMLLoader.load(getClass().getResource("FXMLTraderForumComment.fxml"));
		Scene newScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(newScene);
		window.show();

	}

	@FXML
	void updateStatus(ActionEvent event) throws NamingException {
		context = new InitialContext();
		StatusTraderServiceRemote proxy;
		proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
		TraderServiceRemote proxyTrader = (TraderServiceRemote) context.lookup(jndiNameTrader);
		Trader trader = tableview.getSelectionModel().getSelectedItem().getTrader();
		if  (idTraderConnected == trader.getId()) {
			StatusTrader statusTrader = new StatusTrader(statusTA.getText());
			statusTrader.setId(tableview.getSelectionModel().getSelectedItem().getId());
			statusTrader.setPublicationDate(tableview.getSelectionModel().getSelectedItem().getPublicationDate());
			statusTrader.setTrader(trader);
			proxy.updateStatusTrader(statusTrader);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Status Updating");
			alert.setHeaderText(message);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(" Status Updating Error ");
			alert.setHeaderText("You can't update this Status ");
			alert.showAndWait();
		}

		List<StatusTrader> allStatus = proxy.displayAllStatus();
		ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
		tableview.setItems(items);
		statusTA.setText("");

	}

	@FXML
	void dislikeStatus(ActionEvent event) throws NamingException {
		context = new InitialContext();
		StatusTraderServiceRemote proxy;
		proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
	    Trader trader = tableview.getSelectionModel().getSelectedItem().getTrader();
		StatusTrader statusTrader = tableview.getSelectionModel().getSelectedItem();
		if  (idTraderConnected != trader.getId()){
			statusTrader.setDislikesNumber(statusTrader.getDislikesNumber() + 1);
			statusTrader.setId(statusTrader.getId());
			statusTrader.setTrader(trader);
			proxy.updateStatusTrader(statusTrader);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Status Disliking");
			alert.setHeaderText("Thank you for your Attention");
			alert.showAndWait();
		}
		else{
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(" Status Disliking Error ");
			alert.setHeaderText("You can't dislike your Status ");
			alert.showAndWait();
			
		}
		List<StatusTrader> allStatus = proxy.displayAllStatus();
		ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
		tableview.setItems(items);
		statusTA.setText("");

	}

	@FXML
	void likeStatus(ActionEvent event) throws NamingException {
		context = new InitialContext();
		StatusTraderServiceRemote proxy;
		proxy = (StatusTraderServiceRemote) context.lookup(jndiName);
	    Trader trader = tableview.getSelectionModel().getSelectedItem().getTrader();
		StatusTrader statusTrader = tableview.getSelectionModel().getSelectedItem();
		if  (idTraderConnected != trader.getId()){
			statusTrader.setLikesNumber(statusTrader.getLikesNumber() + 1);
			statusTrader.setId(statusTrader.getId());
			statusTrader.setTrader(trader);
			proxy.updateStatusTrader(statusTrader);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Status Liking");
			alert.setHeaderText("Thank you for your Attention");
			alert.showAndWait();
		}
		else{
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(" Status Liking Error ");
			alert.setHeaderText("You can't Like your Status ");
			alert.showAndWait();
			
		}
		List<StatusTrader> allStatus = proxy.displayAllStatus();
		ObservableList<StatusTrader> items = FXCollections.observableArrayList(allStatus);
		tableview.setItems(items);
		statusTA.setText("");

	}

	@FXML
	void onReturn(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("SpaceTraderInterface.fxml"));
		Scene newScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(newScene);
		window.show();
	}

}
