package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class MarksController implements Initializable {

	@FXML
    private JFXTextField emailtf;

    @FXML
    private Button traderofthemonthbtn;

    @FXML
    void OnTraderMonth(ActionEvent event)  throws IOException {
     		Parent root = FXMLLoader.load(getClass().getResource("TraferOfthMonth.fxml"));
     		Scene newScene= new Scene(root);
     		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
     		window.setScene(newScene);
     		window.show();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	

}
