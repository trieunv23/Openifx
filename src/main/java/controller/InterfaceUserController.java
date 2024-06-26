package controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

import javafx.util.Duration;

import java.util.Date;
import java.util.ResourceBundle;

import Entity.User;
import dao.UserDao;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// import Weather 
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStreamReader;

public class InterfaceUserController implements Initializable , Role<User>{
	@FXML
    private AnchorPane anchorPane;

	@FXML
	private Label fullName ;
	
	@FXML
	private Label asset ;
	
	@FXML
	private Label clockLabel;
	
	@FXML
	private Button google ; 
	
	@FXML
	private Button sudoku ; 
	
	@FXML 
	private Label week ; 
	
	@FXML
	private Label date ; 
	
	@FXML
	private Label session ;
	
	@FXML
	private Circle avata ; 
	
	private User user ; 
	
	private Image image ; 
	
	// weather 
	
	@FXML 
	private Label city ; 
	
	@FXML 
	private Label temperature ; 
	
	@FXML 
	private Label weathertype ; 
	
	@FXML 
	private Label lhumidity ; 
	
	@FXML
	private Label lwindspeed ;
	
	@FXML 
	private ImageView weathertypeig; 
	
	private String iconWeather ;
	
	@Override
	public void setRoleUser(User user) {
		this.user = user ; 
		this.fullName.setText(user.getFullName());
		this.fullName.setAlignment(Pos.CENTER);
		this.asset.setText(String.valueOf(user.getAsset()));
		
		if (UserDao.getInstance().getImageFromDatabase(user) != null ) {
			Image image = UserDao.getInstance().getImageFromDatabase(user);
			avata.setFill(new ImagePattern(image));
		}
		
		image = UserDao.getInstance().getImageFromDatabase(user); 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (image == null ) {
			Image image = new Image(getClass().getResourceAsStream("/Images/avata.png"));
			avata.setFill(new ImagePattern(image));
		}
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateClock));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        displayTime();
        
        this.city.setText(CITY_NAME);
        updateWeather(weathertype, temperature ,lhumidity, lwindspeed);
	}
	
	@FXML
	public void myInfor(ActionEvent event ) {
		try {
			URL url = getClass().getResource("/view/InforUser.fxml") ; 
		    FXMLLoader loader = new FXMLLoader(url);
		    Parent root = loader.load();

		    InforUserController iuc = loader.getController();
		    iuc.setRoleUser(user);

		    Stage window = new Stage () ;
		    window.getIcons().add(new Image("/images/inforUser.png"));
		    Scene scene = new Scene(root, 632, 561);
		    window.setTitle("Information");
		    window.setScene(scene);
		    window.centerOnScreen();
		    window.show();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	@FXML
	public void backLogIn(ActionEvent event) throws IOException {
		URL url = getClass().getResource("/view/Log_In.fxml") ; 
		FXMLLoader loader = new FXMLLoader(url); 
		Parent root = loader.load();
		
		Dialog<Boolean> dialog = new Dialog<>();
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/images/logo2.png"));
		dialog.setTitle("Confirm");
		dialog.setHeaderText("Do you want to log out ?");
		
		ButtonType btok = new ButtonType("OK" , ButtonBar.ButtonData.OK_DONE); 
		ButtonType btcancel = new ButtonType("Cancel" , ButtonBar.ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(btok , btcancel );
		dialog.setResultConverter(choice -> {
			if (choice == btok) {
				Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
				window.getIcons().add(new Image("/images/logo2.png"));
				Scene scene = new Scene (root , 550 , 400 ) ; 
				window.setScene(scene);
				window.setTitle("OPNENIFX");
				window.centerOnScreen();
				window.show();
				return true ; 
			} else {
				return false ;
			}
		});
		dialog.showAndWait();
	}
	
	@FXML
	public void changeAvata(ActionEvent event ) {
		try {
			URL url = getClass().getResource("/view/images.fxml") ; 
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = loader.load();
			
			imagesController ic = loader.getController(); 
			ic.setRoleUser(user);
			ic.setParentController(this); // truyền tham chiếu lớp hiện tại (InterfaceUserController) sang lớp imagesController
			
			Stage window = new Stage() ;
			window.initModality(Modality.APPLICATION_MODAL);
			window.getIcons().add(new Image("/images/avataUser.png"));
			Scene scene = new Scene (root , 724 , 477 ) ;
			window.setScene(scene);
			window.setTitle("Change Avata");
			window.centerOnScreen();
			window.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void resetInterface(ActionEvent event ) {
		displayTime();
		updateWeather(weathertype, temperature ,lhumidity, lwindspeed);
		if (UserDao.getInstance().getImageFromDatabase(user) != null) {
			updateAvataImage(image);
		}
		
	}
	
	public void updateAvataImage(Image newImage) {
		this.image = newImage ; 
	    avata.setFill(new ImagePattern(this.image));
	}
	
	@FXML
	public void searchWeather(ActionEvent event) {
		try {
			URL url = getClass().getResource("/view/Weather.fxml") ; 
			FXMLLoader loader = new FXMLLoader(url);
		    Parent root = loader.load();
			
			Stage window = new Stage () ; 
			window.getIcons().add(new Image("/images/weatherIcon.png"));
			Scene scene = new Scene (root , 652, 385 ) ; 
			window.setScene(scene);
			window.setTitle("Weather") ; 
			window.centerOnScreen() ; 
			window.show() ;
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@FXML
	public void calculator(ActionEvent event) throws IOException {
		URL url = getClass().getResource("/view/Calculator.fxml");
		FXMLLoader loader = new FXMLLoader(url) ; 
		Parent root = loader.load();
		Scene scene = new Scene(root , 780 , 365) ; 
		Stage window = new Stage() ; 
		window.getIcons().add(new Image("/images/Calculator.png"));
		window.setTitle("Calculator"); 
		window.setScene(scene);
		window.centerOnScreen();
		window.show();
	}
	
	@FXML
	public void message(ActionEvent event) throws IOException {
		URL url = getClass().getResource("/view/Message.fxml");
		FXMLLoader loader = new FXMLLoader(url) ;
		Parent root = loader.load();
		MessageController mc = loader.getController();
		mc.setRoleUser(this.user);
		Scene scene = new Scene(root , 670 , 437); 
		Stage window = new Stage() ;
		window.setScene(scene);
		window.getIcons().add(new Image("/images/message.png"));
		window.setTitle("Message");
		window.centerOnScreen();
		window.show();
	}
	
	public void sudoku(ActionEvent event) {
		Stage primaryStage = new Stage() ;
		primaryStage.getIcons().add(new Image("/images/sudokugame.png"));
		SudokuApp sa = new SudokuApp() ;
		sa.start(primaryStage);
	}
	
	public void updateClock(ActionEvent event) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        clockLabel.setText(dateFormat.format(now));
        clockLabel.setAlignment(Pos.CENTER);   
	}
	
	@FXML
	public void google(ActionEvent event ) {
		try {
			URL url = getClass().getResource("/view/Google.fxml") ; 
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = loader.load();
			
			Scene scene2 = new Scene (root , 850 , 580 ) ; 
			Stage window = new Stage (); 
			window.getIcons().add(new Image("/images/google.png"));
			window.setScene(scene2);
			window.setTitle("Web Browser");
			window.centerOnScreen();
			window.show();
		}catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	public String getWeek() {
		DayOfWeek dayOfWeek ; 
		LocalDateTime ngayHienTai = LocalDateTime.now();
		dayOfWeek = ngayHienTai.getDayOfWeek() ;
		return dayOfWeek.toString() ; 
	}
	
	public String getDate() {
		LocalDateTime timeNow = LocalDateTime.now();  
		String formattedDate  =  timeNow.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return formattedDate ; 
	}
	
	public String getSession() {
		String s = "" ; 
		LocalDateTime timeNow = LocalDateTime.now();  
		int hour = timeNow.getHour() ; 
		if (hour >= 0 && hour < 12 ) {
			s = "Morning" ; 
		}else if (hour >= 12 && hour < 18 ) {
			s = "Afternoon" ; 
		}else if (hour >= 18 && hour < 24 ) {
			s = "Evening" ; 
		}
		return s ; 
	}
	
	public void displayTime() {
		week.setText(getWeek());
        date.setText(getDate());
        date.setAlignment(Pos.CENTER);
        session.setText(getSession());
	}
	
	// Weather
	private static final String API_KEY = "4246ab7040273fc9c4665b44b894853d";
	private static final String CITY_NAME = "Danang";
	
	private void updateWeather(Label weatherLabel, Label temperatureLabel , Label lhumidity , Label lwindSpeed ) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://api.openweathermap.org/data/2.5/weather?q=" +
                    CITY_NAME + "&appid=" + API_KEY);

            HttpResponse response = httpClient.execute(httpGet);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder responseStringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                responseStringBuilder.append(line);
            }

            JsonObject jsonObject = JsonParser.parseString(responseStringBuilder.toString()).getAsJsonObject();
            
            // lấy tên thời tiết (Description)
            String weatherDescription = jsonObject.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("description").getAsString();
            
            //lấy icon thời tiết (gồm cả ngày và đêm),  vd : 01d -> "clear sky"   
            String weatherIcon = jsonObject.getAsJsonArray("weather")
                    .get(0).getAsJsonObject().get("icon").getAsString();
            
            double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
            double temperatureCelsius = temperatureKelvin - 273.15; // Chuyển đổi từ Kelvin sang Celsius
            double humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsDouble();
            double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();

            lhumidity.setText(String.valueOf(humidity) + " %");
            lwindSpeed.setText(String.valueOf(windSpeed)+ " m/s");
            
            weatherLabel.setText(weatherDescription);
            weatherLabel.setAlignment(Pos.CENTER);
            temperatureLabel.setText(String.format("%.2f", temperatureCelsius) + " °C");
            
            this.iconWeather = weatherIcon ; 
            
        } catch (Exception e) {
            // e.printStackTrace();
            weatherLabel.setText("Unable to fetch weather data.");
            temperatureLabel.setText("");
        }
        
        String[] igWeatherType = {
        		"/Images/weather01d.png" , // 0
        		"/Images/weather01n.png" , // 1
        		"/Images/weather02d.png" , // 2
        		"/Images/weather02n.png" , // 3 
        		"/Images/weather0304.png" , // 4
        		"/Images/weather09.png" , // 5
        		"/Images/weather10d.png" , // 6 
        		"/Images/weather10n.png" , // 7
        		"/Images/weather11d.png" , // 8 
        		"/Images/weather11n.png" , // 9
        		"/Images/weather13d.png" , // 10 
        		"/Images/weather13n.png" , // 11 
        		"/Images/weather50.png" // 12
        		}; 
        
        	String imaPart = null ;
        if (iconWeather.equals("01d")) {
        	imaPart = igWeatherType[0] ; 
        } else if (iconWeather.equals("01n")) {
        	imaPart = igWeatherType[1] ; 
        } else if (iconWeather.equals("02d")) {
        	imaPart = igWeatherType[2] ;
        } else if (iconWeather.equals("02n")) {
        	imaPart = igWeatherType[3] ;
        } else if (iconWeather.equals("03n") || iconWeather.equals("03d") || iconWeather.equals("04n") || iconWeather.equals("04d")) {
        	imaPart = igWeatherType[4] ;
        } else if (iconWeather.equals("09d") || iconWeather.equals("09n")) {
        	imaPart = igWeatherType[5] ;
        } else if (iconWeather.equals("10d")) {
        	imaPart = igWeatherType[6] ;
        } else if (iconWeather.equals("10n")) {
        	imaPart = igWeatherType[7] ;
        } else if (iconWeather.equals("11d")) {
        	imaPart = igWeatherType[8] ;
        } else if (iconWeather.equals("11n")) {
        	imaPart = igWeatherType[9] ;
        } else if (iconWeather.equals("13d")) {
        	imaPart = igWeatherType[10] ;
        } else if (iconWeather.equals("13n")) {
        	imaPart = igWeatherType[11] ;
        }else if (iconWeather.equals("50d")|| iconWeather.equals("50n")) {
        	imaPart = igWeatherType[12] ;
        }
        
        Image imagePart = new Image(getClass().getResourceAsStream(imaPart));
        
        this.weathertypeig.setImage(imagePart);
    }
	
}
