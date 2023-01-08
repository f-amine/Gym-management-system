package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import appClasses.Receptionist;
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

public class ReceptionistController implements Initializable{

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
    private TableView<Receptionist> test;
    
    @FXML
    private TableColumn<Receptionist, String> col_Address;

    @FXML
    private TableColumn<Receptionist, String> col_Email;

    @FXML
    private TableColumn<Receptionist, String> col_FirstName;

    @FXML
    private TableColumn<Receptionist, String> col_Id;

    @FXML
    private TableColumn<Receptionist, String> col_LastName;

    @FXML
    private TableColumn<Receptionist, String> col_Payment;

    @FXML
    private TableColumn<Receptionist, String> col_Phone;

    @FXML
    private TextField filterField;

    private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;
	Receptionist Receptionist =null;
	public int index =-1;
    ObservableList<Receptionist> dataList;
	
    public ObservableList<Receptionist> loadData() {
    	connection = handler.getConnection();
    	String q1 = "SELECT employeeId,firstName,lastName,email,phoneNumber,address,paymentInformation from employee where role = 'receptionist'";
    	ObservableList<Receptionist> list = FXCollections.observableArrayList();
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    list.add(new Receptionist(rs.getInt("employeeId"),rs.getString("firstName"),rs.getString("LastName"),rs.getString("email")
			    		,rs.getString("phoneNumber"),rs.getString("address"),rs.getString("paymentInformation")));		    
			    
			  }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
    	
    	}
    
    @FXML
    void addReceptionist(MouseEvent event) {
    	connection = handler.getConnection();
		String q1 = "INSERT INTO employee VALUES (?,'receptionist',md5('receptionist'),?,?,?,?,?,'receptionist',?)";
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
			search_Receptionist();
			JOptionPane.showMessageDialog(null, "Receptionist added succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }

    @FXML
    void deleteReceptionist(MouseEvent event) {
    	Receptionist = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String q1 = "DELETE FROM employee WHERE employeeId = ?";
		try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.execute();
			JOptionPane.showMessageDialog(null, "Receptionist deleted succesfully");
			test.setItems(loadData());
			search_Receptionist();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }

    @FXML
    void editReceptionist(MouseEvent event) {
    	Receptionist = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String Value1= IdE.getText();
    	String Value2 = FirstNameE.getText();    
    	String Value3 = LastNameE.getText(); 
    	String Value4 = EmailE.getText();
    	String Value5 = PhoneE.getText(); 
    	String Value6 = AddressE.getText(); 
    	String Value7 = PaymentE.getText(); 
		String q1 = "UPDATE `employee` SET `employeeId`= '"+Value1+"',`firstName`= '"+Value2+"' ,`lastName`= '"+Value3+"' , `email`='"+Value4+"' , `phoneNumber`='"+Value5+"', `address`='"+Value6+"' , `paymentInformation`='"+Value7+"'  WHERE employeeId = '"+Receptionist.getId()+"'";
		try {
			pst=connection.prepareStatement(q1);
			pst.execute();
			JOptionPane.showMessageDialog(null, "Receptionist updated succesfully");
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
    	Receptionist = test.getSelectionModel().getSelectedItem();
    	IdE.setText(Integer.valueOf(Receptionist.getId()).toString());
    	FirstNameE.setText(col_FirstName.getCellData(index).toString());
    	LastNameE.setText(col_LastName.getCellData(index).toString());
    	EmailE.setText(col_Email.getCellData(index).toString());
    	PhoneE.setText(col_Phone.getCellData(index).toString());
    	AddressE.setText(col_Address.getCellData(index).toString());
    	PaymentE.setText(col_Payment.getCellData(index).toString());
    }
    
    @FXML
    void search_Receptionist() {          
		col_Id.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("id"));
		col_FirstName.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("firstName"));
		col_LastName.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("lastName"));
		col_Email.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("email"));
		col_Phone.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("phoneNumber"));
		col_Address.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("address"));
		col_Payment.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("paymentInformation"));
        dataList = loadData();
        test.setItems(dataList);
        FilteredList<Receptionist> filteredData = new FilteredList<>(dataList, b -> true);  
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
        SortedList<Receptionist> sortedData = new SortedList<>(filteredData);  
        sortedData.comparatorProperty().bind(test.comparatorProperty());  
        test.setItems(sortedData);      
    }
    
    public void getHomeScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Home.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

    public void getMembersScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Member.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public void getReceptionistScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Receptionist.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public void getTrainorScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Trainor.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    
    public void getMembershipOfferScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Membership.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    
    public void getEquipmentsScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/equipments.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    

    public void getProgramScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Program.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    

    public void getPaymentScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Payment.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
		col_Id.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("id"));
		col_FirstName.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("firstName"));
		col_LastName.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("lastName"));
		col_Email.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("email"));
		col_Phone.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("phoneNumber"));
		col_Address.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("address"));
		col_Payment.setCellValueFactory(new PropertyValueFactory<Receptionist, String>("paymentInformation"));
		test.setItems(loadData());
		search_Receptionist();
	}

}