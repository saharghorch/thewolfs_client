/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.javafx;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class SpaceTraderController implements Initializable {

	 @FXML
	    private JFXButton OptionsBtn;

	    @FXML
	    private JFXButton PricingBtn;

	    @FXML
	    private JFXButton ProfileBtn;

	    @FXML
	    private JFXButton PortfolioBtn;

	    @FXML
	    private JFXButton AccountsBtn;

	    @FXML
	    private JFXButton AlertsBtn;

	    @FXML
	    private JFXButton ForumBtn;

	    @FXML
	    private AnchorPane holderPane;
    
    AnchorPane options,pricing,profile,portfolio,accounts,alerts,discussion;
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        try {
             options = FXMLLoader.load(getClass().getResource("FXMLTraderOption.fxml"));
             pricing = FXMLLoader.load(getClass().getResource("PricingInterface.fxml"));
             profile = FXMLLoader.load(getClass().getResource("Profiles.fxml"));
             portfolio = FXMLLoader.load(getClass().getResource("FXMLTraderPortfolio.fxml"));
             accounts = FXMLLoader.load(getClass().getResource("FXMLTraderAccount.fxml"));
             alerts = FXMLLoader.load(getClass().getResource("Controls.fxml"));
             discussion = FXMLLoader.load(getClass().getResource("FXMLTraderForum.fxml"));
             setNode(profile);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Set selected node to a content holder
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
    void showAccounts(ActionEvent event) {
    	setNode(accounts);
    }

    @FXML
    void showAlerts(ActionEvent event) {
    	setNode(alerts);
    }

    @FXML
    void showForum(ActionEvent event) {
    	setNode(discussion);
    }

    @FXML
    void showOptions(ActionEvent event) {
    	setNode(options);
    }

    @FXML
    void showPortfolio(ActionEvent event) {
    	setNode(portfolio);
    }

    @FXML
    void showPricing(ActionEvent event) {
    	setNode(pricing);
    }

    @FXML
    void showProfile(ActionEvent event) {
    	setNode(profile);
    }
        
    

}
