package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;

import appClasses.Program;
import appClasses.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ProgramController implements Initializable{

    @FXML
    private Pane assistantsBtn;

    @FXML
    private Pane equipmentsBtn;

    @FXML
    private Pane memberBtn;
    
    @FXML
    private JFXTreeTableView<?> treeTableView;
    
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
    public void loadData() {
    	connection = handler.getConnection();
    	String q1 = "SELECT programName,description from program";
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			TreeItem<Program> root = new TreeItem<>();
			while (rs.next()) {
			    Program dataObject = new Program(rs.getString("programName"),rs.getString("description"));
			    TreeItem<Program> treeItem = new TreeItem<>(dataObject);
			    root.getChildren().add(treeItem);
			  }
			treeTableView.setRoot(root);

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    	
    	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
		
	}

}

