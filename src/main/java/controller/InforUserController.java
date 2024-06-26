package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.User;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InforUserController implements Initializable , Role<User>{
	@FXML
	Button out ;
	
	@FXML
    private Label asset ; 

    @FXML
    private Label dayOfBirth;

    @FXML
    private Label email;

    @FXML
    private Label fullname;

    @FXML
    private Label gender;

    @FXML
    private PasswordField passWord;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label userName;
    
    @FXML
    private ImageView iv ; 
    
    private User user ; 

    @Override
	public void setRoleUser(User u) {
    	this.user = u ; 
		this.userName.setText(u.getUserName());
		this.passWord.setText(u.getPassWord());
		this.passWord.setDisable(true);
		this.fullname.setText(u.getFullName());
		this.gender.setText(u.getGender().getValue());
		this.phoneNumber.setText(u.getPhoneNumber());
		this.email.setText(u.getEmail());
		if (u.getDayOfBirth() != null) {
			this.dayOfBirth.setText(u.getDayOfBirth().toString());
		}
		this.asset.setText(String.valueOf(u.getAsset()));
		
		this.userName.setAlignment(Pos.CENTER);
		this.passWord.setAlignment(Pos.CENTER);
		this.fullname.setAlignment(Pos.CENTER);
		this.gender.setAlignment(Pos.CENTER);
		this.email.setAlignment(Pos.CENTER);
		this.dayOfBirth.setAlignment(Pos.CENTER);
		this.asset.setAlignment(Pos.CENTER);
		this.phoneNumber.setAlignment(Pos.CENTER);
		
		Image image = UserDao.getInstance().getImageFromDatabase(u);
		iv.setImage(image);
	}
    
    public void updatePassword(User user) {
    	this.user = user ; 
    	this.passWord.setText(this.user.getPassWord());
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	private double xOffset = 0;
    private double yOffset = 0;
	
	@FXML
	public void changePassword(ActionEvent event) throws IOException {
		URL url = getClass().getResource("/view/Changepassword.fxml");
		FXMLLoader loader = new FXMLLoader(url) ; 
		
		Parent root = loader.load(); 
		
		ChangepasswordController cc = loader.getController();
		cc.setParentController(this);
		cc.setRoleUser(this.user); 
		Stage window = new Stage(); 
		window.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(root , 288 , 333 ) ; 
		scene.setFill(Color.TRANSPARENT);
		window.setScene(scene);
		window.initStyle(StageStyle.TRANSPARENT);
		
		root.setOnMousePressed((MouseEvent mouseEvent) -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
            window.setX(mouseEvent.getScreenX() - xOffset);
            window.setY(mouseEvent.getScreenY() - yOffset);
        });
        window.centerOnScreen();
        window.show();
	}
}
