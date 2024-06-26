package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
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

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class Add_AdminController implements Initializable , ConnectInterface<AdminController>{
	private AdminController admincontroller ; 
	
	@FXML
    private TextField asset;

    @FXML
    private DatePicker dayofbrith;

    @FXML
    private TextField email;

    @FXML
    private TextField fullname;
    
    @FXML
    private TextField password;

    @FXML
    private TextField phonenumber;

    @FXML
    private TextField username;
    
    private String[] gender = {"Male","Female","Other"} ; 
    
    @FXML
    private ComboBox<String> choiceGender; 
    
    private ObservableList<String> list = FXCollections.observableArrayList(gender) ; 
    
    @Override
	public void setParentController(AdminController admincontroller) {
		this.admincontroller = admincontroller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceGender.setItems(list);
		
		asset.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null , change -> {
            String newText = change.getControlNewText();

            if (newText.matches("\\d*")) { 
                return change;
            } else {
                return null;
            }
        }));
	}
	
	@FXML
    public void cancel(ActionEvent event) {
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.close();
    }

    @FXML
    public void finish(ActionEvent event) throws SQLException1, IOException {
    	try {
			validateRegistration(username.getText(), password.getText() , asset.getText());
			
			String fullname = this.fullname.getText(); 
			String phonenumber = this.phonenumber.getText();
			String email = this.email.getText();
			String username = this.username.getText();
			username = username.toLowerCase();
			String password = this.password.getText();
			LocalDate dayofbirth = this.dayofbrith.getValue(); 
			
			Gender gender ; 
			if (choiceGender.getValue() != null ) {
				gender = Gender.fromString(choiceGender.getValue()); 
			} else {
				gender = Gender.Null;
			}
			
			float asset = 0 ; 
			if (!this.asset.getText().isEmpty()) {
				asset = Float.parseFloat(this.asset.getText());
			}
						
			User user = new User(username , password , fullname , phonenumber , email , dayofbirth , gender , asset ) ; 
			int result = UserDao.getInstance().regesterUser(user); 
			if (result != 0 ) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText("Notification");
				alert.setContentText("Add Successfully !");
				alert.showAndWait();
				
				URL url = getClass().getResource("/view/Admin.fxml");
				FXMLLoader loader = new FXMLLoader(url) ; 
				loader.load();
				// AdminController ac = loader.getController();
				admincontroller.reset();
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				window.close();
			}
		} catch (AdminException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR); 
			alert.setHeaderText("Notification");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch (SQLException1 s1) {
			Alert alert = new Alert(Alert.AlertType.ERROR); 
			alert.setHeaderText("Notification");
			alert.setContentText(s1.getMessage());
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
}
