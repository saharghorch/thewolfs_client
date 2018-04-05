package esprit.javaee;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainPricing extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		  try {
	            Parent root = FXMLLoader.load(getClass().getResource("PricingInterface.fxml"));
	            Scene scene = new Scene(root);
	            //stage.initStyle(StageStyle.TRANSPARENT);
	            stage.setScene(scene);
	            stage.show();
	       } catch (IOException ex) {
	            Logger.getLogger(MainPricing.class.getName()).log(Level.SEVERE, null, ex);
	        }
		
	}
	public static void main(String[] args) {
		launch(args);

	}

	



}
