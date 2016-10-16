package id.ac.unikom.codelabs.mvpweatherapp.presenter;

import java.util.List;

import id.ac.unikom.codelabs.mvpweatherapp.model.Weather;

/**
 * Created by Bayu WPP on 10/14/2016.
 */

public interface ViewMainSet {
    void showErrorMessage();
    void showProgress();
    void hideProgress();
    void showWeatherClickedMessage(Weather cuaca);
    void loadWeathers(List<Weather> weathers);
}
