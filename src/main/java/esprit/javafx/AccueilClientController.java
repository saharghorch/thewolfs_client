package esprit.javafx;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;





public class AccueilClientController implements Initializable {
	@FXML
    private Label usdEurTF;

    @FXML
    private Label usdaudlabel;

    @FXML
    private Label usdgbplabel;

    @FXML
    private Label usdchflabel;

    @FXML
    private Label usdjpylabel;

    @FXML
    private Label usdcadlabel;

    @FXML
    private Label usdaedlabel;

    @FXML
    private Label eurgbplabel;
    

	 // essential URL structure is built using constants
   public static final String ACCESS_KEY = "77526960a9010707e2229fd8141c5b0f";
   public static final String BASE_URL = "http://apilayer.net/api/";
   public static final String ENDPOINT = "live";

   // this object is used for executing requests to the (REST) API
   static CloseableHttpClient httpClient = HttpClients.createDefault();
   
   
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		 HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY);
	        CloseableHttpResponse response;
	        try {
	            response = httpClient.execute(get);
	            HttpEntity entity = response.getEntity();
	            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));
	          Double  usdeur = exchangeRates.getJSONObject("quotes").getDouble("USDEUR");
	     usdEurTF.setText(Double.toString(usdeur));
	       
	     
	      Double  usdaud = exchangeRates.getJSONObject("quotes").getDouble("USDAUD");
	      usdaudlabel.setText(Double.toString(usdaud));
	     
	      Double  usdGBP = exchangeRates.getJSONObject("quotes").getDouble("USDGBP");
	      usdgbplabel.setText(Double.toString(usdGBP));
	      
	      Double  USDCHF = exchangeRates.getJSONObject("quotes").getDouble("USDCHF");
	      usdchflabel.setText(Double.toString(USDCHF));
	      
	      
	      
	      Double  usdusd = exchangeRates.getJSONObject("quotes").getDouble("USDUSD");
	      
	      Double usdjpy = usdusd / 0.00936183;
	      DecimalFormat usjp = new DecimalFormat("000.00000"); 
	      usdjpylabel.setText((usjp.format(usdjpy)));
	     
	      DecimalFormat usca = new DecimalFormat("0.00000"); 
	      Double usdcad = usdusd / 0.787483;
	      usdcadlabel.setText(usca.format(usdcad));

	      
	      Double  usdaed = exchangeRates.getJSONObject("quotes").getDouble("USDAED");
	      usdaedlabel.setText(Double.toString(usdaed));
	      
	      DecimalFormat eubg = new DecimalFormat("0.00000");
	      Double eurgbp = usdGBP /usdeur; 
	      eurgbplabel.setText(eubg.format(eurgbp));
	      response.close();
	      
	      
	          response.close();
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		
	}

    

}
