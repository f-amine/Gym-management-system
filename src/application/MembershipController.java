package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import appClasses.Equipment;
import appClasses.MembershipOffer;
import appClasses.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MembershipController implements Initializable {



	
    @FXML
    private TableView<MembershipOffer> test;

    @FXML
    private TableColumn<MembershipOffer, String> col_Id;

    @FXML
    private TableColumn<MembershipOffer, String> col_Name;

    @FXML
    private TableColumn<MembershipOffer, String> col_Price;

    @FXML
    private TableColumn<MembershipOffer, String> col_Quantity;

    
    
    @FXML
    private TextField IdE;

    @FXML
    private TextField NameE;
    
    @FXML
    private TextField QuantityE;

    @FXML
    private TextField PriceE;

    @FXML
    private TextField filterField;

    
    private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;
	
	MembershipOffer MembershipOffer =null;
	public int index =-1;
	
    @FXML
    void getMembersScene(MouseEvent event) {

    }

    @FXML
    void getEquipmentScene(MouseEvent event) {

    }
    @FXML
    void getProgramScene(MouseEvent event) {

    }
    ObservableList<MembershipOffer> dataList;
    
    public ObservableList<MembershipOffer> loadData() {
    	connection = handler.getConnection();
    	String q1 = "SELECT * from membershipoffer";
    	ObservableList<MembershipOffer> list = FXCollections.observableArrayList();
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    list.add(new MembershipOffer(rs.getInt("membershipOfferId"),rs.getString("name"),rs.getInt("duration"),rs.getDouble("price")));		    
			  }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
    	
    	}
    
    
    public void addEquipment() {
    	connection = handler.getConnection();
		String q1 = "INSERT INTO MembershipOffer VALUES (?,?,?,?)";
		  try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.setString(2, NameE.getText());
			pst.setString(3, QuantityE.getText());
			pst.setString(4, PriceE.getText());
			pst.execute();
			test.setItems(loadData());
			search_Equipment();
			JOptionPane.showMessageDialog(null, "MembershipOffer added succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
   }
    @FXML
    void getSelectedData(MouseEvent event) {
    	
    	MembershipOffer = test.getSelectionModel().getSelectedItem();
    	IdE.setText(Integer.valueOf(MembershipOffer.getId()).toString());
    	NameE.setText(col_Name.getCellData(MembershipOffer).toString());
    	QuantityE.setText(Integer.valueOf(MembershipOffer.getDuration()).toString());
    	PriceE.setText(Double.valueOf(MembershipOffer.getPrice()).toString());
    }
    public void editEquipment() {
    	MembershipOffer = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String Value1= IdE.getText();
    	String Value2 = NameE.getText();    
    	String Value3 = QuantityE.getText(); 
    	String Value4 = PriceE.getText(); 
		String q1 = "UPDATE `Equipment` SET `membershipOfferId`= '"+Value1+"',`name`= '"+Value2+"' ,`duration`= '"+Value3+"' , `price`='"+Value4+"' WHERE membershipOfferId = '"+MembershipOffer.getId()+"'";
		try {
			pst=connection.prepareStatement(q1);
			pst.execute();
			JOptionPane.showMessageDialog(null, "MembershipOffer updated succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		test.setItems(loadData());
    }
    
    public void deleteEquipment(){
    	MembershipOffer = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String q1 = "DELETE FROM Equipment WHERE membershipOfferId = ?";
		try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.execute();
			JOptionPane.showMessageDialog(null, "membershipOfferId deleted succesfully");
			test.setItems(loadData());
			search_Equipment();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }
    
    
  
    @FXML
    void search_Equipment() {          
		col_Id.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("id"));
		col_Name.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("name"));
		col_Price.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("duration"));
		col_Quantity.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("price"));   
        dataList = loadData();
        test.setItems(dataList);
        FilteredList<MembershipOffer> filteredData = new FilteredList<>(dataList, b -> true);  
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        	filteredData.setPredicate(person -> {
        		if (newValue == null || newValue.isEmpty()) {
        			return true;
        		}    
    String lowerCaseFilter = newValue.toLowerCase();
    
    	if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
    		{
    			return true; // Filter matches Name
    		} 
    	else if (person.getId() != -1) 
    	{
    			return true; // Filter matches Description
    	}
    	else if (person.getDuration() != -1) 
    	{
    			return true; // Filter matches Description
    	}
    	else if (person.getPrice() != -1) 
    	{
    			return true; // Filter matches Description
    	}
         else  
          return false; // Does not match.
        });
        });  
        SortedList<MembershipOffer> sortedData = new SortedList<>(filteredData);  
        sortedData.comparatorProperty().bind(test.comparatorProperty());  
        test.setItems(sortedData);      
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
		col_Id.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("id"));
		col_Name.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("name"));
		col_Price.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("duration"));
		col_Quantity.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("price"));
		test.setItems(loadData());
		search_Equipment();
	}

}
