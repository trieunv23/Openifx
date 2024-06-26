package controller;

import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ConfirmCodeController implements Initializable{
	@FXML
	private TextField t1 ;
	
	@FXML
	private TextField t2 ;
	
	@FXML
	private TextField t3 ;
	
	@FXML
	private TextField t4 ;
	
	@FXML
	private Label notification ; 
	
	@FXML
	private Label nottificationemail ; 
	
	@FXML
	private ImageView close ; 
	
	@FXML
	private Label timeline ; 
	
	@FXML
	private Button sendAgain ; 
	
	@FXML
	private Button confirm ; 
	
	private String email ; 
	
	private int code = -1; 
	
	private int countdownSeconds = 60 ; 
	
	private Timeline timelineValue ; 
	
	public static boolean checkEmail = false ; 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/images/close.png")) ;
		close.setImage(image);
		sendAgain.setVisible(false); 
		confirm.setDisable(true);
		
		setAutoFocusOnInput(t1, t2);
	    setAutoFocusOnInput(t2, t3);
	    setAutoFocusOnInput(t3, t4);
	     
	    applyRestrictions(t1);
	    applyRestrictions(t2);
	    applyRestrictions(t3);
	    applyRestrictions(t4);
	     
	    t2.setAlignment(Pos.CENTER);
	    t3.setAlignment(Pos.CENTER);
	    t4.setAlignment(Pos.CENTER);
	    t1.setAlignment(Pos.CENTER);
	     
	     ChangeListener<String> tfcode = new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				t1.setStyle("-fx-border-color: #808080 ;");
				t2.setStyle("-fx-border-color: #808080 ;");
				t3.setStyle("-fx-border-color: #808080 ;");
				t4.setStyle("-fx-border-color: #808080 ;");
				notification.setText("");
				
				if (!t1.getText().isEmpty() && !t2.getText().isEmpty() && !t3.getText().isEmpty() && !t4.getText().isEmpty()) {
					confirm.setDisable(false);
				} else {
					confirm.setDisable(true);
				}
			}
		};
		
		t1.textProperty().addListener(tfcode);
		t2.textProperty().addListener(tfcode);
		t3.textProperty().addListener(tfcode);
		t4.textProperty().addListener(tfcode);		
	}
	
	private void setAutoFocusOnInput(TextField sourceTextField, TextField targetTextField) {
        sourceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                targetTextField.requestFocus();
            }
        });
    }
	
	private void applyRestrictions(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                textField.setText(newValue.substring(0, 1));
            }
            if (!newValue.matches("[0-9]*")) {
                textField.setText("");
            }
        });
    }
	
	public void timeLine() {
		countdownSeconds = 60 ; 
		timeline.setText(String.valueOf(countdownSeconds));
		timelineValue = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1) , e -> {
			countdownSeconds -- ; 
			Platform.runLater(() -> {
			    timeline.setText(String.valueOf(countdownSeconds));
			    timeline.setAlignment(Pos.CENTER);
			});
			if (countdownSeconds <= 0 ) {
				timelineValue.stop();
				timeline.setVisible(false);
				sendAgain.setVisible(true);
				code = -1 ; 
			}
		}));
		
		timelineValue.setCycleCount(Timeline.INDEFINITE);
		timelineValue.play();
	}
	
	@FXML
	public void sendAgain(ActionEvent event) {
		timeline.setVisible(true);
		sendAgain.setVisible(false);
		code = sendCodeToEmail(email); 
		timeLine();
	}
	
	@FXML
	public void close(ActionEvent event) {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow() ; 
		window.close();
	}
	
	@FXML
	public void confirm(ActionEvent event) {
		String codeString = t1.getText() + t2.getText() + t3.getText() + t4.getText() ; 
		int codeValue = Integer.parseInt(codeString);
		if (codeValue != code) {
			t1.setStyle("-fx-border-color: red ;");
			t2.setStyle("-fx-border-color: red ;");
			t3.setStyle("-fx-border-color: red ;");
			t4.setStyle("-fx-border-color: red ;");
			notification.setText("Verification code is incorrect, please try again!");
			notification.setAlignment(Pos.CENTER);
			checkEmail = false ; 
		} else {
			checkEmail = true ; 
			Stage stageconfirm = (Stage)((Node)event.getSource()).getScene().getWindow() ; 
			stageconfirm.close();
		}
	}
	
	public int sendCodeToEmail(String receiver) {
		String HOST_NAME = "smtp.gmail.com";
		  
		int SSL_PORT = 465; 
	   
		// int TSL_PORT = 587; 
		  
		String APP_EMAIL = "trieuunv@gmail.com"; 
		  
		String APP_PASSWORD = "hwwv sztf tnyb megg";
		
		Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.socketFactory.port", SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", SSL_PORT);
 
        // get Session
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(APP_EMAIL, APP_PASSWORD);
            }
        });
        
        int min = 1000 ; 
        int max = 9999 ;
        Random random = new Random() ; 
        int code = random.nextInt(max - min + 1 ) + min ; 
        
        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Testing Subject");
            message.setText("Mã xác thực : " + code );
 
            // send message
            Transport.send(message);
 
            // System.out.println("Message sent successfully");
            return code ; 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void transmitEmail(String email) { 
		this.email = email ;
		code = sendCodeToEmail(email);
		timeLine() ; 
		nottificationemail.setText("Please enter the confirmation code sent to your email " + email );
		nottificationemail.setWrapText(true);
		nottificationemail.setAlignment(Pos.CENTER);
	}
}
