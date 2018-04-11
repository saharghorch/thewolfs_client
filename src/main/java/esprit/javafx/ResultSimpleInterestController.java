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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.Label;

public class ResultSimpleInterestController implements Initializable{
	
	   @FXML
	    private Label Ptf;

	    @FXML
	    private Label Ytf;
	    
	    @FXML
	    private Label periodtf;

	    @FXML
	    private Button acceptBtn;

	    @FXML
	    private Button declineBtn;

	    @FXML
	    private Button returnBtn;

	    @FXML
	    private Label Atf;
	    
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
			Double p =TraderEarnController.principale;
			int n =TraderEarnController.nbyears;
			Double s =TraderEarnController.Simple;
			Double r =0.05d;
			
			 DecimalFormat formaa = new DecimalFormat(".00"); 

		      
			//remplissage des textfields
			Ptf.setText(formaa.format(p));
			periodtf.setText(String.valueOf(n));
			Atf.setText(formaa.format(s));
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
			Double s =TraderEarnController.Simple;
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
		    	Parent root = FXMLLoader.load(getClass().getResource("Traderearn.fxml"));
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
		    	 msgApi(24200571, 300d, 10d);

		    }
		    static String KeySms;
		    public void msgApi(int phone, Double rev, Double dep) {
		        String ACCOUNT_SID = "AC6888bc69e2d3cf528eaf4c9e0ed0bbbf";
		        String AUTH_TOKEN = "5fae791985ae3ec04f3be902aed363fb";
		        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		        String tophone = Integer.toString(phone);

		        System.out.println(phone);
		        System.out.println(tophone);

		        Random rand = new Random();

		        String revstr = Double.toString(rev);
		        String depstr = Double.toString(dep);
		        String msg = "Votre compte est en rouge ! votre dépenses : " + depstr + "Dt et votre revenues : " + revstr;
		        Message message = Message.creator(new PhoneNumber("+216" + tophone),new PhoneNumber("+12015353131"),msg).create();
		        System.out.println(message.getSid());
		    }


}
