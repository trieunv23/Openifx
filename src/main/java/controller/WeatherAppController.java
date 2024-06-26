package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class WeatherAppController implements Initializable { 
    
    @FXML 
    Label temprerature ; 
    
    @FXML 
    Label humudity ; 
    
    @FXML
    Label windspeed ; 
    
    @FXML 
    Label typeweather; 
    
    @FXML 
    Label resultCity; 
    
    @FXML 
    ImageView imageWeathernow ;  
    
    @FXML
    TextField city ; 
    
    @FXML 
    Label lat ; 
    
    @FXML 
    Label lon ; 
    
    @FXML
    Label visibility; 
    
    String iconWeather ; 
    
    @FXML
    Label notification ; 
    
    private static final String API_KEY = "4246ab7040273fc9c4665b44b894853d";
    
    private void updateWeather(String nameCity) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://api.openweathermap.org/data/2.5/weather?q=" +
                    nameCity + "&appid=" + API_KEY);

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
            
            double latitude = jsonObject.getAsJsonObject("coord").get("lat").getAsDouble();
            double longitude = jsonObject.getAsJsonObject("coord").get("lon").getAsDouble();
           
            int visibility = jsonObject.get("visibility").getAsInt();
            
            this.humudity.setText(String.valueOf(humidity) + " %");
            this.windspeed.setText(String.valueOf(windSpeed)+ " m/s");
            
            this.typeweather.setText(weatherDescription);
            this.typeweather.setAlignment(Pos.CENTER);
            this.temprerature.setText(String.format("%.2f", temperatureCelsius) + " °C");
            
            this.iconWeather = weatherIcon ;
            
            System.out.println(weatherIcon);
            
            this.lat.setText(String.valueOf(latitude)) ; 
            this.lon.setText(String.valueOf(longitude));
            
            this.visibility.setText(String.valueOf(visibility / 1000 ) + " Km");
            
            this.imageWeathernow.setImage(selectImageTypeWeather(weatherIcon));
            
        } catch (Exception e) {
            // e.printStackTrace();
            this.temprerature.setText("");
            this.lat.setText("0") ;
            this.lon.setText("0");
            this.temprerature.setText("0 °C"); 
            this.humudity.setText("0 %") ; 
            this.windspeed.setText("0 m/s ") ; 
            this.typeweather.setText("");
            this.visibility.setText("0 Km");
            this.imageWeathernow.setImage(null);
            this.resultCity.setText("");
            this.notification.setText(this.city.getText() + " city does not exist !! , Try again") ; 
            this.notification.setAlignment(Pos.CENTER) ; 
        }
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.city.setText("Danang");
		this.resultCity.setText("Danang");
		this.resultCity.setAlignment(Pos.CENTER);
		updateWeather(this.city.getText());
		
		this.city.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				notification.setText("");
			}
			
		});
	}
		
	@FXML
	public void search (ActionEvent event) throws InterruptedException {
		String nameCity = this.city.getText(); 
		this.resultCity.setText(nameCity);
		
		Thread.sleep(1000);
		
		updateWeather(nameCity);
	}
	
	public Image selectImageTypeWeather(String iconWeather) {
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
        
       return imagePart ;
	}
}
