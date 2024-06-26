package application;
		
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class User extends Application {
	@Override
	public void start(Stage window) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/view/Log_In.fxml"));
				Scene scene = new Scene(root,550,400);
				window.getIcons().add(new Image("/images/logo2.png"));
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
