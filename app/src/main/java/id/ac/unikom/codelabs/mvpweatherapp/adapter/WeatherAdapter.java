package id.ac.unikom.codelabs.mvpweatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.ac.unikom.codelabs.mvpweatherapp.R;
import id.ac.unikom.codelabs.mvpweatherapp.model.Weather;
import id.ac.unikom.codelabs.mvpweatherapp.utility.MathUtility;

/**
 * Created by Bayu WPP on 10/13/2016.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final Context context;
    private final WeatherItemListener mListener;
    private List<Weather> weathers = new ArrayList<>();
    public WeatherAdapter(Context context, WeatherItemListener mListener) {
        this.context = context;
        this.mListener = mListener;
    }

    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_main, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.WeatherViewHolder holder, int position) {
        holder.mTvCity.setText(weathers.get(position).getCityName());
        holder.mTvTemp.setText(MathUtility.getNoDecimal(weathers.get(position).getTemperature()) + "\u00B0C");
        holder.mTvWindSpeed.setText("Angin "+ MathUtility.getNoDecimal(weathers.get(position).getWindSpeed()) +" m/s");
        holder.mTvWeatherDesc.setText(weathers.get(position).getWeatherDescription());
    }

    public void replaceData(List<Weather> weathersList){
        weathers = weathersList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    protected class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTvTemp;
        private final TextView mTvCity;
        private final TextView mTvWeatherDesc;
        private final TextView mTvWindSpeed;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            mTvCity = (TextView) itemView.findViewById(R.id.tv_city);
            mTvWeatherDesc = (TextView) itemView.findViewById(R.id.tv_weather_desc);
            mTvWindSpeed = (TextView) itemView.findViewById(R.id.tv_wind_speed);
        }

        @Override
        public void onClick(View v) {
            Weather weather = weathers.get(getAdapterPosition());
            mListener.onWeatherItemClick(weather);
        }
    }

    public interface WeatherItemListener {
        void onWeatherItemClick(Weather item);
    }

}
