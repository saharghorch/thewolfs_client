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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane holderPane;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnPricing;
    @FXML
    private JFXButton btnContacts;
    @FXML
    private JFXButton btnWidgets;
    @FXML
    private JFXButton btnProfile;
    @FXML
    private JFXButton btnAlerts;
    @FXML 
    private Button deconnectBtn;
    
    AnchorPane statistiques,alerts,marks,profiles,widgets,controls,trader;
    @FXML
    private JFXButton btnControls;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        try {
             statistiques = FXMLLoader.load(getClass().getResource("TraderStat.fxml"));
             alerts = FXMLLoader.load(getClass().getResource("Alerts.fxml"));
             marks = FXMLLoader.load(getClass().getResource("Pricing.fxml"));
             profiles = FXMLLoader.load(getClass().getResource("Profiles.fxml"));
             widgets = FXMLLoader.load(getClass().getResource("Widgets.fxml"));
             controls = FXMLLoader.load(getClass().getResource("Controls.fxml"));
             trader = FXMLLoader.load(getClass().getResource("Trader.fxml"));
             
            setNode(trader);
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
    private void switchPricing(ActionEvent event) {
        setNode(marks);
    }

    @FXML
    private void switchContacts(ActionEvent event) {
        setNode(statistiques);
    }

    @FXML
    private void switchWidget(ActionEvent event) {
        setNode(widgets);
    }

    @FXML
    private void switchProfile(ActionEvent event) {
        setNode(profiles);
    }

    @FXML
    private void switchAlert(ActionEvent event) {
        setNode(alerts);
    }

    @FXML
    private void switchControls(ActionEvent event) {
        setNode(controls);
    }
    
@FXML
    private void switchHome(ActionEvent event) {
        setNode(trader);
    }
@FXML
 	private void Ondeconnect(ActionEvent event) throws IOException {
	Parent root = FXMLLoader.load(getClass().getResource("LoginTrader.fxml"));
	Scene newScene= new Scene(root);
	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	window.setScene(newScene);
	window.show();
}

}
