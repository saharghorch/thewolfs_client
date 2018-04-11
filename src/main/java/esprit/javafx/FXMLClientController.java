package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.Node;

import javafx.fxml.Initializable;

public class FXMLClientController implements Initializable {
	

    @FXML
    private JFXButton btnAccueil;

    @FXML
    private JFXButton showTradersBtn;

    @FXML
    private JFXButton responsebtn;

    @FXML
    private JFXButton profilebtn;

    @FXML
    private AnchorPane holderPane;

    AnchorPane accueil,showtraders,profile;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

        try {
             accueil = FXMLLoader.load(getClass().getResource("Accueil.fxml"));
             showtraders = FXMLLoader.load(getClass().getResource("ShowTraders.fxml"));
             profile = FXMLLoader.load(getClass().getResource("ProfilClient.fxml"));
             
            
             
            setNode(accueil);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
		
	}
	  private void setNode(Node node) {
	        holderPane.getChildren().clear();
	        holderPane.getChildren().add((Node) node);

	        FadeTransition ft = new FadeTransition(Duration.millis(1500));
	        ft.setNode(node);
	        ft.setFromValue(0.1);
	        ft.setToValue(1);
	        ft.setCycleCount(1);
	        ft.setAutoReverse(false);
	        ft.play();
	    }
	  @FXML
	    void switchAccueil(ActionEvent event) {
		  setNode(accueil);
	    }

	    @FXML
	    void switchProfile(ActionEvent event) {
	    	 setNode(profile);
	    }

	  

	    @FXML
	    void switchshowTraders(ActionEvent event) {
	    	 setNode(showtraders);
	    }

	  
}
