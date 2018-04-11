package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.Interest;
import tn.esprit.thewolfs_server.entity.TypeRate;
import tn.esprit.thewolfs_server.services.InterestServiceRemote;

public class TraderEarnController implements Initializable {

	@FXML
	private Button returnbtn;

	@FXML
	private AnchorPane holderPane;

	@FXML
	public JFXTextField amountATF;

	@FXML
	private ComboBox<String> yearscombo;

	@FXML
	private Button confirmAbtn;

	@FXML
	private JFXTextField Nbyearstf;

	@FXML
	private Button simpleInterestbtn;

	@FXML
	private Button compoundInterestbtn;
	@FXML
	private ComboBox<String> interetCombo;
	
	
     // valeur de comboBox
	private ObservableList<String> typeInretest = (ObservableList<String>) FXCollections.observableArrayList("annually",
			"semiAnnually", "monthly", "daily", "continuously","quarterly");
	private ObservableList<String> interet = (ObservableList<String>) FXCollections
			.observableArrayList("Simple Interest", "Compound Interest");
	
	
    // resultats 
	public static Double Compound;
	public static Double Simple;
	public static Double principale;
	public static int nbyears;
	public static TypeRate type;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//remplissage de comboBox
		yearscombo.setItems(typeInretest);
		interetCombo.setItems(interet);

	}

	@FXML
	void confirmEarn(ActionEvent event) throws IOException {

		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/InterestService!tn.esprit.thewolfs_server.services.InterestServiceRemote";
		try {
			Context context = new InitialContext();
			InterestServiceRemote proxy = (InterestServiceRemote) context.lookup(jndiname);

			
			principale = Double.valueOf(amountATF.getText());
			
			// Compound Interet
			if (interetCombo.getValue() == "Compound Interest") {
				
				
				             Interest interest = new Interest(Double.valueOf(amountATF.getText()), 0.05d,
						                          Integer.valueOf(Nbyearstf.getText()),TypeRate.valueOf(yearscombo.getValue()));
				                                  
				             nbyears = Integer.valueOf(Nbyearstf.getText());
				             type =TypeRate.valueOf(yearscombo.getValue());
				           //ajout dans la base
				           proxy.addInterest(interest);
				           
				           //resultat du calcul des interets
				  
				            Compound = proxy.CalculCompoundInterestAmount(Double.valueOf(amountATF.getText()), 0.15d,
						         Integer.valueOf(Nbyearstf.getText()), TypeRate.valueOf(String.valueOf(yearscombo.getValue())));
                            
				            // ouvrir la fenetre du résultat
				            Parent root = FXMLLoader.load(getClass().getResource("ResultCompoundInterest.fxml"));
				            Scene newScene = new Scene(root);
				            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				            window.setScene(newScene);
				            window.show();

			} 
			    //simple interet
			    else {
				             Interest interest = new Interest(Double.valueOf(amountATF.getText()), 0.05d,
						     Integer.valueOf(Nbyearstf.getText()));
				             nbyears = Integer.valueOf(Nbyearstf.getText());
				             proxy.addInterest(interest);
				             Simple = proxy.CalculSimpleInterestAmount(Double.valueOf(amountATF.getText()), 0.05d,
						     Integer.valueOf(Nbyearstf.getText()));
				             
                             //ouvrir la page du resultat du simple interet 
				             Parent root = FXMLLoader.load(getClass().getResource("ResultSimpleInterest.fxml"));
				             Scene newScene = new Scene(root);
				             Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				             window.setScene(newScene);
				             window.show();

			}
		} catch (NamingException e) {

			e.printStackTrace();
		}

	}

	@FXML
	void onReturn(ActionEvent event) throws IOException {
		
		//ouvrir la page de retour
		Parent root = FXMLLoader.load(getClass().getResource("ChoixTrader.fxml"));
		Scene newScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(newScene);
		window.show();
	}
	
}
