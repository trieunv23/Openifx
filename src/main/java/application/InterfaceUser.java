package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 */
public class InterfaceUser extends Application{

	@Override
	public void start(Stage window) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/InterfaceUser.fxml"));
			Scene scene = new Scene(root,1000,650);
			window.setScene(scene);
			window.setTitle("OPENIFX");
			
			window.centerOnScreen();
			
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

