package id.ac.unikom.codelabs.mvpweatherapp.view;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import id.ac.unikom.codelabs.mvpweatherapp.R;
import id.ac.unikom.codelabs.mvpweatherapp.adapter.WeatherAdapter;
import id.ac.unikom.codelabs.mvpweatherapp.model.Weather;
import id.ac.unikom.codelabs.mvpweatherapp.model.service.WeatherApiImpl;
import id.ac.unikom.codelabs.mvpweatherapp.presenter.ViewMainSet;
import id.ac.unikom.codelabs.mvpweatherapp.presenter.WeatherPresenterImpl;

public class MainActivity extends AppCompatActivity implements ViewMainSet,WeatherAdapter.WeatherItemListener, SearchView.OnQueryTextListener {
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

    @Override
    public void showWeatherClickedMessage(Weather cuaca) {
        Toast.makeText(this, "Kota "+cuaca.getCityName()
                +", dengan temperatur "
                +cuaca.getTemperature(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(presenter.getWeathersList());
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.setFilter(presenter.filter(presenter.getWeathersList(),newText));
        return true;
    }
}
