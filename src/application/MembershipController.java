package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import appClasses.MembershipOffer;
import appClasses.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    private TableColumn<MembershipOffer, String> col_Duration;

    
    
    @FXML
    private TextField IdE;

    @FXML
    private TextField NameE;
    
    @FXML
    private TextField DurationE;

    @FXML
    private TextField PriceE;

    @FXML
    private TextField filterField;

    
    private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;
	
	MembershipOffer MembershipOffer =null;
	public int index =-1;
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
			pst.setString(3, DurationE.getText());
			pst.setString(4, PriceE.getText());
			pst.execute();
			test.setItems(loadData());
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
    	DurationE.setText(Integer.valueOf(MembershipOffer.getDuration()).toString());
    	PriceE.setText(Double.valueOf(MembershipOffer.getPrice()).toString());
    }
    public void editEquipment() {
    	MembershipOffer = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String Value1= IdE.getText();
    	String Value2 = NameE.getText();    
    	String Value3 = DurationE.getText(); 
    	String Value4 = PriceE.getText(); 
		String q1 = "UPDATE `membershipoffer` SET `membershipOfferId`= '"+Value1+"',`name`= '"+Value2+"' ,`duration`= '"+Value3+"' , `price`='"+Value4+"' WHERE membershipOfferId = '"+MembershipOffer.getId()+"'";
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
    	String q1 = "DELETE FROM membershipoffer WHERE membershipOfferId = ?";
		try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.execute();
			JOptionPane.showMessageDialog(null, "membershipOfferId deleted succesfully");
			test.setItems(loadData());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }
    
    
  
    @FXML
    void  Search(KeyEvent event) {
    try {
    	connection = handler.getConnection();
    	String q1 = "SELECT * FROM membershipoffer where (membershipOfferId like '$"+filterField.getText()+"%' or name like '%"+filterField.getText()+"%'  or duration like '%"+filterField.getText()+"%' or price like '%"+filterField.getText()+"%') ";
    	dataList=FXCollections.observableArrayList();
    	pst= connection.prepareStatement(q1);
   
    ResultSet rs = pst.executeQuery();
    while(rs.next())
    {
    	dataList.add(new MembershipOffer(rs.getInt("membershipOfferId"),rs.getString("name"),rs.getInt("duration"),rs.getDouble("price")));	
    }
    }catch(Exception e)
    {
    e.printStackTrace();
    }
   
	col_Id.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("id"));
	col_Name.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("name"));
	col_Duration.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("duration"));
	col_Price.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("price"));
	
    test.setItems(dataList);
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
		col_Id.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("id"));
		col_Name.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("name"));
		col_Duration.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("duration"));
		col_Price.setCellValueFactory(new PropertyValueFactory<MembershipOffer, String>("price"));
		test.setItems(loadData());
	}

}
