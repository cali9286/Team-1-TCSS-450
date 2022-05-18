package edu.uw.tcss450.ckald.team1tcss450.ui.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.uw.tcss450.ckald.team1tcss450.R;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {


    private Context context;
    private ArrayList<WeatherRecycle> weatherRecycleArrayList;



    public WeatherAdapter(Context context, ArrayList<WeatherRecycle> weatherRecycleArrayList) {
        this.context = context;
        this.weatherRecycleArrayList = weatherRecycleArrayList;
    }



    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.weather_rv, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        //create adapter
        WeatherRecycle adapter = weatherRecycleArrayList.get(position);

        //get variables to put into our RV
        holder.tempTV.setText(adapter.getTemp() + "°F");
        //Picasso.get().load("https://cdn.weatherapi.com/weather/64x64/night/113.png").into(holder.conditionIV);
        Picasso.get().load("https:".concat(weatherRecycleArrayList.get(position).getIcon())).into(holder.conditionIV);

        //change the format of our time that we get.
        SimpleDateFormat in = new SimpleDateFormat("YYYY-MM-DD HH:MM");
        SimpleDateFormat out = new SimpleDateFormat("HH:MM aa");
        try {
            Date t = in.parse(adapter.getTime());
            holder.timeTV.setText(out.format(t));
        } catch (ParseException e){
            e.printStackTrace();
        }


        holder.timeTV.setText(adapter.getTime());
    }

    @Override
    public int getItemCount() {
        return weatherRecycleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tempTV, timeTV;
        private ImageView conditionIV;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tempTV = itemView.findViewById(R.id.idTVTemp);
            timeTV = itemView.findViewById(R.id.idTVTime);
            conditionIV = itemView.findViewById(R.id.idIVCondition);
        }
    }
}
