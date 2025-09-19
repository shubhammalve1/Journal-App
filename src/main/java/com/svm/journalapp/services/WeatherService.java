package com.svm.journalapp.services;

import com.svm.journalapp.api.response.WeatherResponse;
import com.svm.journalapp.cache.AppCache;
import com.svm.journalapp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static  int cnt1 = 0, cnt2 = 0;

    @Value("${weather.api.key}")
    private String apiKey;

    //private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeatherDetails(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null) {
            ++cnt1;
            return weatherResponse;
        } else {
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.CITY, city).replace(PlaceHolders.API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null) {
                ++cnt2;
                redisService.set("weather_of_" + city, body, 300L);
            }

            System.out.println("cnt1 : " + cnt1 + " and cnt2 : " + cnt2);
            return body;
        }

//        HttpHeaders header = new HttpHeaders();
//        header.set("Key", "Value");
//        User user = User.builder().username("Shubham").password("5126889").build();
//        HttpEntity<User> httpEntity = new HttpEntity<>(user, header);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST,httpEntity, WeatherResponse.class);
    }
}
