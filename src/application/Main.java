package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;



public class Main extends Application {
	public static Stage stg;
	@Override
	public void start(Stage primaryStage) {
		try {	
			stg = primaryStage;
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/UserInterface/Sample.fxml"));
			Scene scene = new Scene(root,1366,768);
			scene.getStylesheets().add(getClass().getResource("/UserInterface/application.css").toExternalForm());
			primaryStage.setTitle("GYM MANAGEMENT SYSTEM");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stage getStg() {
		return stg;
	}

	public static void setStg(Stage stg) {
		Main.stg = stg;
	}

	public void changeSceen(String fxml) throws IOException{
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	public void changeMemberSceen(String fxml) throws IOException{
		Parent pane = FXMLLoader.load(getClass().getResource(fxml));
		stg.getScene().setRoot(pane);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
