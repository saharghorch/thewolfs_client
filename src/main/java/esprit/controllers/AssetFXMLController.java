/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.controllers;

import java.net.URL;



import java.util.List;

import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.thewolfs_server.entity.Asset;
import tn.esprit.thewolfs_server.services.AssetServiceRemote;

import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AssetFXMLController implements Initializable {
   
   

    @FXML
    private Label sharesnumberlabel;

    @FXML
    private TableColumn<Asset,java.sql.Date> EXdatecol;
    @FXML
    private TableColumn<Asset,java.sql.Date> STdatecol;
    @FXML
    private TableColumn<Asset, Integer> sharesnumbercol;
    @FXML
    private TableColumn<Asset, Double> totalvaluecol;
    @FXML
    private Button deletebtn;
    @FXML
    private Button backbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private TextField TFsharesnumber;
    @FXML
    private TextField TFtotalvalue;
    @FXML
    private Button addbtn;
    @FXML
    private Label startdatelabel;
    @FXML
    private Label expirationdatelabel;
    @FXML
    private DatePicker picstartdate;
    @FXML
    private TextField recherche;  
   
    
    @FXML
    private DatePicker picexpirationdate;
    @FXML
    private TableView<Asset> tableview = new TableView<Asset>();
    private ObservableList<Asset> data1 = FXCollections.observableArrayList();
    private ObservableList<Asset> data2 = FXCollections.observableArrayList();
       @FXML
    private Button buy;
/*
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	try {
			remplir();
			
			String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
	        Context ctx = new InitialContext();
	        AssetServiceRemote proxy = (AssetServiceRemote) ctx.lookup(jndiName);

			List<Asset> asset = proxy.displayAllAssets();
			for (Asset ass : asset) {
				data2.add(ass);
			}
		
	    	recherche.textProperty().addListener((observable, oldValue, newValue) -> {
				
				Integer v = new Integer(newValue);
				data2.clear();
				data2.setAll(asset.stream().filter(e -> e.getSharesNumber()>= v).collect(Collectors.toList()));
				tableview.setItems(data2);
				System.out.println("done");
	     });
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }    

    @FXML
    private void add(ActionEvent event) throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
    Context ctx = new InitialContext();
    AssetServiceRemote proxy = (AssetServiceRemote) ctx.lookup(jndiName);
   Asset as=new Asset();
   as.setSharesNumber(Integer.parseInt(TFsharesnumber.getText()));
   as.setOptionStartDate(java.sql.Date.valueOf(picstartdate.getValue()));
   as.setOptionExpirationDate(java.sql.Date.valueOf(picexpirationdate.getValue()));
   as.setTotalValue(Double.parseDouble(TFtotalvalue.getText()));
  
   proxy.displayAllAssets();

   proxy.addAsset(as);
  
remplir();
  
    }
    public void remplir() throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
        Context ctx = new InitialContext();
        AssetServiceRemote proxy = (AssetServiceRemote) ctx.lookup(jndiName);
        
        sharesnumbercol.setCellValueFactory(new PropertyValueFactory<>("SharesNumber"));
        EXdatecol.setCellValueFactory(new PropertyValueFactory<>("OptionExpirationDate"));
        STdatecol.setCellValueFactory(new PropertyValueFactory<>("OptionStartDate"));
        totalvaluecol.setCellValueFactory(new PropertyValueFactory<>("TotalValue"));
      Asset c = new Asset();
        List<Asset> o = proxy.displayAllAssets();
        
		for (Asset  asset : o) {
			data1.add(asset);
			System.out.print("qqqqqqqq");
		}
		//tableviewasset.refresh();
		tableview.setItems(data1);	
	}

   
@FXML
void update(ActionEvent event) throws NamingException {
	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
	Context ctx = new InitialContext();
	AssetServiceRemote proxy=(AssetServiceRemote) ctx.lookup (jndiName);
        if (tableview.getSelectionModel().getSelectedItem() != null) {
            Asset a = tableview.getSelectionModel().getSelectedItem();
            a.setSharesNumber((Integer.valueOf(TFsharesnumber.getText())));
            a.setOptionStartDate(java.sql.Date.valueOf(picstartdate.getValue()));
            a.setOptionExpirationDate(java.sql.Date.valueOf(picexpirationdate.getValue()));
            a.setTotalValue(Double.valueOf(TFtotalvalue.getText()));
            
     	proxy.updateAsset(a);
        	 try {
        		 remplir();
   			} catch (NamingException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
            
}

}


 public void mouse() {
	if (tableview.getSelectionModel().getSelectedItem() !=null)
	{
		Asset a = tableview.getSelectionModel().getSelectedItem();
		TFsharesnumber.setText(a.getSharesNumber()+"");
		TFtotalvalue.setText(a.getTotalValue()+"");
	}

}
 @FXML
 public void delete(ActionEvent event) throws NamingException {
	 String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
		Context ctx = new InitialContext();
		AssetServiceRemote proxy=(AssetServiceRemote) ctx.lookup (jndiName);
		int idAsset = tableview.getSelectionModel().getSelectedItem().getId();
		proxy.deleteAsset(idAsset);
		List<Asset> list = proxy.displayAllAssets();
		ObservableList<Asset> items = FXCollections.observableArrayList(list);
		tableview.setItems(items);
		
 }

  @FXML
 void buy(ActionEvent event) {
	 Parent root;
	try {
		root = FXMLLoader.load(getClass().getResource("/esprit/javafx/FXMLTrader2Asset.fxml"));
		 Scene scene=new Scene(root);
		 Stage st=new Stage();
		 st.setScene(scene);
		 st.show();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	 
	 Stage stage=(Stage) buy.getScene().getWindow();
	 stage.close();

 }
  @FXML
  void back(ActionEvent event) {

  	 Parent root;
  		try {
  			root = FXMLLoader.load(getClass().getResource("/esprit/javafx/Accueil.fxml"));
  			 Scene scene=new Scene(root);
  			 Stage st=new Stage();
  			 st.setScene(scene);
  			 st.show();
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}

  		 
  		 Stage stage=(Stage) backbtn.getScene().getWindow();
  		 stage.close();

  	 }
    
}

    
    

