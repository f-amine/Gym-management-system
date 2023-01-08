module boba {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires com.jfoenix;
	requires javafx.base;
	requires java.desktop;
	requires org.apache.pdfbox;
	requires itextpdf;
	
	opens appClasses to javafx.base;
	opens application to javafx.graphics, javafx.fxml;
}
