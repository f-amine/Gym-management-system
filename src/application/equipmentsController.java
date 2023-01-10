package application;

	import java.io.IOException;
import java.net.URL;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ResourceBundle;

	import javax.swing.JOptionPane;


	import appClasses.Equipment;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
	
	
public class equipmentsController implements Initializable {



		
	    @FXML
	    private TableView<Equipment> test;

	    @FXML
	    private TableColumn<Equipment, String> col_Id;

	    @FXML
	    private TableColumn<Equipment, String> col_Name;

	    @FXML
	    private TableColumn<Equipment, String> col_Price;

	    @FXML
	    private TableColumn<Equipment, String> col_Quantity;

	    
	    
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
		
		Equipment Equipment =null;
		public int index =-1;
		
	    ObservableList<Equipment> dataList;
	    
	    public ObservableList<Equipment> loadData() {
	    	connection = handler.getConnection();
	    	String q1 = "SELECT * from Equipment";
	    	ObservableList<Equipment> list = FXCollections.observableArrayList();
	    	try {
				pst=connection.prepareStatement(q1);
				ResultSet rs = pst.executeQuery();
				while (rs.next()) {
				    list.add(new Equipment(rs.getInt("equipmentId"),rs.getString("name"),rs.getInt("quantity"),rs.getDouble("pricePerUnit")));		    
				  }
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return list;
	    	
	    	}
	    
	    
	    public void addEquipment() {
	    	connection = handler.getConnection();
			String q1 = "INSERT INTO Equipment VALUES (?,?,?,?)";
			  try {
				pst=connection.prepareStatement(q1);
				pst.setString(1, IdE.getText());
				pst.setString(2, NameE.getText());
				pst.setString(3, QuantityE.getText());
				pst.setString(4, PriceE.getText());
				pst.execute();
				test.setItems(loadData());
				search_Equipment();
				JOptionPane.showMessageDialog(null, "Equipment added succesfully");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}
	   }
	    @FXML
	    void getSelectedData(MouseEvent event) {
	    	
	    	Equipment = test.getSelectionModel().getSelectedItem();
	    	IdE.setText(Integer.valueOf(Equipment.getEquipementId()).toString());
	    	NameE.setText(col_Name.getCellData(Equipment).toString());
	    	PriceE.setText(Double.valueOf(Equipment.getPricePerUnit()).toString());
	    	QuantityE.setText(Integer.valueOf(Equipment.getQuantity()).toString());
	    }
	    public void editEquipment() {
	    	Equipment = test.getSelectionModel().getSelectedItem();
	    	connection = handler.getConnection();
	    	String Value1= IdE.getText();
	    	String Value2 = NameE.getText();    
	    	String Value3 = QuantityE.getText(); 
	    	String Value4 = PriceE.getText(); 
			String q1 = "UPDATE `Equipment` SET `equipmentId`= '"+Value1+"',`name`= '"+Value2+"' ,`quantity`= '"+Value3+"' , `pricePerUnit`='"+Value4+"' WHERE equipmentID = '"+Equipment.getEquipementId()+"'";
			try {
				pst=connection.prepareStatement(q1);
				pst.execute();
				JOptionPane.showMessageDialog(null, "Equipment updated succesfully");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}
			test.setItems(loadData());
	    }
	    
	    public void deleteEquipment(){
	    	Equipment = test.getSelectionModel().getSelectedItem();
	    	connection = handler.getConnection();
	    	String q1 = "DELETE FROM Equipment WHERE equipmentId = ?";
			try {
				pst=connection.prepareStatement(q1);
				pst.setString(1, IdE.getText());
				pst.execute();
				JOptionPane.showMessageDialog(null, "Equipment deleted succesfully");
				test.setItems(loadData());
				search_Equipment();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}
	    }
	    
	    
	  
	    @FXML
	    void search_Equipment() {          
			col_Id.setCellValueFactory(new PropertyValueFactory<Equipment, String>("equipementId"));
			col_Name.setCellValueFactory(new PropertyValueFactory<Equipment, String>("name"));
			col_Price.setCellValueFactory(new PropertyValueFactory<Equipment, String>("pricePerUnit"));
			col_Quantity.setCellValueFactory(new PropertyValueFactory<Equipment, String>("quantity"));   
	        dataList = loadData();
	        test.setItems(dataList);
	        FilteredList<Equipment> filteredData = new FilteredList<>(dataList, b -> true);  
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
	    	else if (person.getEquipementId() != -1) 
	    	{
	    			return true; // Filter matches Description
	    	}
	    	else if (person.getPricePerUnit() != -1) 
	    	{
	    			return true; // Filter matches Description
	    	}
	    	else if (person.getQuantity() != -1) 
	    	{
	    			return true; // Filter matches Description
	    	}
	         else  
	          return false; // Does not match.
	        });
	        });  
	        SortedList<Equipment> sortedData = new SortedList<>(filteredData);  
	        sortedData.comparatorProperty().bind(test.comparatorProperty());  
	        test.setItems(sortedData);      
	    }
	    @FXML
	    void  Search(KeyEvent event) {
	    try {
	    	connection = handler.getConnection();
	    	String q1 = "SELECT * FROM equipment where equipmentId like '%"+filterField.getText()+"%' or name like '%"+filterField.getText()+"%'  or quantity like '%"+filterField.getText()+ "%' or pricePerUnit like '%"+filterField.getText()+"%'";
	    	dataList=FXCollections.observableArrayList();
	    	pst= connection.prepareStatement(q1);
	   
	    ResultSet rs = pst.executeQuery();
	    while(rs.next())
	    {
	    	dataList.add(new Equipment(rs.getInt("equipmentId"),rs.getString("name"),rs.getInt("quantity"),rs.getDouble("pricePerUnit")));
	    }
	    }catch(Exception e)
	    {
	    e.printStackTrace();
	    }
		col_Id.setCellValueFactory(new PropertyValueFactory<Equipment, String>("equipementId"));
		col_Name.setCellValueFactory(new PropertyValueFactory<Equipment, String>("name"));
		col_Price.setCellValueFactory(new PropertyValueFactory<Equipment, String>("pricePerUnit"));
		col_Quantity.setCellValueFactory(new PropertyValueFactory<Equipment, String>("quantity"));   
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
			col_Id.setCellValueFactory(new PropertyValueFactory<Equipment, String>("equipementId"));
			col_Name.setCellValueFactory(new PropertyValueFactory<Equipment, String>("name"));
			col_Price.setCellValueFactory(new PropertyValueFactory<Equipment, String>("pricePerUnit"));
			col_Quantity.setCellValueFactory(new PropertyValueFactory<Equipment, String>("quantity"));
			test.setItems(loadData());
			search_Equipment();
		}

	}
