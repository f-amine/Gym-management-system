package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import appClasses.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

public class HomeController implements Initializable {
	
	private Connection connection;
	private dbConnection handler;

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
    	 
    	String q1 = "SELECT COUNT(*) as 'Number of New Members', MONTH(membershipExpiration) as 'Month' FROM Member WHERE YEAR(membershipExpiration) = YEAR(CURDATE()) GROUP BY MONTH(membershipExpiration)";
		try {
			Statement st=connection.createStatement();
			ResultSet rs = st.executeQuery(q1);
			XYChart.Series series = new XYChart.Series();
			while (rs.next()) {
				   String x = rs.getString("Month");
				   double y = rs.getDouble("Number of New Members");
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
