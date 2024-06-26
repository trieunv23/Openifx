package WeatherApp;

public class ObjectWeather {
	String dayOfWeak; 
	String temprerature ; 
	String typeWeather ;
	String iconWeather ;
	
	public ObjectWeather(String dayOfWeak, String temprerature, String typeWeather , String iconWeather) {
		this.dayOfWeak = dayOfWeak;
		this.temprerature = temprerature;
		this.typeWeather = typeWeather;
		this.iconWeather = iconWeather ; 
	}

	public ObjectWeather() {
		super();
	}

	public String getDayOfWeak() {
		return dayOfWeak;
	}

	public void setDayOfWeak(String dayOfWeak) {
		this.dayOfWeak = dayOfWeak;
	}

	public String getTemprerature() {
		return temprerature;
	}

	public void setTemprerature(String temprerature) {
		this.temprerature = temprerature;
	}

	public String getTypeWeather() {
		return typeWeather;
	}

	public void setTypeWeather(String typeWeather) {
		this.typeWeather = typeWeather;
	}
	
	public String getIconWeather() {
		return iconWeather;
	}

	public void setIconWeather(String iconWeather) {
		this.iconWeather = iconWeather;
	}

	@Override
	public String toString() {
		return "ObjectWeather [dayOfWeak=" + dayOfWeak + ", temprerature=" + temprerature + ", typeWeather="
				+ typeWeather + "iconweather=" + iconWeather + "]";
	} 
	
	
}
