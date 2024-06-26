package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.User;
import Exception.SQLException1;
import dao.UserDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChangepasswordController implements Initializable , Role<User> , ConnectInterface<InforUserController> {
	private InforUserController inforUsercontroller ; 
	
	private User user;
	
	@FXML
	private  PasswordField currentPassword ; 
	
	@FXML
	private  PasswordField newPassword ; 
	
	@FXML
	private  PasswordField againnewPassword ; 
	
	@FXML
	private Button change ; 
	
	@FXML
	private TextField currentpasswordshow;
	
	@FXML
	private TextField newpasswordshow;
	
	@FXML
	private TextField againpasswordshow;
	
	@FXML
	private Button currentpasswordbt; 
	
	@FXML
	private Button newpasswordbt; 
	
	@FXML
	private Button againpasswordbt; 
	
	@FXML
	private HBox currenpasswordhb;
	
	@FXML
	private HBox newpasswordhb;
	
	@FXML
	private HBox againpasswordhb;
	
	@FXML
	private Tooltip tooltip ;
	
	@FXML
	private ImageView i1 ; 
	
	@FXML
	private ImageView i2 ;
	
	@FXML
	private ImageView i3 ;
	
	private boolean checkShow1 = false ; 
	
	private boolean checkShow2 = false ; 
	
	private boolean checkShow3 = false ;
	
	private String urlimageeyeclose = "/images/closeeye.png";
    private String urlimageopen = "/images/openeye.png";
	
	@Override
	public void setParentController(InforUserController inforUsercontroller) {
		this.inforUsercontroller = inforUsercontroller ; 
	}
	
	@Override
	public void setRoleUser(User user) {
		this.user = user ; 
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		currentpasswordshow.setVisible(false);
		newpasswordshow.setVisible(false);
		againpasswordshow.setVisible(false);
		
		Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
		i1.setImage(imagePart);
		i2.setImage(imagePart);
		i3.setImage(imagePart);
		currentpasswordbt.setVisible(false);
		newpasswordbt.setVisible(false);
		againpasswordbt.setVisible(false);
		
		this.change.setDisable(true);
		
		newpasswordhb.setOnMouseEntered(event -> {
    		Image icon = new Image("/images/note.png");
    		ImageView iconView = new ImageView(icon);
    		iconView.setFitHeight(20);
            iconView.setFitWidth(20);
            tooltip.setGraphic(iconView);
            Bounds bounds = newpasswordhb.localToScreen(newpasswordhb.getBoundsInLocal());
            double x = bounds.getMaxX();
            double y = bounds.getMaxY();
            tooltip.show(newpasswordhb , x , y + 3);
        });
    	
		newpasswordhb.setOnMouseExited(event -> {
             tooltip.hide();
    	});	
		
		currentPassword.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					currenpasswordhb.setStyle("-fx-border-color : transparent ;");
					if (!currentPassword.getText().isEmpty()) {
						currentpasswordbt.setVisible(true);
					} else {
						currentpasswordbt.setVisible(false);
					}
					
					currentpasswordshow.setText(currentPassword.getText());
			}
		});
		
		newPassword.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String pwvalue = newPassword.getText();
				if (!pwvalue.isEmpty() && !pwvalue.matches("^[a-zA-Z0-9]{8,}$") ) {
					newpasswordhb.setStyle("-fx-border-color: red ;");
				} else {
					newpasswordhb.setStyle("-fx-border-color: transparent ;");
				}
				
				if (!newPassword.getText().isEmpty()) {
					newpasswordbt.setVisible(true);
				} else {
					newpasswordbt.setVisible(false);
				}
				
				newpasswordshow.setText(newPassword.getText());
			}
		});
		
		againnewPassword.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!againnewPassword.getText().equals(newPassword.getText()) && !newPassword.getText().isEmpty()) {
					againpasswordhb.setStyle("-fx-border-color: red ;");
				} else {
					againpasswordhb.setStyle("-fx-border-color: transparent ;");
				}
				
				if (!againnewPassword.getText().isEmpty()) {
					againpasswordbt.setVisible(true);
				} else {
					againpasswordbt.setVisible(false);
				}
				
				againpasswordshow.setText(againnewPassword.getText());
				
			}
		});
		
		currentpasswordshow.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					currenpasswordhb.setStyle("-fx-border-color : transparent ;");
					if (!currentpasswordshow.getText().isEmpty()) {
						currentpasswordbt.setVisible(true);
					} else {
						currentpasswordbt.setVisible(false);
					}
					
					currentPassword.setText(currentpasswordshow.getText());
			}
		});
		
		newpasswordshow.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String pwvalue = newpasswordshow.getText();
				if (!pwvalue.isEmpty() && !pwvalue.matches("^[a-zA-Z0-9]{8,}$") ) {
					newpasswordhb.setStyle("-fx-border-color: red ;");
				} else {
					newpasswordhb.setStyle("-fx-border-color: transparent ;");
				}
				
				if (!newpasswordshow.getText().isEmpty()) {
					newpasswordbt.setVisible(true);
				} else {
					newpasswordbt.setVisible(false);
				}
				
				newPassword.setText(newpasswordshow.getText());
			}
		});
		
		againpasswordshow.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!againpasswordshow.getText().equals(newPassword.getText()) && !newPassword.getText().isEmpty()) {
					againpasswordhb.setStyle("-fx-border-color: red ;");
				} else {
					againpasswordhb.setStyle("-fx-border-color: transparent ;");
				}
				
				if (!againpasswordshow.getText().isEmpty()) {
					againpasswordbt.setVisible(true);
				} else {
					againpasswordbt.setVisible(false);
				}
				
				againnewPassword.setText(againpasswordshow.getText());
				
			}
		});
		
		ChangeListener<String> textChangeListener = new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!currentPassword.getText().isEmpty() 
					&& againnewPassword.getText().equals(newPassword.getText()) 
					&& !newPassword.getText().isEmpty() 
					&& newPassword.getText().matches("^[a-zA-Z0-9]{8,}$")) {
						change.setDisable(false) ;
				} else {
					change.setDisable(true);
				}
				
			}
		}; 
		
		currentPassword.textProperty().addListener(textChangeListener);
		newPassword.textProperty().addListener(textChangeListener);
		againnewPassword.textProperty().addListener(textChangeListener);
	}
	
	@FXML
    public void showCurrentPassword(ActionEvent event) {
    	if (checkShow1 == false) {
    		checkShow1 = true ; 
    	} else if (checkShow1 == true) {
    		checkShow1 = false ; 
    	}
    	
    	if (checkShow1 == true ) {
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageopen));
        	this.i1.setImage(imagePart);
    		this.currentPassword.setVisible(false);
    		this.currentpasswordshow.setVisible(true);
    	} else {
    		this.currentPassword.setVisible(true);
    		this.currentpasswordshow.setVisible(false);
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
        	this.i1.setImage(imagePart);
    	}
    }
	
	@FXML
    public void showNewPassword(ActionEvent event) {
    	if (checkShow2 == false) {
    		checkShow2 = true ; 
    	} else if (checkShow2 == true) {
    		checkShow2 = false ; 
    	}
    	
    	if (checkShow2 == true ) {
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageopen));
        	this.i2.setImage(imagePart);
    		this.newPassword.setVisible(false);
    		this.newpasswordshow.setVisible(true);
    	} else {
    		this.newPassword.setVisible(true);
    		this.newpasswordshow.setVisible(false);
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
        	this.i2.setImage(imagePart);
    	}
    }
	
	@FXML
    public void showAgainPassword(ActionEvent event) {
    	if (checkShow3 == false) {
    		checkShow3 = true ; 
    	} else if (checkShow3 == true) {
    		checkShow3 = false ; 
    	}
    	
    	if (checkShow3 == true ) {
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageopen));
        	this.i3.setImage(imagePart);
    		this.againnewPassword.setVisible(false);
    		this.againpasswordshow.setVisible(true);
    	} else {
    		this.againnewPassword.setVisible(true);
    		this.againpasswordshow.setVisible(false);
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
        	this.i3.setImage(imagePart);
    	}
    }
	
	@FXML
	public void close(ActionEvent event) {
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow() ; 
    	window.close();
	}
	
	private double xOffset = 0;
    private double yOffset = 0;
	
	@FXML
	public void change(ActionEvent event) throws SQLException1 {
		if (!this.currentPassword.getText().equals(this.user.getPassWord())){
			this.currenpasswordhb.setStyle("-fx-border-color: red");
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Wrong current password !");
			alert.showAndWait();
		} else {
			if (this.user.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
				try {
					URL url = getClass().getResource("/view/ConfrimCode.fxml");
					FXMLLoader loader = new FXMLLoader(url);
					Parent root = loader.load();
					ConfirmCodeController ccc = loader.getController();
					ccc.transmitEmail(this.user.getEmail()) ; 
					Stage window = new Stage() ; 
					window.initModality(Modality.APPLICATION_MODAL);
					window.initStyle(StageStyle.TRANSPARENT);
					window.setTitle("Confirm Code"); 
					
					root.setOnMousePressed((MouseEvent mouseEvent) -> {
			            xOffset = mouseEvent.getSceneX();
			            yOffset = mouseEvent.getSceneY();
			        });

			        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
			            window.setX(mouseEvent.getScreenX() - xOffset);
			            window.setY(mouseEvent.getScreenY() - yOffset);
			        });
					
					Scene scene = new Scene (root , 316 , 356 ) ; 
					scene.setFill(Color.TRANSPARENT);
					window.setScene(scene) ;
					window.showAndWait();
					if (ConfirmCodeController.checkEmail == true ) {
						String newpassword = this.newPassword.getText();
						this.user.setPassWord(newpassword);
						int result = UserDao.getInstance().updateUser(this.user, this.user.getUserName());
						if (result != 0 ) {
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Notification");
							alert.setContentText("Change succesfully!");
							alert.showAndWait();
							inforUsercontroller.updatePassword(this.user);
							Stage window2 = (Stage)((Node)event.getSource()).getScene().getWindow() ; 
					    	window2.close();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
	}
	
	

}
