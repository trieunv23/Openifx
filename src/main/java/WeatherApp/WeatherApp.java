package WeatherApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WeatherApp extends Application{
	
		@Override
		public void start(Stage window) throws Exception {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/view/Weather.fxml"));
				Scene scene = new Scene(root,652,385);
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
