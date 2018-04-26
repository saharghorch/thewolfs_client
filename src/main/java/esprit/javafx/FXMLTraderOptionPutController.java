package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//Conversion
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javafx.animation.FadeTransition;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Activity;
import tn.esprit.thewolfs_server.entity.Currency;
import tn.esprit.thewolfs_server.services.AccountServiceRemote;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class FXMLTraderOptionPutController implements Initializable {

	@FXML
	private TextField putOptionPriceTF;

	@FXML
	private TableView<Account> tableview;

	@FXML
	private TableColumn<Account, ?> amountAccountCol;

	@FXML
	private TableColumn<Account, ?> currencyAccountCol;

	@FXML
	private TableColumn<Account, ?> isActiveAccountCol;

	@FXML
	private Button byPutOptionBtn;

	@FXML
	private Button validateAccountBtn;

	@FXML
	private Button ignorePutOptionBtn;

	@FXML
	private Button returnBtn;
	

	SendMail mail = new SendMail();

	// Conversion:essential URL structure is built using constants
	public static final String ACCESS_KEY = "160ed6a739be8daf50db668b975a78df";
	public static final String BASE_URL = "http://apilayer.net/api/";
	public static final String ENDPOINT = "live";

	// this object is used for executing requests to the (REST) API
	static CloseableHttpClient httpClient = HttpClients.createDefault();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		putOptionPriceTF.setText(FXMLTraderOptionController.putOptionPriceStatic.toString());
	   
	}

	@FXML   
	void byPutOption(ActionEvent event) throws NamingException {
		String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
		Context context;
		context = new InitialContext();
		AccountServiceRemote proxy;
		proxy = (AccountServiceRemote) context.lookup(jndiName);

		Integer idTraderConnected = Session.getUser().getId();
		List<Account> accounts = proxy.findAllAccountByTrader(idTraderConnected);
		List<Account> traderAccounts = new ArrayList<>();
		for (Account account : accounts) {
			if (account.getIsActive().equals(Activity.Yes)) {
				traderAccounts.add(account);
			}
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Call Option Pricing");
		alert.setHeaderText("Please select your Account");
		alert.showAndWait();
		amountAccountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
		currencyAccountCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
		isActiveAccountCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));

		ObservableList<Account> items = FXCollections.observableArrayList(traderAccounts);
		tableview.setItems(items);
	}

	@FXML
	void ignorePutOption(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Put Option Pricing");
		alert.setHeaderText("No option traded");
		alert.showAndWait();
		Parent root = FXMLLoader.load(getClass().getResource("SpaceTraderInterface.fxml"));
		Scene newScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(newScene);
		window.show();
	 	

	}
	  

	@FXML
	void validateAccount(ActionEvent event) throws NamingException, ClientProtocolException, IOException {
		Account newValue = tableview.getSelectionModel().getSelectedItem();
		String jndiNameTrader = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		Context contextTrader = new InitialContext();
		TraderServiceRemote proxyTrader = (TraderServiceRemote) contextTrader.lookup(jndiNameTrader);
		String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
		Context context;
		context = new InitialContext();
		AccountServiceRemote proxy;
		proxy = (AccountServiceRemote) context.lookup(jndiName);
		if (newValue.getCurrency().equals(Currency.EUR)) {
			if (newValue.getAmount() < FXMLTraderOptionController.callOptionPriceStatic) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle(" Call Option Failed ");
				alert.setHeaderText("you don't have enough money to buy this option");
				alert.showAndWait();
			} else {
				Float lastAmount = newValue.getAmount();
				Float newAmount = (float) (lastAmount - FXMLTraderOptionController.putOptionPriceStatic);
				Account account = new Account(newAmount, newValue.getCurrency(), newValue.getIsActive());
				Integer idTraderConnected = Session.getUser().getId();
				account.setId(newValue.getId());
				account.setTrader(proxyTrader.findTraderById(idTraderConnected));
				proxy.updateAccount(account);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Buy Put Option");
				alert.setHeaderText("Succesful :) A Confirmation Email will be sent ");
				alert.showAndWait();
				List<Account> accounts = proxy.findAllAccountByTrader(idTraderConnected);
				List<Account> traderAccounts = new ArrayList<>();
				for (Account accountFinal : accounts) {
					if (accountFinal.getIsActive().equals(Activity.Yes)) {
						traderAccounts.add(accountFinal);
					}
				}
				amountAccountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
				currencyAccountCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
				isActiveAccountCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
				ObservableList<Account> items = FXCollections.observableArrayList(traderAccounts);
				tableview.setItems(items);
				String message=newAmount.toString()+" EUR";
				mail.send(proxyTrader.findTraderById(idTraderConnected).getEmail(),message);
			}
		} else if (newValue.getCurrency().equals(Currency.USD)) {
			// Conversion EUR/USD et refaire le m�me traitement pr�c�dent
			HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY);
			CloseableHttpResponse response;

			response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
			Double usdeur = exchangeRates.getJSONObject("quotes").getDouble("USDEUR");
			response.close();
			Double conversion = 1 / usdeur;

			Float lastAmount = newValue.getAmount();
			Float newAmount = (float) (lastAmount - (FXMLTraderOptionController.putOptionPriceStatic * conversion));
			Account account = new Account(newAmount, newValue.getCurrency(), newValue.getIsActive());
			Integer idTraderConnected = Session.getUser().getId();
			account.setId(newValue.getId());
			account.setTrader(proxyTrader.findTraderById(idTraderConnected));
			proxy.updateAccount(account);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Buy Call Option");
			alert.setHeaderText("Succesful :) A Confirmation Email will be sent ");
			alert.showAndWait();
			List<Account> accounts = proxy.findAllAccountByTrader(idTraderConnected);
			List<Account> traderAccounts = new ArrayList<>();
			for (Account accountFinal : accounts) {
				if (accountFinal.getIsActive().equals(Activity.Yes)) {
					traderAccounts.add(accountFinal);
				}
			}
			amountAccountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
			currencyAccountCol.setCellValueFactory(new PropertyValueFactory<>("currency"));
			isActiveAccountCol.setCellValueFactory(new PropertyValueFactory<>("isActive"));
			ObservableList<Account> items = FXCollections.observableArrayList(traderAccounts);
			tableview.setItems(items);
			String message=newAmount.toString()+" USD";
			mail.send(proxyTrader.findTraderById(idTraderConnected).getEmail(),message);
		}

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
