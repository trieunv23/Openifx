package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Calculator extends Application{

	public void start(Stage window) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Calculator.fxml"));
			Scene scene = new Scene(root,780,365);
			window.setScene(scene);	
			window.setTitle("OPENIFX");
			window.centerOnScreen();
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}	

public static void main(String[] args) {
	launch(args);
}
	
}
