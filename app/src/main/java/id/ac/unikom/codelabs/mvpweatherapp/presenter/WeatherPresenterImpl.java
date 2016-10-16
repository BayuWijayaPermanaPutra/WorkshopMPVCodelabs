package id.ac.unikom.codelabs.mvpweatherapp.presenter;

import java.util.ArrayList;
import java.util.List;

import id.ac.unikom.codelabs.mvpweatherapp.model.Weather;
import id.ac.unikom.codelabs.mvpweatherapp.model.service.WeatherApi;
import id.ac.unikom.codelabs.mvpweatherapp.model.service.WeatherApiImpl;

/**
 * Created by Bayu WPP on 10/13/2016.
 */

public class WeatherPresenterImpl extends BasePresenter implements WeatherPresenter{
    private final ViewMainSet mViewMain;
    private final WeatherApiImpl weatherApi;
    private List<Weather> weatherList;

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
                weatherList = weathers;
            }

            @Override
            public void onFailure() {
                mViewMain.hideProgress();
                mViewMain.showErrorMessage();
            }
        });
    }

    public List<Weather> getWeathersList(){
        if (weatherList != null){
            return weatherList;
        }
        return null;
    }

    @Override
    public void itemClick(Weather cuaca) {
        mViewMain.showWeatherClickedMessage(cuaca);
    }

    @Override
    public List<Weather> filter(List<Weather> models, String query) {
        query = query.toLowerCase();

        final List<Weather> filteredModelList = new ArrayList<>();
        for (Weather model : models) {
            final String text = model.getCityName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
