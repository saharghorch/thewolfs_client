/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esprit.controllers;




import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.thewolfs_server.entity.Options;
import tn.esprit.thewolfs_server.entity.Status;
import tn.esprit.thewolfs_server.services.OptionsRemote;
/**
 * FXML Controller class
 *
 * @author sahar ghorch
 */
public class AdminController implements Initializable {
	public static int i;
	 @FXML
	    private ComboBox<Status> comboBoxStatus;
	 @FXML
	    private Button updateButton;

	@FXML
    private TableView<Options> tableViewOption;

	@FXML
    private TableColumn<Options, Integer> id;
	@FXML
    private TableColumn<Options, Float> premiumPrice;
    @FXML
    private TableColumn<Options, Date> date;

   
    @FXML
    private TableColumn<Options, String> status;

    @FXML
    private TableColumn<Options, Float> strikePrice;
    @FXML
    private TableColumn<Options, String> type;

    @FXML
    private TableColumn<Options, Integer> asset;

    @FXML
    private TableColumn<Options, Integer> counterparty;

    @FXML
    private TableColumn<Options, Integer> trader;

    @FXML
    private TableColumn<Options, Integer> user;
    
    public ObservableList<Options> list = FXCollections.observableArrayList();
    public ObservableList<Status> comboList = FXCollections.observableArrayList(Status.Canceled,Status.onHold,Status.Valid);

    @FXML
    private TextField textStrikePrice;
    
    Options option = new Options();
    
    @FXML
    private Button buttonAdd;
    
    @FXML
    private Button deleteOption;
    List<Options> arr = new ArrayList();
    @FXML
    void ActionUpdate(ActionEvent event) {
    	
    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			context = new InitialContext();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			proxy.UpdateOptionStatus(arr.get(i).getId(),comboBoxStatus.getValue());
			afficher(proxy);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    @FXML
    void ComboChanged(ActionEvent event) {
    
    }
    @FXML
    void ActionButtonAdd(ActionEvent event) {

    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			
			context = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//System.out.println("ccccccccccccccccc");
		
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			//System.out.println("eeeeeeeeeeeeeeeeeeee");
			Options option = new Options();
			//System.out.println(textStrikePrice.getText());
//option.setStrike_price(Float.parseFloat(textStrikePrice.getText()));

//System.out.println(option);
proxy.addOption(option);
System.out.println("ajout avec succes");
}
		catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    	
	
    
    
    public void afficher(OptionsRemote proxy){
    	
    	tableViewOption.getItems().clear();
		arr= proxy.findAll();
		System.out.println(arr);
    	for (int i=0;i<arr.size();i++){
    	list.add(arr.get(i));	
    	}
    
    	
    	id.setCellValueFactory(new PropertyValueFactory<Options, Integer>("id"));
    	date.setCellValueFactory(new PropertyValueFactory<Options, Date>("expiration_date"));
    	premiumPrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("premium_price"));
    	status.setCellValueFactory(new PropertyValueFactory<Options, String>("status"));
    	strikePrice.setCellValueFactory(new PropertyValueFactory<Options, Float>("strike_price"));
    	type.setCellValueFactory(new PropertyValueFactory<Options, String>("type"));
    	asset.setCellValueFactory(new PropertyValueFactory<Options, Integer>("asset"));
    	counterparty.setCellValueFactory(new PropertyValueFactory<Options, Integer>("counterparty"));
    	trader.setCellValueFactory(new PropertyValueFactory<Options, Integer>("trader"));
    	user.setCellValueFactory(new PropertyValueFactory<Options, Integer>("user"));
		tableViewOption.setItems(list);
    }
    
    @FXML
    void ActionButtonDelete(ActionEvent event) {
    	i = tableViewOption.getSelectionModel().getSelectedIndex();
    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			context = new InitialContext();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			proxy.deleteOption(arr.get(i).getId());
			afficher(proxy);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

    @FXML
    void OnMouseClickedTable(MouseEvent event) {

    	i = tableViewOption.getSelectionModel().getSelectedIndex();
    	System.out.println(i);
    	System.out.println(arr.get(i).getId());
    	
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	comboBoxStatus.setItems(comboList);
    	String jndiname="thewolfs_server-ear/thewolfs_server-ejb/OptionsManager!tn.esprit.thewolfs_server.services.OptionsRemote";
		Context context=null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			OptionsRemote proxy=(OptionsRemote) context.lookup(jndiname);
			afficher(proxy);
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    
    
    
}
