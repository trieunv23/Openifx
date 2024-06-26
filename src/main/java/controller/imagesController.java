package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.User;
import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class imagesController implements Initializable, Role<User>,  ConnectInterface<InterfaceUserController>{
	private InterfaceUserController parentController;
	
	@FXML 
	private ImageView avata ;
	
	@FXML 
	private Label resultFile;
	
	@FXML 
	private AnchorPane anchorPane ; 
	
	@FXML 
	private Button change ; 
	
	private Image image ; 
	
	private User user ; 
	
	private File file ; 
	
	private Image image2 ;
	
	@Override
	public void setParentController(InterfaceUserController parentController) { // parentController được lấy từ lớp InterfaceUserController thông qua ic.setParentController(this); 
        this.parentController = parentController;
    }
	
	@Override
	public void setRoleUser(User user) {
		this.user = user ;
		Image image = UserDao.getInstance().getImageFromDatabase(user);
		avata.setImage(image);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		this.avata.setImage(image2);
		change.setDisable(true);
	} 
	
	@FXML
	public void addFile(ActionEvent event ) {
		Stage stage = (Stage) this.anchorPane.getScene().getWindow();
		FileChooser fc = new FileChooser() ;
		fc.setTitle("Choose Image");
		FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image File", "*jpg" , "png" );
		fc.getExtensionFilters().add(imageFilter);
		this.file = fc.showOpenDialog(stage);
		if (file!=null) {
			Image image = new Image(file.toURI().toString());
			avata.setImage(image);
			this.image = image ; 
			resultFile.setText(file.toString());
		}
		if (resultFile.getText() != "") {
			change.setDisable(false);
		}
	}

	@FXML
	public void finish(ActionEvent event) {
		try {
			URL url = getClass().getResource("/view/InterfaceUser.fxml") ; 
			FXMLLoader loader = new FXMLLoader(url);
			loader.load(); 
			InterfaceUserController iuc = loader.getController();
			UserDao.getInstance().saveImageToDatabase(user, file);
			iuc.setRoleUser(user);
			parentController.updateAvataImage(image);
			
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void cancel (ActionEvent event ) {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.close(); 
	}
}
