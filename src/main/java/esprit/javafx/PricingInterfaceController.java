package esprit.javafx;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
import tn.esprit.thewolfs_server.services.PricingRemote;

public class PricingInterfaceController implements Initializable {

	@FXML
	private TextField XTF;
	@FXML
	private TextField sigmaTF;
	@FXML
	private TextField rTF;
	@FXML
	private TextField tTF;
	@FXML
	private TableView<?> tableview;
	@FXML
	private TableColumn<?, ?> SCol;
	@FXML
	private TableColumn<?, ?> XCol;
	@FXML
	private TableColumn<?, ?> sigmaCol;
	@FXML
	private TableColumn<?, ?> rCol;
	@FXML
	private TableColumn<?, ?> tCol;
	@FXML
	private TableColumn<?, ?> priceCallOptionCol;
	@FXML
	private TableColumn<?, ?> pricePutOptionCol;
	@FXML
	private Button PutOptionPriceBtn;
	@FXML
	private Button callOptionPriceBtn;
	@FXML
	private TextField idcarte;
	@FXML
	private TextField STF;
	@FXML
	private TextField CallOptionTF;
	@FXML
	private TextField PutOptionTF;

	@FXML
	private DatePicker expirationDateDP;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	@FXML
	private void PutOptionPrice(ActionEvent event) throws NamingException {
		String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/Pricing!tn.esprit.thewolfs_server.services.PricingRemote";
		Context context = new InitialContext();
		PricingRemote proxy = (PricingRemote) context.lookup(jndiName);
		if (STF.getText().equals("") || XTF.getText().equals("") || sigmaTF.getText().equals("")
				|| rTF.getText().equals("") || tTF.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" Pricing Exception ");
			alert.setHeaderText("you have an empty field");
			alert.showAndWait();
		} else {
			Double s = Double.parseDouble(STF.getText());
			Double x = Double.parseDouble(XTF.getText());
			Double sigma = Double.parseDouble(sigmaTF.getText());
			Double r = Double.parseDouble(rTF.getText());
			Double t = Double.parseDouble(tTF.getText());
			Double PutOption = proxy.PutOptionPrice(s, x, sigma, r, t);
			PutOptionTF.setText(PutOption.toString());
		}

	}

	@FXML
	private void callOptionPrice(ActionEvent event) throws NamingException {
		String jndiName = "thewolfs_server-ear/thewolfs_server-ejb/Pricing!tn.esprit.thewolfs_server.services.PricingRemote";
		Context context = new InitialContext();
		PricingRemote proxy = (PricingRemote) context.lookup(jndiName);
		if (STF.getText().equals("") || XTF.getText().equals("") || sigmaTF.getText().equals("")
				|| rTF.getText().equals("") || tTF.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" Pricing Exception ");
			alert.setHeaderText("you have at least an empty field");
			alert.showAndWait();
		} else {
			//Important
			Calendar cal = Calendar.getInstance ();
	        Date today = cal.getTime ();
			
			LocalDate n = expirationDateDP.getValue();
			Date date = Date.from(n.atStartOfDay(ZoneId.systemDefault()).toInstant());
			long z = proxy.getDateDiff(today, date, TimeUnit.DAYS);
			System.out.println(z);
			double res = (double) z / 365;
			Double s = Double.parseDouble(STF.getText());
			Double x = Double.parseDouble(XTF.getText());
			Double sigma = Double.parseDouble(sigmaTF.getText())/100;
			Double r = Double.parseDouble(rTF.getText())/100;
			Double t =res;
			Double CallOption = proxy.CallOptionPrice(s, x, sigma, r, t);
			CallOptionTF.setText(CallOption.toString());
			STF.setText("");
			XTF.setText("");
			sigmaTF.setText("");
			rTF.setText("");

		}

	}

}
