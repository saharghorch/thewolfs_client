package esprit.javafx;

import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.thewolfs_server.entity.Level;
import tn.esprit.thewolfs_server.entity.Trader;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class Trader1Controller implements Initializable {

	@FXML
	private TextField firstnametf;
	@FXML
	private TextField lastnametf;
	@FXML
	private TextField emailtf;
	@FXML
	private ComboBox<Level> leveltf;
	@FXML
	private TextField passwordtf;
	@FXML
	private TextField cherchTF;
	@FXML
	private TableView<Trader> tableviewtrader;
	@FXML
	private TableColumn<Trader, ?> firstnamecol;
	@FXML
	private TableColumn<Trader, ?> lastnamecol;
	@FXML
	private TableColumn<Trader, ?> emailcol;
	@FXML
	private TableColumn<Trader, ?> passwordcol;
	@FXML
	private TableColumn<Trader, ?> levelcol;
	@FXML
	private Button addTraderBtn;
	@FXML
	private Button updateTraderBtn;
	@FXML
	private Button removeTraderBtn;
	@FXML
	private Button searchTraderBtn;
	@FXML
	private Button refreshbtn1;
	@FXML
	private TextField idTraderTF;

	private ObservableList<Level> unitex = FXCollections.observableArrayList(Level.firstLevel, Level.secondLevel,
			Level.thirdLevel);
	ObservableList<Trader> data = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 */

	public Trader1Controller() {
	}

	public void initialize(URL url, ResourceBundle rb) {
		leveltf.setItems(unitex);
		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";

		try {
			Context context = new InitialContext();
			TraderServiceRemote proxy = (TraderServiceRemote) context.lookup(jndiname);

			List<Trader> traders = proxy.dislayTrader();
			firstnamecol.setCellValueFactory(new PropertyValueFactory<>("first_name"));
			lastnamecol.setCellValueFactory(new PropertyValueFactory<>("last_name"));
			emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
			passwordcol.setCellValueFactory(new PropertyValueFactory<>("password"));
			levelcol.setCellValueFactory(new PropertyValueFactory<>("level"));
			ObservableList<Trader> items = FXCollections.observableArrayList(traders);
			tableviewtrader.setItems(items);

		} catch (NamingException e) {

			e.printStackTrace();
		}
		tableviewtrader.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
			showdetails(newValue);
		}));
	}

	private void showdetails(Trader trader) {

		// Level comblevel = trader.getLevel();
		firstnametf.setText(trader.getFirst_name());
		lastnametf.setText(trader.getLast_name());
		emailtf.setText(trader.getEmail());
		passwordtf.setText(trader.getPassword());
		leveltf.setValue(trader.getLevel());

	}

	@FXML
	private void afficherrevenu(MouseEvent event) throws NamingException {
		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context context = new InitialContext();
		TraderServiceRemote proxy = (TraderServiceRemote) context.lookup(jndiname);
	}

	@FXML
	private void addTrader(ActionEvent event) throws NamingException {

		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context context = new InitialContext();
		TraderServiceRemote proxy = (TraderServiceRemote) context.lookup(jndiname);

		Trader trader = new Trader(firstnametf.getText(), lastnametf.getText(), emailtf.getText(), passwordtf.getText(),
				leveltf.getValue());
		boolean valide1 = true;
		
		if (firstnametf.getText().equals("")) {

			valide1 = false;
		}
		if (lastnametf.getText().equals("")) {

			valide1 = false;
		}
		if (emailtf.getText().equals("")) {

			valide1 = false;
		}
		if (passwordtf.getText().equals("")) {

			valide1 = false;
		}
		if (leveltf.getValue()==null) {

			valide1 = false;
		}
		
		Trader tradertest =new Trader();
		tradertest=proxy.Traderexiste(trader);
		
			
		
		if (valide1 == false) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Trader adding");
			alert.setHeaderText("you have an empty field");
			alert.showAndWait();			
			}		
		else{
			
				
			proxy.addTrader(trader);
			List<Trader> list = proxy.dislayTrader();
			ObservableList<Trader> items = FXCollections.observableArrayList(list);
			tableviewtrader.setItems(items);
			firstnametf.setText("");
			lastnametf.setText("");
			emailtf.setText("");
			passwordtf.setText("");
			leveltf.setValue(null);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Trader adding");
			alert.setHeaderText("succesful");
			alert.showAndWait();
			}
		}
		

	

	@FXML
	private void updateTrader(ActionEvent event) throws NamingException {

		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context context = new InitialContext();
		TraderServiceRemote proxy = (TraderServiceRemote) context.lookup(jndiname);

		Trader trader = new Trader(firstnametf.getText(), lastnametf.getText(), emailtf.getText(), passwordtf.getText(),
				leveltf.getValue());
		trader.setId(tableviewtrader.getSelectionModel().getSelectedItem().getId());
		
		
		boolean valide1 = true;
		if (firstnametf.getText().equals("")) {

			valide1 = false;
		}
		if (lastnametf.getText().equals("")) {

			valide1 = false;
		}
		if (emailtf.getText().equals("")) {

			valide1 = false;
		}
		if (passwordtf.getText().equals("")) {

			valide1 = false;
		}
		if (leveltf.getValue()== null) {

			valide1 = false;
		}
		
		if (valide1==true){
		proxy.updateTrader(trader);
		List<Trader> list = proxy.dislayTrader();
		ObservableList<Trader> items = FXCollections.observableArrayList(list);
		tableviewtrader.setItems(items);
		firstnametf.setText("");
		lastnametf.setText("");
		emailtf.setText("");
		passwordtf.setText("");
		leveltf.setValue(null);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Trader updating");
		alert.setHeaderText("succesful");
		alert.showAndWait();
		}
		else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Trader updating");
			alert.setHeaderText("you have an empty field");
			alert.showAndWait();
			
		}
	}

	@FXML
	private void removeTrader(ActionEvent event) throws NamingException {

		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context context = new InitialContext();
		TraderServiceRemote proxy = (TraderServiceRemote) context.lookup(jndiname);

		int idTrader = tableviewtrader.getSelectionModel().getSelectedItem().getId();
		proxy.deleteTraderById(idTrader);
		List<Trader> list = proxy.dislayTrader();
		ObservableList<Trader> items = FXCollections.observableArrayList(list);
		tableviewtrader.setItems(items);
		firstnametf.setText("");
		lastnametf.setText("");
		emailtf.setText("");
		passwordtf.setText("");
		leveltf.setValue(null);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Trader removing");
		alert.setHeaderText("succesful");
		alert.showAndWait();
	}

	@FXML
	private void searchTrader(ActionEvent event) throws NamingException {

		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context context = new InitialContext();
		TraderServiceRemote proxy = (TraderServiceRemote) context.lookup(jndiname);
        if (cherchTF.getText().equals("")){
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Trader searching");
    		alert.setHeaderText("you have to enter a trader's name");
    		alert.showAndWait();
        
        }
        else{
		List<Trader> listTrader = proxy.findTraderByName(cherchTF.getText());
		ObservableList<Trader> items = FXCollections.observableArrayList(listTrader);
		tableviewtrader.setItems(items);
		cherchTF.setText("");
        }

	}

	@FXML
	private void refreshTrader(ActionEvent event) {

	}

}
