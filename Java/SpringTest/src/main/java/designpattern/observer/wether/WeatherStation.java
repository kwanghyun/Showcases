package designpattern.observer.wether;

import java.util.*;

import util.LogUtil;

// 2. Observer - defines a one-to-many dependency between objects so that
// when one object changes state, all its dependents are notified and
// updated automatically.
public class WeatherStation {

	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();

		CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(
				weatherData);
		StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

		weatherData.setMeasurements(80, 65, 30.4f);
		LogUtil.writeline('-', 50);
		weatherData.setMeasurements(82, 70, 29.2f);
		LogUtil.writeline('-', 50);
		weatherData.setMeasurements(78, 90, 29.2f);
	}
}
