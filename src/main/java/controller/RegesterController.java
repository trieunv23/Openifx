package controller;

import Exception.RegesterException;
import Exception.SQLException1;
import dao.UserDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Entity.Gender;
import Entity.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegesterController implements Initializable{
	
	@FXML
	private AnchorPane a1 ; 
	
	@FXML
	private AnchorPane a2 ; 

    @FXML
    private DatePicker dayOfBirth;

    @FXML
    private TextField email;
    
    @FXML
    private TextField fristname;
    
    @FXML
    private TextField lastname;

    @FXML
    private PasswordField password;
    
    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField phoneNuber;

    @FXML
    private TextField username;
    
    @FXML
    private Label notification ; 
    
    @FXML
    private TextField passwordshow ; 
    
    @FXML
    private TextField confirmpasswordshow ; 
    
    @FXML
    private CheckBox show ; 
    
    @FXML
    private Label resultDate ;  
    
    @FXML
    private Button finish; 
    
    @FXML
    private Button btshow1 ; 
    
    @FXML
    private Button btshow2 ; 
    
    @FXML
    private Button btback ; 
    
    @FXML
    private Button checkemail ; 
    
    @FXML
    private Button next ; 
    
    private boolean checkShow1 = false ; 
    
    private boolean checkShow2 = false ; 
    
    @FXML
    private ImageView i1 ;
    
    @FXML
    private ImageView i2 ;
    
    @FXML
    private ImageView iconcheck ; 
    
    @FXML
    private HBox passwordhb ; 
    
    @FXML
    private HBox confirmpasswordhb ; 
    
    @FXML
    private Tooltip tooltip ; 
    
    @FXML
    private Tooltip noteemail ; 
    
    private String urlimageeyeclose = "/images/closeeye.png";
    private String urlimageopen = "/images/openeye.png";
    
    private String urlcheck = "/images/check.png" ; 
    private String urlchecksuccsecfully = "/images/checksuccesfully.png" ; 
    private String urlcheckfail = "/images/checkfail.png" ; 
    
    @FXML
    private ComboBox<String> choiceGender ;
    
    private String[] gender = {"Male","Female","Other"} ; 
    
    private ObservableList<String> list = FXCollections.observableArrayList(gender); 
    
    private User userregester = new User(); 
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	dayOfBirth.setEditable(false);
    	
    	finish.setDisable(true);
    	a1.setVisible(true);
    	a2.setVisible(false);
    	btback.setVisible(false);
    	
    	choiceGender.setItems(list);
    	passwordshow.setVisible(false);
    	confirmpasswordshow.setVisible(false);
    	btshow1.setVisible(false);
    	btshow2.setVisible(false);
    	
    	Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
    	this.i1.setImage(imagePart);
    	this.i2.setImage(imagePart);
    	
    	checkemail.setVisible(false);
    	Image imageCheck = new Image(getClass().getResourceAsStream(urlcheck));
    	this.iconcheck.setImage(imageCheck);
    	
    	this.email.textProperty().addListener(new ChangeListener<String>() {
 
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!email.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$") && !email.getText().isEmpty()) {
					email.setStyle("-fx-border-color: red;");
				} else {
					email.setStyle("-fx-border-color: transparent;");
				}
				
				iconcheck.setImage(imageCheck);
				if (email.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
					checkemail.setVisible(true);
				} else {
					checkemail.setVisible(false);
				}
			}
		});
    	
    	email.setOnMouseEntered(even -> {
    		Image icon = new Image("/images/note.png");
    		ImageView iconView = new ImageView(icon);
    		iconView.setFitHeight(20);
            iconView.setFitWidth(20);
            noteemail.setGraphic(iconView);
            noteemail.setText("Please enter a valid email, do not contain special characters, email must exist");
            Bounds bounds = email.localToScreen(email.getBoundsInLocal());
            double x = bounds.getMaxX();
            double y = bounds.getMaxY();
            noteemail.show(email , x , y + 3);
    	});
    	
    	email.setOnMouseExited(event -> {
    		noteemail.hide();
    	});	
    	
    	passwordhb.setOnMouseEntered(event -> {
    		Image icon = new Image("/images/note.png");
    		ImageView iconView = new ImageView(icon);
    		iconView.setFitHeight(20);
            iconView.setFitWidth(20);
            tooltip.setGraphic(iconView);
            Bounds bounds = passwordhb.localToScreen(passwordhb.getBoundsInLocal());
            double x = bounds.getMaxX();
            double y = bounds.getMaxY();
            tooltip.show(passwordhb , x , y + 3);
        });
    	
    	passwordhb.setOnMouseExited(event -> {
             tooltip.hide();
    	});	
    	
    	username.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				username.setStyle("-fx-border-color : transparent ;");
			}
		});
    	
    	password.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!password.getText().isEmpty()) {
					btshow1.setVisible(true);
				} else {
					btshow1.setVisible(false); 
				}
				
				if (!password.getText().isEmpty() && !password.getText().matches("^[a-zA-Z0-9]{8,}$")) {
					passwordhb.setStyle("-fx-border-color : red ; ");
				} else {
					passwordhb.setStyle("-fx-border-color : transparent ; ");
				}
				
				String passwordValue = password.getText() ; 
				passwordshow.setText(passwordValue);		
			}
    		
		});
    	
    	passwordshow.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!passwordshow.getText().isEmpty()) {
					btshow1.setVisible(true);
				} else {
					btshow1.setVisible(false); 
				}

				String passwordValue = passwordshow.getText() ; 
				password.setText(passwordValue);
			}
		});
    	
    	confirmPassword.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!confirmPassword.getText().isEmpty() && !confirmPassword.getText().equals(password.getText())) {
					confirmpasswordhb.setStyle("-fx-border-color: red;");
				} else if (confirmPassword.getText().equals(password.getText())){
					confirmpasswordhb.setStyle("-fx-border-color: transparent;");
				}
				
				if (!confirmPassword.getText().isEmpty()) {
					btshow2.setVisible(true);
				} else {
					btshow2.setVisible(false);
				}
				
				confirmpasswordshow.setText(confirmPassword.getText());
			}
    		
		});
    	
    	confirmpasswordshow.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!confirmpasswordshow.getText().isEmpty() && !confirmpasswordshow.getText().equals(password.getText())) {
					confirmpasswordhb.setStyle("-fx-border-color: red;"); // ?
				} else if (confirmpasswordshow.getText().equals(password.getText())){
					confirmpasswordhb.setStyle("-fx-border-color: transparent;"); // ?
				}
				
				if (!confirmpasswordshow.getText().isEmpty()) {
					btshow2.setVisible(true);
				} else {
					btshow2.setVisible(false);
				}
				
				confirmPassword.setText(confirmpasswordshow.getText());
			}
    		
		});
    	
		ChangeListener<String> textChangeListener = new ChangeListener<String>() {
					
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if (!username.getText().isEmpty() 
							&& confirmPassword.getText().equals(password.getText()) 
							&& !password.getText().isEmpty() 
							&& password.getText().matches("^[a-zA-Z0-9]{8,}$")) {
								finish.setDisable(false) ;
						} else {
							finish.setDisable(true);
						}
						
					}
				};
				
				username.textProperty().addListener(textChangeListener);
				password.textProperty().addListener(textChangeListener);
				confirmPassword.textProperty().addListener(textChangeListener);
			}
    
    @FXML
    public void show1(ActionEvent event) {
    	if (checkShow1 == false) {
    		checkShow1 = true ; 
    	} else if (checkShow1 == true) {
    		checkShow1 = false ; 
    	}
    	
    	if (checkShow1 == true ) {
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageopen));
        	this.i1.setImage(imagePart);
    		this.password.setVisible(false);
    		this.passwordshow.setVisible(true);
    	} else {
    		this.password.setVisible(true);
    		this.passwordshow.setVisible(false);
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
        	this.i1.setImage(imagePart);
    	}
    }
    
    @FXML
    public void show2(ActionEvent event) {
    	if (checkShow2 == false) {
    		checkShow2 = true ; 
    	} else if (checkShow2 == true) {
    		checkShow2 = false ; 
    	}
    	
    	if (checkShow2 == true ) {
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageopen));
        	this.i2.setImage(imagePart);
    		this.confirmPassword.setVisible(false);
    		this.confirmpasswordshow.setVisible(true);
    	} else {
    		this.confirmPassword.setVisible(true);
    		this.confirmpasswordshow.setVisible(false);
    		Image imagePart = new Image(getClass().getResourceAsStream(urlimageeyeclose));
        	this.i2.setImage(imagePart);
    	}
    }
    
    @FXML
    public void checkemail(ActionEvent event) {
    	String emailValue = email.getText(); 
    	if (checkEmailExist(emailValue)) {
    		Image imagesuccsecfully = new Image(urlchecksuccsecfully) ; 
    		iconcheck.setImage(imagesuccsecfully);
    		email.setStyle("-fx-border-color : transparent ;");
    	} else {
    		Image imagefail = new Image(urlcheckfail) ; 
    		iconcheck.setImage(imagefail);
    		email.setStyle("-fx-border-color : red ;");
    	}
    }
    
    public void closeWindow(ActionEvent event) {
    	Stage window =  (Stage) ((Node)event.getSource()).getScene().getWindow() ; 
    	window.close();
    }
    
    @FXML
    public void next(ActionEvent event) {
    	// 
    	try {
			validateRegistration1(this.fristname.getText(), this.lastname.getText(), this.email.getText(), this.phoneNuber.getText(), choiceGender.getValue(), dayOfBirth.getValue());
			
			String firstname = this.fristname.getText();
			firstname = firstname.trim();
			String lastname = this.lastname.getText();
			lastname = lastname.trim(); 
			String fullname = firstname.concat(" " + lastname);
			String email = this.email.getText();
			String phoneNumber = this.phoneNuber.getText();
			Gender gender = Gender.fromString(choiceGender.getValue()) ;
			LocalDate dayofbirth = dayOfBirth.getValue(); 
			userregester.setFullName(fullname);
			userregester.setEmail(email);
			userregester.setPhoneNumber(phoneNumber);
			userregester.setGender(gender);
			userregester.setDayOfBirth(dayofbirth);
			
			a1.setVisible(false);
			a2.setVisible(true);
			btback.setVisible(true);
		} catch (RegesterException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("/images/logo2.png"));
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
    }
    
    private double xOffset = 0;
    private double yOffset = 0;
     
    @FXML
    public void finish(ActionEvent event) throws Exception , SQLException1 {
    	try {
			validateRegistration2(this.username.getText(), this.password.getText(), this.confirmPassword.getText());
			String username = this.username.getText();
			String password = this.password.getText();
			
			this.userregester.setUserName(username);
			this.userregester.setPassWord(password);
			
			URL url = getClass().getResource("/view/ConfrimCode.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = loader.load();
			ConfirmCodeController ccc = loader.getController();
			ccc.transmitEmail(email.getText());
			
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
				int result = UserDao.getInstance().regesterUser(userregester);
				if (result != 0) {
					Alert alert = new Alert (Alert.AlertType.INFORMATION);
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				    stage.getIcons().add(new Image("/images/logo2.png"));
					alert.setTitle("INFORMATION");
					alert.setHeaderText("Notification");
					alert.setContentText("Registered successfully !!");
					alert.showAndWait();
					Stage stageRegester = (Stage)((Node)event.getSource()).getScene().getWindow() ; 
					stageRegester.close();
				}
			}
			
    	} catch (RegesterException re) {
			Alert alert = new Alert(AlertType.ERROR);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("/images/logo2.png"));
			alert.setTitle("Error");
			alert.setContentText(re.getMessage());
			alert.showAndWait();
		} catch (SQLException1 e) {
			Alert alert = new Alert(AlertType.ERROR);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("/images/logo2.png"));
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
    }
    
    @FXML
    public void logIn(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Log_In.fxml"));
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene (root , 550 , 400 ) ; 
			window.setScene(scene);
			window.setTitle("OPENIFX");
			window.centerOnScreen();
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    public void back(ActionEvent event) {
    	a1.setVisible(true);
    	a2.setVisible(false);
    	btback.setVisible(false);
    }
    
    public void validateRegistration1(String fname , String lname , String email , String phonenumber , String gender , LocalDate dayofbrith) throws RegesterException {
    	if (fname.isEmpty() && lname.isEmpty() || email.isEmpty() || phonenumber.isEmpty() || gender == null || dayofbrith == null) {
    		throw new RegesterException("Please enter complete information !");
    	} 
    	
    	if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) { // email 
    		throw new RegesterException("Please enter a valid email !");
    	} else {
    		if (!checkEmailExist(email)) {
    			throw new RegesterException("Email not exist !");
    		}
    	}
    	
    	if (!phonenumber.matches("^\\+?[0-9]+$")) { // phonenumber
    		throw new RegesterException("Please enter a valid phone number !");
    	}
    	
    	LocalDate datenow = LocalDate.now();
		if (dayofbrith.isAfter(datenow)) {
			throw new RegesterException("Please enter a valid date of birth !") ; 
		}
    	
    }
    
    public void validateRegistration2(String username, String password , String confirmpassword) throws RegesterException {
    	if (!username.matches("^[a-zA-Z0-9]{5,}$")) { // username 
    		throw new RegesterException("Please enter a valid username !"); 
    	} else if (UserDao.getInstance().checkExistUsername(username)) {
    		throw new RegesterException("Username already exist !"); 
    	}
    	
    	if (!password.matches("^[a-zA-Z0-9]{8,}$")) { // password
    		throw new RegesterException("Please enter a valid password !");
    	}
    	
    	if (!password.equals(confirmpassword)) {
    		throw new RegesterException("Password incorrect");
    	}
    	
    }
    
    public boolean checkEmailExist(String email) { 
        try {
        	String url = "https://api.hunter.io/v2/email-verifier?email="+email+"&api_key=3934a1a928dce497e714f59cd953fa5ae06b777d" ; 

        	
        	HttpGet httpGet = new HttpGet(url);
            // Mở kết nối HTTP
        	HttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpGet);

            // Đọc phản hồi từ Hunter API
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuilder response = new StringBuilder();
            String line ; 
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
           
            String status = jsonObject.getAsJsonObject("data").get("status").getAsString();
            
            return status.equals("valid") ;  
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false ; 
    }
}
