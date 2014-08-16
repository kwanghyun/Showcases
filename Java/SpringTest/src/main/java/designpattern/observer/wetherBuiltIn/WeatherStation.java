package designpattern.observer.wetherBuiltIn;

import util.LogUtil;

public class WeatherStation {

	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		CurrentConditionsDisplay currentConditions = new CurrentConditionsDisplay(weatherData);
		StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

		weatherData.setMeasurements(80, 65, 30.4f);
		LogUtil.writeline();
		weatherData.setMeasurements(82, 70, 29.2f);
		LogUtil.writeline();
		weatherData.setMeasurements(78, 90, 29.2f);
	}
}
