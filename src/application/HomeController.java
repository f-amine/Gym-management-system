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
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void getRevenueGraphData() {
 
    	String q1 = "SELECT MONTH(date) as month, SUM(amount) as total_amount\r\n"
    			+ "FROM payment\r\n"
    			+ "WHERE date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW()\r\n"
    			+ "GROUP BY MONTH(date)\r\n"
    			+ "ORDER BY MONTH(date) ASC";
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void getMemberGraphData() {
    	 
    	String q1 = "SELECT MONTH(date) as 'month', COUNT(*) as 'Number of Members' FROM payment WHERE date >= DATE_ADD(CURRENT_DATE, INTERVAL -1 YEAR) GROUP BY MONTH(date);";
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
		connection = handler.getConnection();
		getRevenueGraphData();
		getMemberGraphData();
		numberMembers.setText(String.valueOf(getNumerMembers()));
		numberStaff.setText(String.valueOf(getNumerStaff()));
		numberEquipments.setText(String.valueOf(getNumerEquipments()));
	}
    
}

