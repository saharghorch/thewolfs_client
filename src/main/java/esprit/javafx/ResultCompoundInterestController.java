package esprit.javafx;


import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.thewolfs_server.entity.TypeRate;


public class ResultCompoundInterestController implements Initializable{
	
	
	   @FXML
	    private Label Ptf;

	    @FXML
	    private Label Ytf;

	    @FXML
	    private Button acceptBtn;

	    @FXML
	    private Button declineBtn;

	    @FXML
	    private Button returnBtn;

	    @FXML
	    private Label Atf;

	    @FXML
	    private Label periodtf;
	    
	    
	    
	    @FXML
	    private AnchorPane parent;
	    @FXML
	    private LineChart<?, ?> lineChart;
	    @FXML
	    private NumberAxis y;
	    @FXML
	    private NumberAxis x;
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			
	    	// récupération des données 
	    	Double p =TraderEarnController.principale;
			int n =TraderEarnController.nbyears;
			Double s =TraderEarnController.Compound;
			Double r =0.05d;
			TypeRate t=	TraderEarnController.type;
			
			 DecimalFormat formaa = new DecimalFormat(".00"); 

			//remplissage des textfields de l'interface courante 
			Ptf.setText(formaa.format(p));
			Ytf.setText(String.valueOf(t));
			Atf.setText(formaa.format(s));
			periodtf.setText(String.valueOf(n));
			
			 //réglage de l'echelle
		    y.setAutoRanging(false);
		    y.setLowerBound(p-5000);
		    y.setUpperBound(s+1000);
		    y.setTickUnit((s-p)/n);
		 fillChart();
		}
	    
	    
	    private void fillChart() {
			Double p =TraderEarnController.principale;
			int n =TraderEarnController.nbyears;
			Double s =TraderEarnController.Compound;
	        XYChart.Series series = new XYChart.Series(); 
	        
	        	Double vertical = Double.valueOf((s-p)/n);
	            	for(int i=0;i<n+1;i++){
	        		series.getData().add(new XYChart.Data(i,p+(vertical*i)));
	        	}
	       
	        
	        XYChart.Series series2 = new XYChart.Series();
	    	for(int i=0;i<n+1;i++){
        		series2.getData().add(new XYChart.Data(i,p));
        	}

	       
	        lineChart.getData().addAll(series,series2);
	    }
	    
	    @FXML
	    void OnDecline(ActionEvent event) throws IOException {
	    	
	    	Parent root = FXMLLoader.load(getClass().getResource("TraderEarn.fxml"));
	  		Scene newScene= new Scene(root);
	  		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	  		window.setScene(newScene);
	  		window.show();

	    }

	    @FXML
	 
	    void OnReturn(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("TraderEarn.fxml"));
	  		Scene newScene= new Scene(root);
	  		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	  		window.setScene(newScene);
	  		window.show();

	    }
	    @FXML
	    void Onaccept(ActionEvent event) {

	    }

		

}
