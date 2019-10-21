package br.com.bossini.usjt_ccp3an_bua_weather_forecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Weather {
    public final String dayOfWeek;

    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String description;
    public final String iconURL;

    public Weather (long dt, double minTemp, double maxTemp,
                    double humidity, String description,
                    String iconName){
        this.dayOfWeek = convert(dt);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        this.minTemp = nf.format(minTemp) + "\u00b0C";
        this.maxTemp = nf.format(maxTemp) + "\u00b0C";
        NumberFormat pf = NumberFormat.getPercentInstance();
        this.humidity = pf.format(humidity / 100);
        this.description = description;
        this.iconURL = "http://openweathermap.org/img/w/" + iconName + ".png";
    }

    public String convert (long dt){
        Calendar agora = Calendar.getInstance();
        agora.setTimeInMillis(dt * 1000);
        SimpleDateFormat sdf =
                new SimpleDateFormat("EEE HH:mm");
        return sdf.format(agora.getTime());
    }
}

class WeatherViewHolder extends RecyclerView.ViewHolder{
    public ImageView conditionImageView;
    public TextView dayTextView;
    public TextView lowTextView;
    public TextView highTextView;
    public TextView humidityTextView;

    public WeatherViewHolder (View raiz){
        super(raiz);
        this.conditionImageView =
                raiz.findViewById(R.id.conditionImageView);
        this.dayTextView =
                raiz.findViewById(R.id.dayTextView);
        this.lowTextView =
                raiz.findViewById(R.id.lowTextView);
        this.highTextView =
                raiz.findViewById(R.id.highTextView);
        this.humidityTextView =
                raiz.findViewById(R.id.humidityTextView);
    }
}

class WeatherAdapter
        extends RecyclerView.Adapter <WeatherViewHolder>{

    private Context context;
    private List<Weather> previsoes;

    public WeatherAdapter(Context context, List<Weather> previsoes) {
        this.context = context;
        this.previsoes = previsoes;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(context);
        View raiz = inflater.inflate(
                R.layout.list_item,
                parent,
                false
        );
        return new WeatherViewHolder(raiz);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Weather w = previsoes.get(position);
        holder.lowTextView.setText(
            context.getString(
                    R.string.low_temp,
                    w.minTemp
            )
        );
        holder.highTextView.setText(
                context.getString(
                        R.string.high_temp,
                        w.maxTemp
                )
        );
        holder.humidityTextView.setText(
                context.getString(
                        R.string.day_description,
                        w.dayOfWeek,
                        w.description
                )
        );
    }

    @Override
    public int getItemCount() {
        return previsoes.size();
    }
}