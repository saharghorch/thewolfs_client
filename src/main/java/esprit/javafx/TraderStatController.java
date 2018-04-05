package esprit.javafx;

import java.net.URL;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tn.esprit.thewolfs_server.services.TraderServiceRemote;

public class TraderStatController implements Initializable {
	
	public TraderStatController() {
		// TODO Auto-generated constructor stub
	}


    @FXML
    private AnchorPane boxConteudo;

    @FXML
    private PieChart chart1;

    @FXML
    private Label labelstat;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		String jndiname = "thewolfs_server-ear/thewolfs_server-ejb/TraderService!tn.esprit.thewolfs_server.services.TraderServiceRemote";
		
		TraderServiceRemote proxy;
		try {
			Context context = new InitialContext();
			proxy = (TraderServiceRemote) context.lookup(jndiname);
			

	        ObservableList<PieChart.Data> pieChartData = null;
	        pieChartData = FXCollections.observableArrayList(new PieChart.Data("First Level ",proxy.calculerLevel1()),new PieChart.Data("Second Level",proxy.calculerLevel2()),new PieChart.Data("Third Level", proxy.calculerLevel3()));
	        
	        chart1.setData(pieChartData);
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
	

         
         
         chart1.getData().stream().forEach(data ->{
             data.getNode().addEventHandler(javafx.scene.input.MouseEvent.ANY, e -> {
               
                 labelstat.setText("Il y a :  "+ (int) data.getPieValue()+" Trader en"+data.getName());
                 
             });
         });
	
	}

}
