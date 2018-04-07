package esprit.javafx;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
	private TextField soldetf;
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
	private TableColumn<Trader, ?> soldecol;
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
	@FXML
    private Button generatestatbtn;

    @FXML
    private Button refreshbtn;
    
    
    private static Level level_Trader;


	ObservableList<Trader> data = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 */

	public Trader1Controller() {
	}

	public void initialize(URL url, ResourceBundle rb) {
		
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
			soldecol.setCellValueFactory(new PropertyValueFactory<>("solde"));
			
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
		soldetf.setText(trader.getSolde().toString());

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

		Trader trader = new Trader(firstnametf.getText(), lastnametf.getText(),
				                 emailtf.getText(),  passwordtf.getText(),level_Trader,
				                 Float.parseFloat(soldetf.getText()));
		
		if (trader.getSolde()<10000){
			level_Trader=Level.firstLevel;
		}else if(trader.getSolde()>100000 && trader.getSolde()<1000000){
			level_Trader=Level.secondLevel;
		}else {level_Trader=Level.thirdLevel;}
		trader.setLevel(level_Trader);
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
		/*if (leveltf.getValue()==null) {

			valide1 = false;
		}*/
		if (soldetf.getText().equals("")) {

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
				if(tradertest!=null){	
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Trader adding");
					alert.setHeaderText("already exists");
					alert.showAndWait();
					System.out.println(tradertest);	
				
				}else{
					proxy.addTrader(trader);
					List<Trader> list = proxy.dislayTrader();
					ObservableList<Trader> items = FXCollections.observableArrayList(list);
					tableviewtrader.setItems(items);
					firstnametf.setText("");
					lastnametf.setText("");
					emailtf.setText("");
					passwordtf.setText("");
					leveltf.setValue(null);
					soldetf.setText("");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Trader adding");
					alert.setHeaderText("succesful");
					alert.showAndWait();
			}
			}
		
		}
		

	

	@FXML
	private void updateTrader(ActionEvent event) throws NamingException {

		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context context = new InitialContext();
		TraderServiceRemote proxy = (TraderServiceRemote) context.lookup(jndiname);
         
		Trader trader = new Trader(firstnametf.getText(), lastnametf.getText(),
                emailtf.getText(),  passwordtf.getText(),level_Trader,
                Float.parseFloat(soldetf.getText()));
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
		if(soldetf.getText().equals("")){
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
		soldetf.setText("");
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
		soldetf.setText("");
		
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
	private void refreshTableview(ActionEvent event) throws NamingException {
		
		
	
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
			soldecol.setCellValueFactory(new PropertyValueFactory<>("solde"));
			ObservableList<Trader> items = FXCollections.observableArrayList(traders);
			tableviewtrader.setItems(items);
			firstnametf.setText("");
			lastnametf.setText("");
			emailtf.setText("");
			passwordtf.setText("");
			leveltf.setValue(null);
			soldetf.setText("");

		} catch (NamingException e) {

			e.printStackTrace();
		}
		tableviewtrader.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
			showdetails(newValue);
		}));

	}
	  @FXML
	    void generatestat(ActionEvent event) throws IOException {
		  	
		  Stage stage = new Stage();
          //((Node) event.getSource()).getScene().getWindow().hide();
           Parent root = FXMLLoader.load(getClass().getResource("../javafx/TraderStat.fxml"));
           Scene scene = new Scene(root);
           stage.setScene(scene);
           stage.show();
	    }
	 
	/*  @FXML
	    private void generatepdf(ActionEvent event) throws Exception {
		  String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
			
			TraderServiceRemote proxy;
			try {
				Context context = new InitialContext();
				proxy = (TraderServiceRemote) context.lookup(jndiname);
				proxy.createPDF();
			} catch (NamingException e) {
				
				e.printStackTrace();
			}
		
		  
	        Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Information Dialog");
	            alert.setHeaderText(null);
	            alert.setContentText("Le PDF a été génerer avec succés!");

	            alert.showAndWait();
	    }*/

}
