package esprit.javafx;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginTraderController {

	
	   @FXML
	    private AnchorPane holderPane;

	    @FXML
	    private JFXTextField emailTF;

	    @FXML
	    private JFXPasswordField passwordTF;

	    @FXML
	    private Button loginBtn;

	    @FXML
	    void login(ActionEvent event) {
	    	
	    	LogInOut l = new LogInOut(emailTF.getText(), passwordTF.getText() ) ;
		    String s = l.login(); 
		    Scene x;
		    
		    if (s.equals("verifie your cridentials ")){
		    	 

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Trader adding");
				alert.setHeaderText("you have an empty field");
				alert.showAndWait();
		    }else if (s.equals("level")){
		    	
		    	try {
		 			x = new Scene(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
		 			Stage stage = new Stage();
		 		    stage.setScene(x);
		 		    stage.show();
		 		} catch (IOException e) {
		 			e.printStackTrace();
		 		}	

		    	Stage stage = (Stage) loginBtn.getScene().getWindow();
		        stage.close();
		    }
		    
	 	    

	    }

	    }

