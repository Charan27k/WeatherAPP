package com.mycompany.weather.controller;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.mycompany.weather.model.*;

@Controller
public class HomeController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "home";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(@RequestParam(name="userName", required=true) String cityName, Model model) {
		System.out.println("User Page Requested");
		
		WeatherDetails weatherDetails;
		//consumes the service
		try {
		RestTemplate restTemplate = new RestTemplate();
        weatherDetails = restTemplate.getForObject(
					"http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&APPID=9e19f55fa0eef478381b1212a7756983", WeatherDetails.class);
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", "city not found or connection problem - Try again");
    		return "home";
		}
       
       
        StringBuilder overallWeather = new 
                StringBuilder(); 
        for(Weather weather:weatherDetails.getWeather())
        {
        	overallWeather.append(weather.getDescription()+"  ");
        }
        
        
        
        //conversions
        String todayDate=new SimpleDateFormat("YYYY-MMM-dd z").format(new Date(weatherDetails.getDt()*1000L));
        String sunRise=new SimpleDateFormat("hh : mm : ss aa z").format(new Date(weatherDetails.getSys().getSunrise()*1000L));
        String sunSet = new SimpleDateFormat("hh : mm :ss aa z").format(new Date(weatherDetails.getSys().getSunset()*1000L));
        long temperatureCelsius=Math.round(weatherDetails.getMain().getTemp()-273.15);//conversion from kelvin to celsius
        long temperatureFahrenheit=Math.round((( weatherDetails.getMain().getTemp() - 273.15) * 9/5) + 32);//conversion from kelvin to Fahrenheit
        
        //adding to model
        model.addAttribute("userName", weatherDetails.getName());
        model.addAttribute("date", todayDate);
        model.addAttribute("overallWeather", overallWeather);
        model.addAttribute("temperatureCelsius", temperatureCelsius);
        model.addAttribute("temperatureFahrenheit", temperatureFahrenheit);
        model.addAttribute("sunRise",sunRise);
        model.addAttribute("sunSet",sunSet);
        System.out.println("cityname"+cityName);
        
        return "user";
	}
}

