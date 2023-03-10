package application;


import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import appClasses.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class SampleController implements Initializable {
	private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;

    @FXML
    private PasswordField password;

    @FXML
    private Button signIn;

    @FXML
    private TextField username;
    public 
    @FXML
    void login(ActionEvent event) {
    	//retrieve date from database
    	connection = handler.getConnection();
    	String q1 = "SELECT username,md5(password) from employee where username=? and password=?";
    	try {
    		
			pst=connection.prepareStatement(q1);
			pst.setString(1, username.getText());
			pst.setString(2, password.getText());
			ResultSet rs = pst.executeQuery();
			int count=0;
			while (rs.next()) {
				count=count+1;
			}
			if (count==1) {
				Main m = new Main();
				try {
					m.changeSceen("/UserInterface/Home.fxml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Username or password incorrect");
				alert.show();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
	}

}