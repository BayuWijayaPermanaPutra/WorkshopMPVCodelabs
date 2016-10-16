package id.ac.unikom.codelabs.mvpweatherapp.view;

import java.util.List;

import id.ac.unikom.codelabs.mvpweatherapp.model.Weather;

/**
 * Created by Bayu WPP on 10/14/2016.
 */

public interface ViewMainSet {
    void showErrorMessage();
    void loadWeathers(List<Weather> weathers);
    void showProgress();
    void hideProgress();
}
