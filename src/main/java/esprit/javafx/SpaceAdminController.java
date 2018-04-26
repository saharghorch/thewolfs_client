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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SpaceAdminController implements Initializable {

    

    @FXML
    private JFXButton TradersBtn;

    @FXML
    private JFXButton OptionsBtn;

    @FXML
    private JFXButton PortfolioBtn;

    @FXML
    private JFXButton AccountsBtn;

    @FXML
    private JFXButton ForumBtn;

    @FXML
    private AnchorPane holderPane;
    
    AnchorPane traders;
    AnchorPane portfolios;
    AnchorPane options;
    AnchorPane accounts;
    AnchorPane discussion;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
        	
        	 traders = FXMLLoader.load(getClass().getResource("FXMLAdminTrader.fxml"));
             options = FXMLLoader.load(getClass().getResource("FXMLAdminOption.fxml"));
             portfolios = FXMLLoader.load(getClass().getResource("FXMLAdminPortfolio.fxml"));
             accounts = FXMLLoader.load(getClass().getResource("FXMLAdminAccount.fxml"));
             discussion = FXMLLoader.load(getClass().getResource("FXMLAdminForum.fxml"));
             setNode(traders);
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
    void showForum(ActionEvent event) {
    	setNode(discussion);
    }

    @FXML
    void showOptions(ActionEvent event) {
    	setNode(options);
    }

    @FXML
    void showPortfolios(ActionEvent event) {
    	setNode(portfolios);
    }



    @FXML
    void showTraders(ActionEvent event) {
    	setNode(traders);
    }

	

}
