package id.ac.unikom.codelabs.mvpweatherapp.presenter;

import java.util.List;

import id.ac.unikom.codelabs.mvpweatherapp.model.Weather;
import id.ac.unikom.codelabs.mvpweatherapp.model.service.WeatherApi;
import id.ac.unikom.codelabs.mvpweatherapp.model.service.WeatherApiImpl;
import id.ac.unikom.codelabs.mvpweatherapp.view.ViewMainSet;

/**
 * Created by Bayu WPP on 10/13/2016.
 */

public class WeatherPresenterImpl extends BasePresenter implements WeatherPresenter{
    private final ViewMainSet mViewMain;
    private final WeatherApiImpl weatherApi;

    public WeatherPresenterImpl(ViewMainSet mViewMain, WeatherApiImpl weatherApi) {
        this.mViewMain = mViewMain;
        this.weatherApi = weatherApi;
    }

    @Override
    public void loadWeather() {
        mViewMain.showProgress();
        weatherApi.getWeathers(new WeatherApi.WeatherApiCallback<List<Weather>>() {
            @Override
            public void onSuccess(List<Weather> weathers) {
                mViewMain.hideProgress();
                mViewMain.loadWeathers(weathers);
            }

            @Override
            public void onFailure() {
                mViewMain.hideProgress();
                mViewMain.showErrorMessage();
            }
        });
    }
}
