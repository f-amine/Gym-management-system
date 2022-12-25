package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import appClasses.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeController implements Initializable {
	
	private Connection connection;
	private dbConnection handler;
	
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
	 
    @FXML
    private LineChart<?, ?> membersGraph;

    @FXML
    private LineChart<?, ?> revenueGraph;
    
    @FXML
    private Text numberEquipments;

    @FXML
    private Text numberMembers;

    @FXML
    private Text numberStaff;
    
    
// ______________________________________________________________________________________________________________________________________
    public void getRevenueGraphData() {
 
    	String q1 = "SELECT MONTH(date) AS month, SUM(amount) AS total_amount FROM payment WHERE YEAR(date) = YEAR(CURDATE()) GROUP BY MONTH(date)";
		try {
			Statement st=connection.createStatement();
			ResultSet rs = st.executeQuery(q1);
			XYChart.Series series = new XYChart.Series();
			while (rs.next()) {
				   String x = rs.getString("month");
				   double y = rs.getDouble("total_amount");
				   series.getData().add(new XYChart.Data(x, y));
				 }
			revenueGraph.getData().addAll(series);
			series.getNode().setStyle("-fx-stroke: red");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}


    }
    //count the number of members whose membership expires in each month of the current year
    public void getMemberGraphData() {
    	 
    	String q1 = "SELECT COUNT(*) AS 'Number of Members', "
    			+ "MONTHNAME(membershipExpiration) AS 'Month' FROM Member "
    			+ "WHERE MONTH(membershipExpiration) "
    			+ "BETWEEN MONTH(DATE_ADD(CURRENT_DATE, INTERVAL -1 MONTH)) AND MONTH(DATE_ADD(CURRENT_DATE, INTERVAL -12 MONTH))"
    			+ " GROUP BY MONTH(membershipExpiration)";
		try {
			Statement st=connection.createStatement();
			ResultSet rs = st.executeQuery(q1);
			XYChart.Series series = new XYChart.Series();
			while (rs.next()) {
				   String x = rs.getString("Month");
				   double y = rs.getDouble("Number of Members");
				   series.getData().add(new XYChart.Data(x, y));
				 }
			membersGraph.getData().addAll(series);
			series.getNode().setStyle("-fx-stroke: red");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    public int getNumerMembers() {
    	int count =0;
    	String q1 = "SELECT COUNT(*) FROM member WHERE membershipExpiration >= CURDATE()";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(q1);
			if (rs.next()) {
			      count = rs.getInt(1);
			    }
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return count;
    }
    //returns the number of staff in the gym
    public int getNumerStaff() {
    	int count =0;
    	String q1 = "SELECT COUNT(*) FROM employee";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(q1);
			if (rs.next()) {
			      count = rs.getInt(1);
			    }
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return count;
    }
    //Return number of equipments in the gym
    public int getNumerEquipments() {
    	int count =0;
    	String q1 = "SELECT COUNT(*) FROM equipment";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(q1);
			if (rs.next()) {
			      count = rs.getInt(1);
			    }
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return count;
    }
  //Change scene to members Page
    public void getMembersScene() {
		memberBtn.getScene().getWindow().hide();
		Stage member = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			member.setScene(scene);
			member.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    //Change scene to program Page
    public void getProgramScene() {
		memberBtn.getScene().getWindow().hide();
		Stage program = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Program.fxml"));
			Scene scene = new Scene(root);
			program.setScene(scene);
			program.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
		connection = handler.getConnection();
		getRevenueGraphData();
		getMemberGraphData();
		numberMembers.setText(String.valueOf(getNumerMembers()));
		numberStaff.setText(String.valueOf(getNumerStaff()));
		numberEquipments.setText(String.valueOf(getNumerEquipments()));
	}
    
}

