package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class CalculatorController implements Initializable{
	@FXML
	private Label displayResult;
	
	@FXML
	private Label temp ; 
	
	private String currentInput = "" ; 
	
	private double currentResult = 0;
	
	private String lastOperator = "";
	
	private int countClick = 0 ; 
	
	private int checkOperator = 0 ;
	
	private boolean checkError = false ;
	
	private String tempValue = "" ; 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.displayResult.setText("0");
	}
	
	@FXML
	public void chosenumber(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		String calculator = clickedButton.getText(); 
		if (isnumberstring(calculator)) {
			if (currentInput.isEmpty() || checkError == true) {
				currentInput = "" ; 
				currentInput += calculator ; 
				displayResult.setText(currentInput);
				if (checkError == true ) {
					checkError = false ; 
				}
			} else if (!currentInput.equals("")) {
				currentInput += calculator ; 
				displayResult.setText(currentInput);
			}
		} else if (calculator.equals(".") && !checkcomma(currentInput)) {
			if (checkError == true ) {
				currentInput = "" ; 
				currentInput = currentInput + "0" + calculator ; 
				displayResult.setText(currentInput);
				checkError = false ;
			}
			else if ( currentInput.isEmpty()) {
				currentInput = "" ; 
				currentInput = currentInput + "0" + calculator ; 
				displayResult.setText(currentInput);
			} else if ( !currentInput.isEmpty()) {
				currentInput += calculator ; 
				displayResult.setText(currentInput);
			}
		}
	}
	 
	@FXML
	public void delete(ActionEvent event) {
		if (lastOperator.isEmpty() && !currentInput.isEmpty() && !currentInput.equals("0")) {
			currentInput = deleteLastString(currentInput); 
			displayResult.setText(currentInput); 
			if (currentInput.isEmpty()) {
				displayResult.setText("0");
			}
		} else if (!lastOperator.isEmpty() && !currentInput.isEmpty()) {			
			currentInput = deleteLastString(currentInput);			
			displayResult.setText(currentInput);
		}
	}

    @FXML
    public void choseoperator(ActionEvent event) {
    	Button clickedButton = (Button) event.getSource();
        String operator = clickedButton.getText();
	        
        if (!currentInput.isEmpty() && checkOperator != 0 ) { 
        	double inputValue = Double.parseDouble(currentInput);
        	System.out.println(inputValue);
        	switch(checkOperator) {
        	case 1 : 
        		currentResult = inputValue ; 
        		break;
        	case 2 : 
        		currentResult = -inputValue ;
        		break;
        	case 3 :
        		currentResult = 0 ;
        		break;
        	case 4 :
        		currentResult = 0 ;
        		break;
        	}
        	checkOperator = 0 ; 
        	currentInput = "" ; 
        }
    	if (!currentInput.isEmpty()) {
            double inputValue = Double.parseDouble(currentInput);

            switch (lastOperator) {
                case "":
                    currentResult = inputValue;
                    break;
                case "+":
                    currentResult += inputValue;
                    break;
                case "-":
                    currentResult -= inputValue;
                    break;
                case "*":
                    currentResult *= inputValue;
                    break;
                case "/":
                    if (inputValue != 0) {
                        currentResult /= inputValue;
                    } else {
                    	displayResult.setText("Error");
                    	checkError = true ;
                    	currentResult = 0 ;
                    	currentInput = "" ; 
                    	lastOperator = "" ; 
                    	operator = "" ; 
                    	temp.setText("");
                        return;
                    }
                    break;
                case "^": 
                	currentResult = Math.pow(currentResult, inputValue) ; 
                	break; 
                case "%": 
                	currentResult = currentResult % inputValue ; 
                	break;
            }
            currentInput = "";
            displayResult.setText(String.valueOf(currentResult));
        } else if (currentInput.isEmpty() && countClick == 0) { 
        	switch(operator) {
        	case "+" : 
        		checkOperator = 1 ; 
        		tempValue = "+" ;
        		break;
        	case "-" : 
        		checkOperator = 2 ;
        		tempValue = "-" ;
        		break;
        	case "*" : 
        		checkOperator = 3 ;
        		tempValue = "*" ; 
        		break;
        	case "/" : 
        		checkOperator = 4 ;
        		tempValue = "/" ;
        		break;
        	case "%" : 
        		checkOperator = 5 ; 
        		tempValue = "%" ; 
        	}
        	temp.setText(tempValue);
        	temp.setAlignment(Pos.CENTER);
        }
        lastOperator = operator;
        temp.setText(String.valueOf(currentResult) + " " + lastOperator);
        temp.setAlignment(Pos.CENTER);
        countClick ++ ; 
    }

    @FXML
    public void reset(ActionEvent event) {
    	currentInput = "" ; 
    	 
    	currentResult = 0;
    	
        lastOperator = "";
        
        countClick = 0 ; 
        
        checkOperator = 0 ;
        
        checkError = false ; 
        
        displayResult.setText("0");
        
        temp.setText("");
    }
	    
    @FXML
    public void equal(ActionEvent event) {
    	if (!currentInput.isEmpty() && !lastOperator.isEmpty()) {
            double inputValue = Double.parseDouble(currentInput);

            switch (lastOperator) {
                case "":
                    currentResult = inputValue;
                    break;
                case "+":
                    currentResult += inputValue;
                    break;
                case "-":
                    currentResult -= inputValue;
                    break;
                case "*":
                	currentResult *= inputValue;
                    break;
                case "/":
                    if (inputValue != 0) {
                        currentResult /= inputValue;
                    } else {
                    	displayResult.setText("Error");
                    	checkError = true ;
                    	currentResult = 0 ;
                    	currentInput = ""; 
                    	lastOperator = "" ; 
                    	temp.setText("");
                        return;
                    }
                    break;
                case "^": 
                	currentResult = Math.pow(currentResult, inputValue) ; 
                	break; 
                case "%": 
                	currentResult = currentResult % inputValue ; 
                	break;
            }
            currentInput = "";
            displayResult.setText(String.valueOf(currentResult));
            temp.setText(String.valueOf(currentResult));
            temp.setAlignment(Pos.CENTER);
            lastOperator = "" ; 
    	} else if (!currentInput.isEmpty() && lastOperator.isEmpty()) {
    		currentResult = Double.parseDouble(currentInput); 
    		displayResult.setText(String.valueOf(currentResult));
    		temp.setText(String.valueOf(currentResult));
    		temp.setAlignment(Pos.CENTER);
    		currentInput = "" ; 
    	}
    }
	    
    public String deleteLastString(String value) {
    	if(value.isEmpty()) {
    		return value ; 
    	} 
    	return value.substring(0 , value.length() - 1 );
    }
    
    public boolean isnumberstring(String s) {
    	char c = s.charAt(0);
    	if (Character.isDigit(c) && c >= '0' && c <= '9') {
    		return true ; 
    	} else {
    		return false ; 
    	}
    }
    
    public boolean checkcomma(String currentInput) {
    	for (int i = 0 ; i < currentInput.length() ; i ++ ) {
    		if (currentInput.charAt(i) == '.') {
    			return true ; 
    		}
    	}
    	return false ; 
    }
    
}
