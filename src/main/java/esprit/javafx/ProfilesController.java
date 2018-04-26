package esprit.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;

public class ProfilesController implements Initializable{
	@FXML
    private TextField traderTF;
	@FXML
    private TextField traderTF1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		traderTF.setText(Session.getUser().getFirst_name()+" "+Session.getUser().getLast_name());
		traderTF1.setText(Session.getUser().getEmail());
		
	}

}
