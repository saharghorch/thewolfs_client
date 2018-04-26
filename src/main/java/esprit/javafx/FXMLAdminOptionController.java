package esprit.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.thewolfs_server.entity.StockOption;
import tn.esprit.thewolfs_server.services.StockOptionServiceRemote;

public class FXMLAdminOptionController implements Initializable{

    @FXML
    private TableView<StockOption> tableview;

    @FXML
    private TableColumn<StockOption, ?> symboleCol;

    @FXML
    private TableColumn<StockOption, ?> underlyingPriceCol;

    @FXML
    private TableColumn<StockOption, ?> volatilityCol;

    @FXML
    private TableColumn<StockOption, ?> interestRateCol;

    @FXML
    private TableColumn<StockOption, ?> typeCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String jndiNameStockOption="thewolfs_server-ear/thewolfs_server-ejb/StockOptionService!tn.esprit.thewolfs_server.services.StockOptionServiceRemote";
    	Context contextStockOption;
		try {
			contextStockOption = new InitialContext();
			StockOptionServiceRemote proxyStockOption=(StockOptionServiceRemote) contextStockOption.lookup(jndiNameStockOption);
			symboleCol.setCellValueFactory(new PropertyValueFactory<>("symbole"));
			underlyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("underlyingPrice"));
			volatilityCol.setCellValueFactory(new PropertyValueFactory<>("volatility"));
			interestRateCol.setCellValueFactory(new PropertyValueFactory<>("riskFreeInterestRate"));
		    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
			
			List<StockOption> stockOptions=proxyStockOption.displayAllStockOptions();
			List<StockOption> options=new ArrayList<>();
			for(StockOption stock:stockOptions){
				if (stock.getStatus()==null){
				options.add(stock);
				}
			}
			ObservableList<StockOption> items = FXCollections.observableArrayList(options);
		    tableview.setItems(items);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

}
