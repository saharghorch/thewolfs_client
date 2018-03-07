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

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AssetFXMLController implements Initializable {
   
   

    @FXML
    private Label sharesnumberlabel;
    @FXML
    private TableColumn<Asset,java.sql.Date> colstartdate;
    @FXML
    private TableColumn<Asset,java.sql.Date> colexpirationdate;
    @FXML
    private TableColumn<Asset, Integer> colsharesnumber;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnupdate;
    @FXML
    private TextField textfieldsharesnumber;
    @FXML
    private Button btnajout;
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
    private TableView<Asset> tableviewasset = new TableView<Asset>();
    private ObservableList<Asset> data1 = FXCollections.observableArrayList();
    private ObservableList<Asset> data2 = FXCollections.observableArrayList();
   

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
				data2.setAll(asset.stream().filter(e -> e.getShares_number()>= v).collect(Collectors.toList()));
				tableviewasset.setItems(data2);
				System.out.println("done");
	     });
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }    

    @FXML
    private void onclickadd(ActionEvent event) throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
    Context ctx = new InitialContext();
    AssetServiceRemote proxy = (AssetServiceRemote) ctx.lookup(jndiName);
   Asset as=new Asset();
   as.setShares_number(Integer.parseInt(textfieldsharesnumber.getText()));
   as.setOption_Start_Date(java.sql.Date.valueOf(picstartdate.getValue()));
   as.setOptions_Expiration_Date(java.sql.Date.valueOf(picexpirationdate.getValue()));
   proxy.addAsset(as);
  
   remplir();
  
    }
    public void remplir() throws NamingException {
    	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
        Context ctx = new InitialContext();
        AssetServiceRemote proxy = (AssetServiceRemote) ctx.lookup(jndiName);
        
        colsharesnumber.setCellValueFactory(new PropertyValueFactory<>("Shares_number"));
        colexpirationdate.setCellValueFactory(new PropertyValueFactory<>("Options_Expiration_Date"));
        colstartdate.setCellValueFactory(new PropertyValueFactory<>("Option_Start_Date"));
      
        Asset c = new Asset();
        List<Asset> o = proxy.displayAllAssets();
        
		for (Asset  asset : o) {
			data1.add(asset);
			System.out.print("qqqqqqqq");
		}
		//tableviewasset.refresh();
		tableviewasset.setItems(data1);	
	}


@FXML
void updatebtn(ActionEvent event) throws NamingException {
	String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
	Context ctx = new InitialContext();
	AssetServiceRemote proxy=(AssetServiceRemote) ctx.lookup (jndiName);
        if (tableviewasset.getSelectionModel().getSelectedItem() != null) {
            Asset a = tableviewasset.getSelectionModel().getSelectedItem();
            a.setShares_number((Integer.valueOf(textfieldsharesnumber.getText())));
            //a.setOption_Start_Date(java.sql.Date.valueOf(picstartdate.getValue()));
            //a.setOption_Start_Date(java.sql.Date.valueOf(picexpirationdate.getValue()));
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
	if (tableviewasset.getSelectionModel().getSelectedItem() !=null)
	{
		Asset a = tableviewasset.getSelectionModel().getSelectedItem();
		textfieldsharesnumber.setText(a.getShares_number()+"");
	}

}
 public void deletebtn(ActionEvent event) throws NamingException {
	 String jndiName ="thewolfs_server-ear/thewolfs_server-ejb/AssetServices!tn.esprit.thewolfs_server.services.AssetServiceRemote";
		Context ctx = new InitialContext();
		AssetServiceRemote proxy=(AssetServiceRemote) ctx.lookup (jndiName);
		int idAsset = tableviewasset.getSelectionModel().getSelectedItem().getId();
		proxy.deleteAsset(idAsset);
		List<Asset> list = proxy.displayAllAssets();
		ObservableList<Asset> items = FXCollections.observableArrayList(list);
		tableviewasset.setItems(items);
		
 }

 
    
}

    
    

