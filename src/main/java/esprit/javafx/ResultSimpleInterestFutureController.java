package esprit.javafx;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
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


public class ResultSimpleInterestFutureController implements Initializable{
	
	 @FXML
	    private Label Atf;

	    @FXML
	    private Button acceptBtn;

	    @FXML
	    private Button declineBtn;

	    @FXML
	    private Button returnBtn;

	    @FXML
	    private Label Ptf;

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
			//récupération des données
			Double p =TraderInvestController.FV;
			int n =TraderInvestController.nbyears;
			Double s =TraderInvestController.Simple;
			Double r =0.05d;
			 DecimalFormat formaa = new DecimalFormat(".00");
			//remplissage des textfields
			Ptf.setText(formaa.format(s));
			periodtf.setText(String.valueOf(n));
			Atf.setText(formaa.format(p));
			
			//réglage de l'echelle
		    y.setAutoRanging(false);
		    y.setLowerBound(s-5000);
		    y.setUpperBound(p+1000);
		    y.setTickUnit((s-p)/n);
		 fillChart();
			
		}
		private void fillChart() {
			Double p =TraderInvestController.FV;
			int n =TraderInvestController.nbyears;
			Double s =TraderInvestController.Simple;
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
			  Parent root = FXMLLoader.load(getClass().getResource("TraderInvest.fxml"));
		  		Scene newScene= new Scene(root);
		  		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		  		window.setScene(newScene);
		  		window.show();

		    }

		    @FXML
		    void OnReturn(ActionEvent event) throws IOException {
		    	
				  Parent root = FXMLLoader.load(getClass().getResource("TraderInvest.fxml"));
			  		Scene newScene= new Scene(root);
			  		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			  		window.setScene(newScene);
			  		window.show();


		    }

		    @FXML
		    void Onaccept(ActionEvent event) {

		    }

}
