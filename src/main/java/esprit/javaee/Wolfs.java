/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.javaee;

import javax.naming.NamingException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author sahar ghorch
 */
public class Wolfs extends Application {
	public static void main(String[] args) throws NamingException {
		launch(args);
	}
    @Override
    public void start(Stage stage)throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("TraderOption.fxml"));
         Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        
}
}
