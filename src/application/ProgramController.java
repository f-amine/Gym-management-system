package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import appClasses.Program;
import appClasses.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ProgramController implements Initializable{

	
    @FXML
    private TableView<Program> test;   
    @FXML
    private TableColumn<Program, String> col_description;

    @FXML
    private TableColumn<Program, String> col_name;   
    @FXML
    private Pane assistantsBtn;

    @FXML
    private Pane equipmentsBtn;

    @FXML
    private Pane memberBtn;   

    @FXML
    private Pane membershipBtn;

    @FXML
    private Pane programBtn;

    @FXML
    private Pane reportsBtn;

    @FXML
    private Pane trainorBtn;
    
    private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;
    @FXML
    void getMembersScene(MouseEvent event) {

    }

    @FXML
    void getProgramScene(MouseEvent event) {

    }
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
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
		col_name.setCellValueFactory(new PropertyValueFactory<Program, String>("Name"));
		col_description.setCellValueFactory(new PropertyValueFactory<Program, String>("description"));
		test.setItems(loadData());
	}

}

