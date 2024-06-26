module Openifx {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.web;
	requires com.google.gson;
	requires org.apache.httpcomponents.httpcore;
	requires org.apache.httpcomponents.httpclient;
	requires mysql.connector.java;
	requires javax.mail.api;
	requires commons.validator;
	requires org.json;
	// requires mysql.connector.java;
	
	opens application to javafx.graphics, javafx.fxml , javafx.base ;
	opens controller to javafx.graphics, javafx.fxml , javafx.base; 
	opens Entity to javafx.graphics, javafx.fxml , javafx.base; 
	opens WeatherApp to javafx.graphics, javafx.fxml , javafx.base ;
}