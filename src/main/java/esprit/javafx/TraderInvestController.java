package esprit.javafx;


import java.io.IOException;
import java.net.URL;
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

public class TraderInvestController implements Initializable{
	
    @FXML
    private Button returnbtn;

    @FXML
    private AnchorPane holderPane;

    @FXML
    private JFXTextField amountPTF;

    @FXML
    private ComboBox<String> yearscombo;

    @FXML
    private Button confirmAbtn;
    
    @FXML
    private JFXTextField periodtf;
   

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
  		public static Double FV;
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

				
				FV = Double.valueOf(amountPTF.getText());
				
				// Compound Interet
				if (interetCombo.getValue() == "Compound Interest") {
					
					
					             Interest interest = new Interest(Double.valueOf(amountPTF.getText()), 0.05d,
							                          Integer.valueOf(periodtf.getText()),TypeRate.valueOf(yearscombo.getValue()));
					                                
					             nbyears = Integer.valueOf(periodtf.getText());
					             type =TypeRate.valueOf(yearscombo.getValue());
					           //ajout dans la base
					           proxy.addInterest(interest);
					           
					           //resultat du calcul des interets
					            Compound = proxy.CalculCompoundInterestPrincipale(Double.valueOf(amountPTF.getText()), 0.15d,
							         Integer.valueOf(periodtf.getText()), TypeRate.valueOf(String.valueOf(yearscombo.getValue())));
	                          // proxy.CalculCompoundInterestPrincipale(amount, rate, period, typeRate)
			Parent root = FXMLLoader.load(getClass().getResource("ResultCompoundInterestFuture.fxml"));
   		Scene newScene= new Scene(root);
   		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
   		window.setScene(newScene);
   		window.show();
				} else {
		             Interest interest = new Interest(Double.valueOf(amountPTF.getText()), 0.05d,
				     Integer.valueOf(periodtf.getText()));
		             nbyears = Integer.valueOf(periodtf.getText());
		             proxy.addInterest(interest);
		             Simple = proxy.CalculSimpleInterestPrincipale(Double.valueOf(amountPTF.getText()), 0.05d,  Integer.valueOf(periodtf.getText()));
		            	
		             
                     //ouvrir la page du resultat du simple interet 
		             Parent root = FXMLLoader.load(getClass().getResource("ResultSimpleInterestFuture.fxml"));
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
	    	
	    	Parent root = FXMLLoader.load(getClass().getResource("ChoixTrader.fxml"));
	  		Scene newScene= new Scene(root);
	  		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	  		window.setScene(newScene);
	  		window.show();
		    }

}
