package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Admin extends Application {
	
	@Override
	public void start(Stage window) {
		try {
			Image iconImage = new Image(getClass().getResourceAsStream("/images/adminIcon.png"));
			window.getIcons().add(iconImage);
			Parent root = FXMLLoader.load(getClass().getResource("/view/Admin.fxml"));
			Scene scene = new Scene(root,1100,670);
			window.setScene(scene);	
			window.setTitle("Manager User (Admin)");
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
