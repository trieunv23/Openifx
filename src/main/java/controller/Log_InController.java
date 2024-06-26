package controller;

import Exception.SQLException1;
import dao.UserDao;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import Entity.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Log_InController implements Initializable{
	
	@FXML 
	private Button regester ; 
	
	@FXML 
	private TextField userName ;
	
	@FXML
	private PasswordField password ; 
	
	@FXML
	private Label notification ; 

	@FXML
	private Label hello ; 
	
	@FXML
	private Label today ;
	
	@FXML
	private TextField passwordshow ;
	
	@FXML
	private ImageView imagevieweye ; 
	
	private String urlimageeyeclose = "/images/closeeye.png";
	private String urlimageopen = "/images/openeye.png";
	
	@FXML
	private Button btshow ; 
	
	private boolean checkShow = false ; 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btshow.setVisible(false);
		passwordshow.setVisible(false);
		passwordshow.setDisable(false);
		
		Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
    	this.imagevieweye.setImage(imagePart);
		
		displayTime();
		
		userName.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				notification.setText("");
				
			}
			
		});
		password.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				notification.setText("");
				
				if (!password.getText().isEmpty()) {
					btshow.setVisible(true);
				} else if (password.getText().isEmpty()) {
					btshow.setVisible(false);
				}
				
				String passwordValue = password.getText() ; 
				passwordshow.setText(passwordValue);
				
			}
			
		});
		
		passwordshow.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				notification.setText("");
				
				String passwordValue = passwordshow.getText() ; 
				password.setText(passwordValue);
				
			}
		});
		
	}	
	
	@FXML
	public void regester(ActionEvent event){
		try {
			URL url = getClass().getResource("/view/Regester.fxml") ; 
			FXMLLoader loader = new FXMLLoader(url) ; 
			Parent root = loader.load(); 
			Stage window  = (Stage)((Node)event.getSource()).getScene().getWindow(); 
			Scene scene = new Scene (root) ; 
			window.setScene(scene);
			window.setTitle("Create New Account") ; 
			window.centerOnScreen();
			window.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @FXML
    public void logIn(ActionEvent event) throws SQLException1 , SQLException, IOException{
    	try {
			String username = this.userName.getText() ; 
			String password = this.password.getText();
			User user = new User () ; 
			user.setUserName(username);
			user.setPassWord(password);
			UserDao.getInstance().logInUser(user);
			User user2 = UserDao.getInstance().getInforUser(user); 
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION) ; 
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/images/logo2.png"));
			alert.setHeaderText("Notification");
			alert.setContentText("Log In successfully !!");
			alert.setTitle("INFORMATION");
			alert.showAndWait();
			
			URL url = getClass().getResource("/view/InterfaceUser.fxml") ; 
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = loader.load();
			InterfaceUserController iuc = loader.getController();
			iuc.setRoleUser(user2); 
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene (root , 1050 , 650 ) ; 
			window.setScene(scene);
			window.centerOnScreen();
			window.show();
		}catch (SQLException1 e1) {
			notification.setText(e1.getMessage());
			notification.setAlignment(Pos.CENTER);
		}
    }
    
    @FXML
    public void show(ActionEvent event) {
    	if (checkShow == false) {
    		checkShow = true ; 
    	} else if (checkShow == true) {
    		checkShow = false ; 
    	}
    	
    	if (checkShow == true ) {
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageopen));
    		this.password.setVisible(false);
    		this.passwordshow.setVisible(true);
    		this.imagevieweye.setImage(imagePart);
    	} else {
    		this.password.setVisible(true);
    		this.passwordshow.setVisible(false);
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
        	this.imagevieweye.setImage(imagePart);
    	}
    }
    
    public void displayTime() {
    	LocalDateTime datenow = LocalDateTime.now();
		int hour = datenow.getHour();
		if (hour >= 0 && hour < 12 ) {
			hello.setText("Good Morning ! ");
		}else if (hour >= 12 && hour < 18 ) {
			hello.setText("Good Afternoon !");
		}else if (hour >= 18 && hour < 24 ) {
			hello.setText("Good Evening !");
		}
		hello.setAlignment(Pos.CENTER);
		today.setText("Today is " + String.valueOf(datenow.getDayOfMonth()) + "/" + String.valueOf(datenow.getMonthValue()));
		today.setAlignment(Pos.CENTER) ;
    }


}
