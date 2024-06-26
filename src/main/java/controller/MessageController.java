package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entity.InteractUser;
import Entity.MessageUser;
import Entity.User;
import dao.UserDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
// import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MessageController implements Initializable , Role<User>{

	@FXML
	private ListView<InteractUser> listViewUser ;
	
	@FXML
	private ListView<MessageUser> listChat ;
	
	@FXML
	private Circle avata ;
	
	@FXML
	private Circle avataInteract ; 
	
	@FXML
	private TextField message ; 
	
	@FXML
	private Button buttonsend;
	
	@FXML
	private Label nameInteract ; 
	
	@FXML
	private MenuItem menuitemexit;
	
	@FXML
	private ContextMenu contextmenu ; 
	
	@FXML
	private Button setting ; 
	
	private User user ; 
	
	private InteractUser interactuser ; 
	
	@Override
	public void setRoleUser(User user) {
		this.user = user ;
		Image image = UserDao.getInstance().getImageFromDatabase(user);
		avata.setFill(new ImagePattern(image));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuitemexit.setOnAction(event -> {
			Dialog<Boolean> dialog = new Dialog<>();
			dialog.setTitle("Confirm");
			dialog.setHeaderText("Exit ?");
			
			ButtonType btok = new ButtonType("OK" , ButtonBar.ButtonData.OK_DONE); 
			ButtonType btcancel = new ButtonType("Cancel" , ButtonBar.ButtonData.CANCEL_CLOSE);
			dialog.getDialogPane().getButtonTypes().addAll(btok , btcancel );
			dialog.setResultConverter(choice -> {
				if (choice == btok) {
					Stage stage = (Stage) setting.getScene().getWindow();
					stage.close();
					return true ; 
				} else {
					return false ;
				}
			});
			dialog.showAndWait();
		});
		
		setting.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
                contextmenu.show(setting, e.getScreenX(), e.getScreenY());
            }
		});
		
		message.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (message.getText().isEmpty()) {
					buttonsend.setVisible(false);
				} else {
					buttonsend.setVisible(true);
				}
			}
		});
		listViewUser.setOnMouseClicked(event -> {
	        InteractUser interactuser = listViewUser.getSelectionModel().getSelectedItem();
	        this.interactuser = interactuser ; 
	        nameInteract.setText(interactuser.getFullnameinteract());
	        User user = new User();
	        user.setUserName(interactuser.getUsernameinteract());
	        Image image = UserDao.getInstance().getImageFromDatabase(user);
	        if (image == null) {
	            Image ig = new Image(getClass().getResourceAsStream("/Images/avata.png"));
	            avataInteract.setFill(new ImagePattern(ig));
	        } else {
	            avataInteract.setFill(new ImagePattern(image));
	        }
	        displaymessage();
	    });
		
		message.setOnAction(event -> actionSend());
	}
	
	@FXML
	public void send(ActionEvent event) {
		actionSend();
	}
	
	@FXML
	public void display(ActionEvent event) {
		getListInteract();
	}
	
	@FXML
	public void displayChat(ActionEvent event) {
		displaymessage();
	}
	
	public void actionSend() {
		String message = this.message.getText();
		String sender = this.user.getUserName();
		String receiver = this.interactuser.getUsernameinteract();
		int result = UserDao.getInstance().sendMessage(sender, receiver, message);
		if (result != 0 ) {
			System.out.println("Send succesfully!");
		} 
		this.message.setText("");
		displaymessage();
		getListInteract();
	}
	
	public void displaymessage() {
		listChat.getItems().clear();
		String nameuser = this.user.getUserName();
		String usernameinteract = interactuser.getUsernameinteract();
		List<MessageUser> listMessages = UserDao.getInstance().getMessage(this.user);
		
		List<MessageUser> messageinteract = new ArrayList<MessageUser>();
		for (MessageUser messageuser : listMessages) {
			if (messageuser.getSender().equals(usernameinteract)) {
				messageinteract.add(messageuser);
			} else if (messageuser.getReceiver().equals(usernameinteract)) {
				messageinteract.add(messageuser);
			}
		}
		
		ObservableList<MessageUser> message = FXCollections.observableArrayList(messageinteract);
		
		listChat.getItems().addAll(message);
		listChat.setCellFactory(param -> new ListCell<MessageUser>() {
			private boolean isSender = false ;  // kiểm tra xem ô đã được tô màu chưa
			
			@Override
            protected void updateItem(MessageUser messageuser, boolean empty) {
                super.updateItem(messageuser, empty);
                if (empty || messageuser == null) {
                    setText(null);
                    setStyle(null);
                    setTextFill(Color.BLACK);
                } else {
                    if (messageuser.getSender().equals(nameuser)) {
                    	setText("You : " + messageuser.getMessage());
                    	setStyle("-fx-background-color: #0000ff;");
                    	setTextFill(Color.WHITE);
                    	if (!isSender) {
                            setStyle("-fx-background-color: #0000ff;");
                            isSender = true; 
                        }
                    } else if (messageuser.getSender().equals(usernameinteract)) {
                    	setText(interactuser.getFullnameinteract() + " : " + messageuser.getMessage());
                    	if (isSender) {
                            setStyle(null);
                            setTextFill(Color.BLACK);// Reset the style
                            isSender = false;
                        }
                    }
                }
            }
		});
	}
	
	public void getListInteract() {
		listViewUser.getItems().clear();
		
		List<InteractUser> listUserInteract = UserDao.getInstance().getListInteract(this.user.getUserName());
		ObservableList<InteractUser> interactuser = FXCollections.observableArrayList(listUserInteract);
		listViewUser.getItems().addAll(interactuser);
		listViewUser.setCellFactory(param -> new ListCell<InteractUser>() {
			@Override
            protected void updateItem(InteractUser interactuser, boolean empty) {
                super.updateItem(interactuser, empty);

                if (empty || interactuser == null) {
                    setText(null);
                } else {
                    setText(interactuser.getFullnameinteract());
                }
                
            }
		});
	}
}
