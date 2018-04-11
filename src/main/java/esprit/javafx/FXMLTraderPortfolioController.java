package esprit.javafx;

import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.thewolfs_server.entity.Account;
import tn.esprit.thewolfs_server.entity.Portfolio;
import tn.esprit.thewolfs_server.entity.StockOption;
import tn.esprit.thewolfs_server.services.PortfolioServiceRemote;

public class FXMLTraderPortfolioController implements Initializable {
	@FXML
    private TableView<StockOption> tableview1;
	
    @FXML
    private  TextField creationDateTF;

    @FXML
    private  TextField cashTF;

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

    @FXML
    private TableColumn<StockOption, ?> statusCol;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Context context;
		try {
			String jndiName="thewolfs_server-ear/thewolfs_server-ejb/PortfolioService!tn.esprit.thewolfs_server.services.PortfolioServiceRemote";
			context = new InitialContext();
			PortfolioServiceRemote proxy=(PortfolioServiceRemote) context.lookup(jndiName);
			Integer idPortfolio=Session.getUser().getPortfolio().getId();
			Portfolio portfolio=proxy.getPortfolioById(idPortfolio);
			creationDateTF.setText(portfolio.getCreationDate().toString());
			cashTF.setText(portfolio.getCash().toString());
			symboleCol.setCellValueFactory(new PropertyValueFactory<>("symbole"));
			underlyingPriceCol.setCellValueFactory(new PropertyValueFactory<>("underlyingPrice"));
			volatilityCol.setCellValueFactory(new PropertyValueFactory<>("volatility"));
			interestRateCol.setCellValueFactory(new PropertyValueFactory<>("riskFreeInterestRate"));
		    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
			statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
			List<StockOption> stockOptions=proxy.findStockOptionsValid();
			ObservableList<StockOption> items = FXCollections.observableArrayList(stockOptions);
		    tableview1.setItems(items);
		
	       
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}

}
