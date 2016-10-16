package id.ac.unikom.codelabs.mvpweatherapp.model.service;

import java.util.List;

import id.ac.unikom.codelabs.mvpweatherapp.model.Weather;

/**
 * Created by Bayu WPP on 10/13/2016.
 */

public interface WeatherApi {
    interface WeatherApiCallback<T>{
        void onSuccess(T weathers);
        void onFailure();
    }

    void getWeathers(WeatherApiCallback<List<Weather>> callback);
}
