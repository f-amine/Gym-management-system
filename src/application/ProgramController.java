package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import appClasses.Program;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class ProgramController implements Initializable{

	
    @FXML
    private TableView<Program> test;

    @FXML
    private TableColumn<Program, String> col_description;

    @FXML
    private TableColumn<Program, String> col_name;   
    
    @FXML
    private TextField description;
    
    @FXML
    private TextField formName;
    
    @FXML
    private TextField programName;
    
    @FXML
    private TextField filterField;

    @FXML
    private TextField memberId;
    
    private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;
	
	Program program =null;
	
	int index =-1;
    @FXML
    void getMembersScene(MouseEvent event) {

    }

    @FXML
    void getProgramScene(MouseEvent event) {

    }
    ObservableList<Program> dataList;
    public ObservableList<Program> loadData() {
    	connection = handler.getConnection();
    	String q1 = "SELECT programName,description from program";
    	ObservableList<Program> list = FXCollections.observableArrayList();
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    list.add(new Program(rs.getString("programName"),rs.getString("description")));
			  }
			

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
    	
    	}
    
    @FXML
    void newLine(KeyEvent event) {
    	    if (event.isShiftDown() && event.getCode() == KeyCode.ENTER) {
    	    	description.insertText(description.getCaretPosition(), "\n");
    	    }
    };
    
    public void addProgram() {
    	connection = handler.getConnection();
		String q1 = "INSERT INTO PROGRAM (programName,description) VALUES (?,?)";
		  try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, formName.getText());
			pst.setString(2, description.getText());
			pst.execute();
			test.setItems(loadData());
			search_program();
			JOptionPane.showMessageDialog(null, "Program added succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
   }
    
    
    @FXML
    void getSelected(MouseEvent event) {
    	index = test.getSelectionModel().getSelectedIndex();
    	if (index <=-1) {
    		return;
    	}
    	description.setText(col_description.getCellData(index).toString());
    	formName.setText(col_name.getCellData(index).toString());
    }
    
    public void editPorgram() {
    	program = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String Value1= formName.getText();
    	String Value2 = description.getText();    	
		String q1 = "UPDATE `program` SET `programName`='"+Value1+"',`description`='"+Value2+"' WHERE programName = '"+program.getName()+"'";
		try {
			pst=connection.prepareStatement(q1);
			pst.execute();
			JOptionPane.showMessageDialog(null, "program updated succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		test.setItems(loadData());
    }
    
    public void deleteProgram(){
    	program = test.getSelectionModel().getSelectedItem();
    	connection = handler.getConnection();
    	String q1 = "DELETE FROM program WHERE programName = ?";
		try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, formName.getText());
			pst.execute();
			JOptionPane.showMessageDialog(null, "program deleted succesfully");
			test.setItems(loadData());
			search_program();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }
    
    
    public boolean checkMemberExist(){
    	connection = handler.getConnection();
    	String q1 = "SELECT memberid from member where memberid=?";
    	try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, memberId.getText());
			ResultSet rs = pst.executeQuery();
			int count=0;
			while (rs.next()) {
				count=count+1;
			}
			if (count==1) {
				return true;
			}
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    	
    }
    public boolean checkProgramExist() {
    	connection = handler.getConnection();
    	String q1 = "SELECT programName from program where programName=?";
    	try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, programName.getText());
			ResultSet rs = pst.executeQuery();
			int count=0;
			while (rs.next()) {
				count=count+1;
			}
			if (count==1) {
				return true;
			}
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }
    
    public void assignProgram() {
    	connection = handler.getConnection();
    	String q1 = "UPDATE member SET program= ? where memberid = ?";
    	try {
			if (checkMemberExist() && checkProgramExist()) {
				pst=connection.prepareStatement(q1);
				pst.setString(1, programName.getText());
				pst.setString(2, memberId.getText());
				pst.execute();
				JOptionPane.showMessageDialog(null, "program Assigned succesfully");
			}
			else {
				JOptionPane.showMessageDialog(null, "Member or program doesn't exist please enter a valid Member's ID and Program Name");
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void search_program() {          
    	col_name.setCellValueFactory(new PropertyValueFactory<Program, String>("Name"));
		col_description.setCellValueFactory(new PropertyValueFactory<Program, String>("description"));        
        dataList = loadData();
        test.setItems(dataList);
        FilteredList<Program> filteredData = new FilteredList<>(dataList, b -> true);  
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
    	else if (person.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) 
    	{
    			return true; // Filter matches Description
    	}                           
         else  
          return false; // Does not match.
        });
        });  
        SortedList<Program> sortedData = new SortedList<>(filteredData);  
        sortedData.comparatorProperty().bind(test.comparatorProperty());  
        test.setItems(sortedData);      
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
		col_name.setCellValueFactory(new PropertyValueFactory<Program, String>("Name"));
		col_description.setCellValueFactory(new PropertyValueFactory<Program, String>("description"));
		test.setItems(loadData());
		search_program();
	}

}

