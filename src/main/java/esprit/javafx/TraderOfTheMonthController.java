package esprit.javafx;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TraderOfTheMonthController implements Initializable {
	  @FXML
	    private Button returnbtn;

	    @FXML
	    private AnchorPane holderPane;

	    @FXML
	    void onReturn(ActionEvent event) throws IOException {

	    	Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
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
