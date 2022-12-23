module boba {
	requires javafx.controls;
	requires javafx.fxml;
	requires mysql.connector.java;
	requires java.sql;
	requires javafx.graphics;
	requires com.jfoenix;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
