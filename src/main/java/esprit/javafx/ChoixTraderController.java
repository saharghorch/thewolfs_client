package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChoixTraderController implements Initializable {
	@FXML
    private Button earnBtn;

    @FXML
    private Button investBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@FXML
    void Onearn(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("TraderEarn.fxml"));
  		Scene newScene= new Scene(root);
  		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
  		window.setScene(newScene);
  		window.show();
	    }
    

    @FXML
    void Oninvest(ActionEvent event) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource("TraderInvest.fxml"));
  		Scene newScene= new Scene(root);
  		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
  		window.setScene(newScene);
  		window.show();
	    }

    

   
	 @FXML
	    void onReturn(ActionEvent event) throws IOException {

	    	Parent root = FXMLLoader.load(getClass().getResource("FXMLClient.fxml"));
  		Scene newScene= new Scene(root);
  		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
  		window.setScene(newScene);
  		window.show();
	    }
}
