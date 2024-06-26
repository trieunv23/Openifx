package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Entity.Gender;
import Entity.User;
import Exception.AdminException;
import Exception.SQLException1;
import dao.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Change_AdminController implements Initializable , Role<User> , ConnectInterface<AdminController>{
	private AdminController admincontroller ;  

    @FXML
    private TextField asset;

    @FXML
    private DatePicker dayofbrith;

    @FXML
    private TextField email;

    @FXML
    private MenuItem female;

    @FXML
    private TextField fullname;

    @FXML
    private MenuItem male;

    @FXML
    private MenuItem other;

    @FXML
    private TextField password;

    @FXML
    private TextField phonenumber;

    @FXML
    private TextField username;

    @FXML
    private Label usernameuser;
    
    private String[] gender = {"Male","Female","Other"} ;
    
    ObservableList<String> list = FXCollections.observableArrayList(gender) ; 
    
    @FXML
    private ComboBox<String> choiceGender; 
    
    private User user ;
    
    @Override
	public void setParentController(AdminController admincontroller) {	
		this.admincontroller = admincontroller ; 
	}
    
    @Override
	public void setRoleUser(User user) {
    	username.setText(user.getUserName());
    	password.setText(user.getPassWord());
    	fullname.setText(user.getFullName());
    	phonenumber.setText(user.getPhoneNumber());
    	email.setText(user.getEmail());
    	dayofbrith.setValue(user.getDayOfBirth());
    	choiceGender.setValue(user.getGender().getValue());
    	asset.setText(String.valueOf(user.getAsset()));
    	
    	this.user = user ; 
    	// usernameuser.setText(this.user.getUserName());
	}

    @FXML
    public void cancel(ActionEvent event) {
    	Stage window  = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.close();
    }
    
    @FXML
    public void finish(ActionEvent event) throws SQLException1 , AdminException {
    	try {
    		validateRegistration(this.username.getText() , this.password.getText() , String.valueOf(this.asset.getText()));
    		String username = this.username.getText(); 
        	String password = this.password.getText(); 
        	String fullname = this.fullname.getText();
        	String phonenumber = this.phonenumber.getText(); 
        	String email = this.email.getText();
        	LocalDate dayofbrith = this.dayofbrith.getValue() ; 
        	Gender gender = Gender.fromString(choiceGender.getValue());
        	
        	float asset = 0 ; 
        	if (!this.asset.getText().isEmpty()) {
        		asset = Float.parseFloat(this.asset.getText());
        	} 
        	
        	Dialog<Boolean> dialog = new Dialog<>();
    		dialog.setTitle("Confirm");
    		dialog.setHeaderText("Do you want to change the account whose username is " + this.user.getUserName() + " ?");
        	ButtonType btok = new ButtonType("OK" , ButtonBar.ButtonData.OK_DONE); 
    		ButtonType btcancel = new ButtonType("Cancel" , ButtonBar.ButtonData.CANCEL_CLOSE);
    		dialog.getDialogPane().getButtonTypes().addAll(btok , btcancel );
        	
        	User user = new User(username , password , fullname , phonenumber , email , dayofbrith , gender , asset) ; 
			
			dialog.setResultConverter(choice -> {
    			if (choice == btok) {
    				try {
						int result = UserDao.getInstance().updateUser(user, this.user.getUserName()) ;
						if (result != 0 ) {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setHeaderText("Notification");
							alert.setContentText("Successfuly change !");
							alert.showAndWait();
							admincontroller.reset();
							Stage window  = (Stage)((Node)event.getSource()).getScene().getWindow();
							window.close();
						}
					} catch (SQLException1 e) {
						System.out.println("Aaa");
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText("Error");
						alert.setContentText(e.getMessage());
						alert.showAndWait();
					}
    				return true ; 
    			} else {
    				return false ;
    			}
    		});
    		dialog.showAndWait();
    	} catch (AdminException ae) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText(ae.getMessage());
			alert.showAndWait();
		}
    }
    
    public void validateRegistration(String username , String password , String asset) throws AdminException{
		if (username.isEmpty() || password.isEmpty()) {
			throw new AdminException("Username and password cannot be left blank !");
		} else if (!validateFloat(asset) && !asset.isEmpty()){
			throw new AdminException("Invalid asset !");
		} else if (UserDao.getInstance().checkExistUsername(username)) {
			throw new AdminException("Username aready exist !");
		}
	}
	
    public boolean validateFloat(String input) throws NumberFormatException{
        try {
        	Float.parseFloat(input);
        	return true ; 
        } catch (NumberFormatException e) {
        	return false ; 
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceGender.setItems(list);
	}
}
