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
import tn.esprit.thewolfs_server.services.AccountServiceRemote;

public class ChartAccountInterfaceController implements Initializable{

    @FXML
    private PieChart chartAccount;

    @FXML
    private Label labelOfChart;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String jndiName="thewolfs_server-ear/thewolfs_server-ejb/AccountService!tn.esprit.thewolfs_server.services.AccountServiceRemote";
    	Context context;
		try {
			context = new InitialContext();
			AccountServiceRemote proxy;
			proxy = (AccountServiceRemote) context.lookup(jndiName);
			ObservableList<PieChart.Data> pieChartData = null;
	        pieChartData = FXCollections.observableArrayList(new PieChart.Data("EUR Currency Account",proxy.numberAccountEUR()),new PieChart.Data("USD Currency Account",proxy.numberAccountUSD()),new PieChart.Data("SAR Currency Account", proxy.numberAccountSAR()));
	        chartAccount.setData(pieChartData);
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
	
        chartAccount.getData().stream().forEach(data ->{
        data.getNode().addEventHandler(javafx.scene.input.MouseEvent.ANY, e -> {
        labelOfChart.setText("There are  "+ (int) data.getPieValue()+data.getName());
                 
             });
         });
		
	}

}
