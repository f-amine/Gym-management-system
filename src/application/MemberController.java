package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import java.sql.Date;
import appClasses.Member;
import appClasses.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MemberController implements Initializable{

    @FXML
    private TextField AddressE;

    @FXML
    private TextField ContactE;

    @FXML
    private TextField EmailE;

    @FXML
    private TextField FirstNameE;

    @FXML
    private TextField IdE;

    @FXML
    private TextField LastNameE;

    @FXML
    private DatePicker MembershipExpirationE;

    @FXML
    private ChoiceBox <String> MembershipTypeE;

    @FXML
    private TextField PaymentE;

    @FXML
    private TextField PhoneE;

    @FXML
    private ChoiceBox <String> ProgramE;

    @FXML
    private TableColumn<Member, String> col_Address;

    @FXML
    private TableColumn<Member, String> col_Contact;

    @FXML
    private TableColumn<Member, String> col_Email;

    @FXML
    private TableColumn<Member, String> col_FirstName;

    @FXML
    private TableColumn<Member, String> col_Id;

    @FXML
    private TableColumn<Member, String> col_LastName;

    @FXML
    private TableColumn<Member, String> col_Membership;

    @FXML
    private TableColumn<Member, String> col_MembershipEx;

    @FXML
    private TableColumn<Member, String> col_Payment;

    @FXML
    private TableColumn<Member, String> col_Phone;

    @FXML
    private TableColumn<Member, String> col_Program;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Member> test;

    private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;
	Member Member =null;
	public int index =-1;
    ObservableList<Member> dataList;
    
    
    public void choiceBoxMembershipFill() {
    	connection = handler.getConnection();
    	ObservableList<String> options = FXCollections.observableArrayList();
    	String q1 = "SELECT name from membershipoffer";
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    options.add(rs.getString("name"));
			  }
			MembershipTypeE.setItems(options);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

    	
    }
    public void choiceBoxProgramFill() {
    	connection = handler.getConnection();
    	ObservableList<String> options = FXCollections.observableArrayList();
    	String q1 = "SELECT programName from program";
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    options.add(rs.getString("ProgramName"));
			  }
			ProgramE.setItems(options);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

    	
    }
  

    public ObservableList<Member> loadData() {
    	connection = handler.getConnection();
    	String q1 = "SELECT * from member";
    	ObservableList<Member> list = FXCollections.observableArrayList();
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    list.add(new Member(rs.getInt("memberId"), rs.getString("membershiptype"),rs.getString("Program"),rs.getString("firstName")
			    		,rs.getString("lastName"),rs.getString("email"),rs.getString("phoneNumber"),rs.getString("address"),rs.getString("paymentInformation")
			    		,rs.getString("emergencyContactInfo"),rs.getDate("membershipExpiration")));		    
			    
			  }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
    	
    	}
    
    @FXML
    void addMember(MouseEvent event) {
    	connection = handler.getConnection();
		String q1 = "INSERT INTO member VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		  try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.setString(2, MembershipTypeE.getValue());
			pst.setString(3, ProgramE.getValue());
			pst.setString(4, FirstNameE.getText());
			pst.setString(5, LastNameE.getText());
			pst.setString(6, EmailE.getText());
			pst.setString(7, PhoneE.getText());
			pst.setString(8, AddressE.getText());
			pst.setString(9, PaymentE.getText());
			pst.setString(10, ContactE.getText());
			pst.setString(11, String.valueOf(MembershipExpirationE.getValue()));
			pst.execute();
			test.setItems(loadData());
			//search_Trainor();
			JOptionPane.showMessageDialog(null, "Member added succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }
    
    @FXML
    void deleteMember(MouseEvent event) {
    	Member = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String q1 = "DELETE FROM member WHERE memberId = ?";
		try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.execute();
			JOptionPane.showMessageDialog(null, "Member deleted succesfully");
			test.setItems(loadData());
			//search_Trainor();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }
    
    @FXML
    void editMember(MouseEvent event) {
    	Member = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
		String Value1 = IdE.getText();
		String Value2 = MembershipTypeE.getValue();
		String Value3 = ProgramE.getValue();
		String Value4 = FirstNameE.getText();
		String Value5 = LastNameE.getText();
		String Value6 = EmailE.getText();
		String Value7 = PhoneE.getText();
		String Value8 = AddressE.getText();
		String Value9 = PaymentE.getText();
		String Value10 = ContactE.getText();
		String Value11 = String.valueOf(MembershipExpirationE.getValue());
		String q1 = "UPDATE `member` SET `memberId`= '"+Value1+"',`membershiptype`= '"+Value2+"' ,`program`= '"+Value3+"' , `firstname`='"+Value4+"' , `lastname`='"+Value5+"', `email`='"+Value6+"' , `phoneNumber`='"+Value7+"' , `address` = '"+Value8+"' , `paymentInformation` = ' "+Value9+"' , `emergencyContactInfo` ='"+Value10+"' , `membershipExpiration` = '"+Value11+"'  WHERE memberId = '"+Member.getId()+"'";
		try {
			pst=connection.prepareStatement(q1);
			pst.execute();
			JOptionPane.showMessageDialog(null, "Member updated succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		test.setItems(loadData());
    }
    
    @FXML
    void getSelectedData(MouseEvent event) {
    	Member= test.getSelectionModel().getSelectedItem();
    	if (Member != null) {
    	IdE.setText(Integer.valueOf(Member.getId()).toString());
    	MembershipTypeE.setValue(col_Membership.getCellData(Member).toString());
    	ProgramE.setValue(col_Program.getCellData(Member).toString());
    	FirstNameE.setText(col_FirstName.getCellData(Member).toString());
    	LastNameE.setText(col_LastName.getCellData(Member).toString());
    	EmailE.setText(col_Email.getCellData(Member).toString());
    	PhoneE.setText(col_Phone.getCellData(Member).toString());
    	AddressE.setText(col_Address.getCellData(Member).toString());
    	PaymentE.setText(col_Payment.getCellData(Member).toString());
    	ContactE.setText(col_Contact.getCellData(Member).toString());
    	Date date = Member.getMembershipExpiration();
    	LocalDate localDate = date.toLocalDate();
    	MembershipExpirationE.setValue(localDate);
    	}
    }
    @FXML
    void search_Trainor() {          
		col_Id.setCellValueFactory(new PropertyValueFactory<Member, String>("id"));
		col_Membership.setCellValueFactory(new PropertyValueFactory<Member, String>("membershipoffer"));
		col_Program.setCellValueFactory(new PropertyValueFactory<Member, String>("ProgramName"));
		col_FirstName.setCellValueFactory(new PropertyValueFactory<Member, String>("firstName"));
		col_LastName.setCellValueFactory(new PropertyValueFactory<Member, String>("lastName"));
		col_Email.setCellValueFactory(new PropertyValueFactory<Member, String>("email"));
		col_Phone.setCellValueFactory(new PropertyValueFactory<Member, String>("PhoneNumber"));
		col_Address.setCellValueFactory(new PropertyValueFactory<Member, String>("address"));
		col_Payment.setCellValueFactory(new PropertyValueFactory<Member, String>("paymentInformation"));
		col_Contact.setCellValueFactory(new PropertyValueFactory<Member, String>("emergencyContactInfo"));
		col_MembershipEx.setCellValueFactory(new PropertyValueFactory<Member, String>("membershipExpiration"));
        dataList = loadData();
        test.setItems(dataList);
        FilteredList<Member> filteredData = new FilteredList<>(dataList, b -> true);  
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
    	else if (person.getMembershipoffer().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
		{
			return true; 
		} 
    	else if (person.getProgramName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
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
    	else if (person.getEmergencyContactInfo().toLowerCase().indexOf(lowerCaseFilter) != -1 ) 
		{
			return true; 
		} 
    	else if (person.getMembershipExpiration() != null ) 
		{
			return true; 
		}
        else  
          return false; 
        });
        });  
        SortedList<Member> sortedData = new SortedList<>(filteredData);  
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
		col_Id.setCellValueFactory(new PropertyValueFactory<Member, String>("id"));
		col_Membership.setCellValueFactory(new PropertyValueFactory<Member, String>("membershipoffer"));
		col_Program.setCellValueFactory(new PropertyValueFactory<Member, String>("ProgramName"));
		col_FirstName.setCellValueFactory(new PropertyValueFactory<Member, String>("firstName"));
		col_LastName.setCellValueFactory(new PropertyValueFactory<Member, String>("lastName"));
		col_Email.setCellValueFactory(new PropertyValueFactory<Member, String>("email"));
		col_Phone.setCellValueFactory(new PropertyValueFactory<Member, String>("PhoneNumber"));
		col_Address.setCellValueFactory(new PropertyValueFactory<Member, String>("address"));
		col_Payment.setCellValueFactory(new PropertyValueFactory<Member, String>("paymentInformation"));
		col_Contact.setCellValueFactory(new PropertyValueFactory<Member, String>("emergencyContactInfo"));
		col_MembershipEx.setCellValueFactory(new PropertyValueFactory<Member, String>("membershipExpiration"));
		test.setItems(loadData());
		choiceBoxMembershipFill();
		choiceBoxProgramFill();
	}

}
