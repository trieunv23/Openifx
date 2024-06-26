package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GoogleController implements Initializable{
	@FXML
	private WebView webView;
	@FXML
	private TextField adressBar;
	
	private WebEngine webEngine ; 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		webEngine = webView.getEngine();
		webEngine.load("https://www.google.com/");
		adressBar.setText(webEngine.getLocation());
		webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            // Cập nhật giá trị TextField khi chuyển trang
			adressBar.setText(newValue);
        });

	}
	
	public void keyHandle (KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			String address = adressBar.getText(); 
			webEngine.load(address);
		}
	}
	
	public void goBack() {
        if (webEngine.getHistory().getCurrentIndex() > 0) {
            webEngine.getHistory().go(-1);
        }
    }
	
	 public void goForward() {
	        if (webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1) {
	            webEngine.getHistory().go(1);
	        }
	    }


}
