module boba {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires com.jfoenix;
	requires javafx.base;
	
	opens appClasses to javafx.base;

	opens application to javafx.graphics, javafx.fxml;
}
