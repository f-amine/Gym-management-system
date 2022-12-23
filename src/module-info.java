module boba {
	requires javafx.controls;
	requires javafx.fxml;
	requires mysql.connector.java;
	requires java.sql;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
