package appClasses;

import java.sql.*;

public class dbConnection {
	  final String DB_URL ="jdbc:mysql://localhost:3306/gymdb";
	  final String DB_USERNAME = "root";
	  final String DB_PASSWORD = "";
	  
	  Connection dbconnection;
	  public Connection getConnection() {
			  try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				dbconnection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		  return dbconnection;
	  }

	
	
}
