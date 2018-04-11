
package esprit.javafx;
	
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainAsset extends Application {
	@Override
	  public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLTraderAsset.fxml"));
            Scene scene = new Scene(root);
            
            stage.initStyle(StageStyle.TRANSPARENT);
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
           
        	Logger.getLogger(MainAsset.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
	
   
    public static void main(String[] args) {
        launch(args);
    }
    
}