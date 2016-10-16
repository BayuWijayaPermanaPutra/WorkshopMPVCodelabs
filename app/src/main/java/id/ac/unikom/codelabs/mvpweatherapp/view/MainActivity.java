package id.ac.unikom.codelabs.mvpweatherapp.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import id.ac.unikom.codelabs.mvpweatherapp.R;
import id.ac.unikom.codelabs.mvpweatherapp.adapter.WeatherAdapter;
import id.ac.unikom.codelabs.mvpweatherapp.model.Weather;
import id.ac.unikom.codelabs.mvpweatherapp.model.service.WeatherApi;
import id.ac.unikom.codelabs.mvpweatherapp.model.service.WeatherApiImpl;
import id.ac.unikom.codelabs.mvpweatherapp.presenter.WeatherPresenterImpl;

public class MainActivity extends AppCompatActivity implements ViewMainSet,WeatherAdapter.WeatherItemListener {
    private WeatherPresenterImpl presenter;
    private RecyclerView rvWeather;
    private SwipeRefreshLayout swipeWeather;
    private WeatherAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        presenter = new WeatherPresenterImpl(this, new WeatherApiImpl());

        adapter = new WeatherAdapter(this,this);
        rvWeather.setLayoutManager(new LinearLayoutManager(this));
        rvWeather.setAdapter(adapter);

        presenter.loadWeather();
        swipeWeather.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadWeather();
            }
        });
    }

    private void initView() {
        rvWeather = (RecyclerView) findViewById(R.id.recyclerview_main);
        swipeWeather = (SwipeRefreshLayout) findViewById(R.id.swipe_main);
    }

    @Override
    public void onWeatherItemClick(Weather item) {
        Toast.makeText(this, "Kota "+item.getCityName()
                +", temperaturnya "
                +item.getTemperature(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this, "Failed get Weather", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadWeathers(List<Weather> weathers) {
        adapter.replaceData(weathers);
    }

    @Override
    public void showProgress() {
        if(!swipeWeather.isRefreshing()){
            swipeWeather.post(new Runnable() {
                @Override
                public void run() {
                    swipeWeather.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideProgress() {
        if (swipeWeather.isRefreshing()){
            swipeWeather.setRefreshing(false);
        }
    }
}
