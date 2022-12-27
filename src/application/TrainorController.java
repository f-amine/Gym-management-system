package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import appClasses.MembershipOffer;
import appClasses.Trainor;
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

public class TrainorController implements Initializable{

    @FXML
    private TextField AddressE;

    @FXML
    private TextField EmailE;

    @FXML
    private TextField FirstNameE;

    @FXML
    private TextField IdE;

    @FXML
    private TextField LastNameE;

    @FXML
    private TextField PaymentE;

    @FXML
    private TextField PhoneE;
    
    @FXML
    private TableView<Trainor> test;
    
    @FXML
    private TableColumn<Trainor, String> col_Address;

    @FXML
    private TableColumn<Trainor, String> col_Email;

    @FXML
    private TableColumn<Trainor, String> col_FirstName;

    @FXML
    private TableColumn<Trainor, String> col_Id;

    @FXML
    private TableColumn<Trainor, String> col_LastName;

    @FXML
    private TableColumn<Trainor, String> col_Payment;

    @FXML
    private TableColumn<Trainor, String> col_Phone;

    @FXML
    private TextField filterField;

    private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;
	Trainor Trainor =null;
	public int index =-1;
    ObservableList<Trainor> dataList;
	
    public ObservableList<Trainor> loadData() {
    	connection = handler.getConnection();
    	String q1 = "SELECT employeeId,firstName,lastName,email,phoneNumber,address,paymentInformation from employee where role = 'trainor'";
    	ObservableList<Trainor> list = FXCollections.observableArrayList();
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    list.add(new Trainor(rs.getInt("employeeId"),rs.getString("firstName"),rs.getString("LastName"),rs.getString("email")
			    		,rs.getString("phoneNumber"),rs.getString("address"),rs.getString("paymentInformation")));		    
			    
			  }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
    	
    	}
    
    @FXML
    void addTrainor(MouseEvent event) {
    	connection = handler.getConnection();
		String q1 = "INSERT INTO employee VALUES (?,'trainor','trainor',?,?,?,?,?,'trainor',?)";
		  try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.setString(2, FirstNameE.getText());
			pst.setString(3, LastNameE.getText());
			pst.setString(4, EmailE.getText());
			pst.setString(5, PhoneE.getText());
			pst.setString(6, AddressE.getText());
			pst.setString(7, PaymentE.getText());
			pst.execute();
			test.setItems(loadData());
			search_Trainor();
			JOptionPane.showMessageDialog(null, "Trainor added succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }

    @FXML
    void deleteTrainor(MouseEvent event) {
    	Trainor = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String q1 = "DELETE FROM employee WHERE employeeId = ?";
		try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.execute();
			JOptionPane.showMessageDialog(null, " deleted succesfully");
			test.setItems(loadData());
			search_Trainor();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }

    @FXML
    void editTrainor(MouseEvent event) {
    	Trainor = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String Value1= IdE.getText();
    	String Value2 = FirstNameE.getText();    
    	String Value3 = LastNameE.getText(); 
    	String Value4 = EmailE.getText();
    	String Value5 = PhoneE.getText(); 
    	String Value6 = AddressE.getText(); 
    	String Value7 = PaymentE.getText(); 
		String q1 = "UPDATE `employee` SET `employeeId`= '"+Value1+"',`firstName`= '"+Value2+"' ,`lastName`= '"+Value3+"' , `email`='"+Value4+"' , `phoneNumber`='"+Value5+"', `address`='"+Value6+"' , `paymentInformation`='"+Value7+"'  WHERE employeeId = '"+Trainor.getId()+"'";
		try {
			pst=connection.prepareStatement(q1);
			pst.execute();
			JOptionPane.showMessageDialog(null, "Trainor updated succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		test.setItems(loadData());
    }
    
    @FXML
    void getSelectedData(MouseEvent event) {
    	index = test.getSelectionModel().getSelectedIndex();
    	if (index <=-1) {
    		return;
    	}
    	Trainor = test.getSelectionModel().getSelectedItem();
    	IdE.setText(Integer.valueOf(Trainor.getId()).toString());
    	FirstNameE.setText(col_FirstName.getCellData(index).toString());
    	LastNameE.setText(col_LastName.getCellData(index).toString());
    	EmailE.setText(col_Email.getCellData(index).toString());
    	PhoneE.setText(col_Phone.getCellData(index).toString());
    	AddressE.setText(col_Address.getCellData(index).toString());
    	PaymentE.setText(col_Payment.getCellData(index).toString());
    }
    
    @FXML
    void search_Trainor() {          
		col_Id.setCellValueFactory(new PropertyValueFactory<Trainor, String>("id"));
		col_FirstName.setCellValueFactory(new PropertyValueFactory<Trainor, String>("firstName"));
		col_LastName.setCellValueFactory(new PropertyValueFactory<Trainor, String>("lastName"));
		col_Email.setCellValueFactory(new PropertyValueFactory<Trainor, String>("email"));
		col_Phone.setCellValueFactory(new PropertyValueFactory<Trainor, String>("phoneNumber"));
		col_Address.setCellValueFactory(new PropertyValueFactory<Trainor, String>("address"));
		col_Payment.setCellValueFactory(new PropertyValueFactory<Trainor, String>("paymentInformation"));
        dataList = loadData();
        test.setItems(dataList);
        FilteredList<Trainor> filteredData = new FilteredList<>(dataList, b -> true);  
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        	filteredData.setPredicate(person -> {
        		if (newValue == null || newValue.isEmpty()) {
        			return true;
        		}    
    String lowerCaseFilter = newValue.toLowerCase();
   
    	if (person.getId() != -1) 
    	{
    		return true;
    	}
    	else if (person.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
		{
			return true; 
		} 
    	else if (person.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
		{
			return true; 
		} 
    	else if (person.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
		{
			return true;
		}
    	else if (person.getPhoneNumber().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
		{
			return true; 
		} 
    	else if (person.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
		{
			return true; 
		} 
    	else if (person.getPaymentInformation().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
		{
			return true; 
		} 
        else  
          return false; 
        });
        });  
        SortedList<Trainor> sortedData = new SortedList<>(filteredData);  
        sortedData.comparatorProperty().bind(test.comparatorProperty());  
        test.setItems(sortedData);      
    }
    
    @FXML
    void getMembersScene(MouseEvent event) {

    }

    @FXML
    void getProgramScene(MouseEvent event) {

    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
		col_Id.setCellValueFactory(new PropertyValueFactory<Trainor, String>("id"));
		col_FirstName.setCellValueFactory(new PropertyValueFactory<Trainor, String>("firstName"));
		col_LastName.setCellValueFactory(new PropertyValueFactory<Trainor, String>("lastName"));
		col_Email.setCellValueFactory(new PropertyValueFactory<Trainor, String>("email"));
		col_Phone.setCellValueFactory(new PropertyValueFactory<Trainor, String>("phoneNumber"));
		col_Address.setCellValueFactory(new PropertyValueFactory<Trainor, String>("address"));
		col_Payment.setCellValueFactory(new PropertyValueFactory<Trainor, String>("paymentInformation"));
		test.setItems(loadData());
		search_Trainor();
	}

}