package edu.uw.tcss450.ckald.team1tcss450.ui.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.uw.tcss450.ckald.team1tcss450.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {


    //******************
    //context may be null here
    private Context mcontext = this.getContext();
    private RelativeLayout homeRL;
    private ProgressBar loadingPB;
    private TextView cityNameTV, tempTV, conditionTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEDT;
    private ImageView backIV, IconIV, searchIV;

    private ArrayList<WeatherRecycle> weatherRecycleArrayList;
    private WeatherAdapter RVadapter;

    //get location of user.
    private LocationManager locationManager;
    private int PermissionCode = 1;

    private String cityName;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //fill the whole screen.
        // this line (84) buggy doesn't like the bottom nav graph
        //*******************************
        //getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        homeRL = v.findViewById(R.id.idRLHome);
        loadingPB = v.findViewById(R.id.idPBLoading);
        cityNameTV = v.findViewById(R.id.idTVCityName);
        tempTV = v.findViewById(R.id.idTVTemp);
        conditionTV = v.findViewById(R.id.idTVCondition);
        weatherRV = v.findViewById(R.id.idRVWeather);
        cityEDT = v.findViewById(R.id.idEditCity);
        backIV = v.findViewById(R.id.idIVBack);
        IconIV = v.findViewById(R.id.idIVIcon);
        searchIV = v.findViewById(R.id.idIVSearch);

        weatherRecycleArrayList = new ArrayList<>();
        RVadapter = new WeatherAdapter(mcontext, weatherRecycleArrayList);
        weatherRV.setAdapter(RVadapter);

        //locationManager = (LocationManager) mcontext.getSystemService(Context.LOCATION_SERVICE);


        /*
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},PermissionCode);
        } else {
            Log.d("DB","Got to else");
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        cityName = getCityName(location.getLongitude(), location.getLatitude());
        */
        cityName = "Tacoma";
        getWeatherInfo(cityName);

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = cityEDT.getText().toString();
                if(city.isEmpty()) {
                    Toast.makeText(mcontext, "Enter City Name", Toast.LENGTH_SHORT).show();
                } else {
                    cityNameTV.setText(cityName);
                    getWeatherInfo(city);
                }
            }
        });
        return v;
    }


    /*
    //may have bugs to work out here. technically deprecated ********************
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Please Provide Permissions", Toast.LENGTH_SHORT).show();
                //close activity below. but make work for fragment not activity.
                //finish(); ********************
            }
        }
    }
    */

    private String getCityName(double lon, double lat) {
        //set to "error" so as not to set yet
        String cityName = "";

        //********************
        //Anywhere we use context or getActivity = buggy
        //*********************
        Geocoder g = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
        try {
            List<Address> addressList = g.getFromLocation(lat,lon,10);

            for(Address a : addressList) {
                if(a!=null) {
                    String city = a.getLocality();
                    if(city != null && !city.equals("")) {
                        cityName = city;
                    } else {
                        Log.d("TAG", "City Not Found");
                        Toast.makeText(mcontext, "User City Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    private void getWeatherInfo(String CityName) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=95203d195a4b434780402424221205&q="+ CityName +"&days=1&aqi=no&alerts=no";
        cityNameTV.setText(cityName);
        RequestQueue RQ = Volley.newRequestQueue(mcontext);

        JsonObjectRequest JsonOR = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //remove progress bar and add the home page we made
                loadingPB.setVisibility(View.GONE);
                homeRL.setVisibility(View.VISIBLE);

                //clear the everything that is inside the arraylist to put new data in.
                weatherRecycleArrayList.clear();

                //set current temp to our TextView for temp, am/pm, current condition, forecast
                try {
                    String currentTemp = response.getJSONObject("current").getString("temp_f");
                    tempTV.setText(currentTemp.concat("°F"));
                    int dayTime = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    //load the icon with Picasso
                    //Picasso.get().load("https:"+conditionIcon).into(IconIV);
                    //*********************
                    //maybe try the .concat
                    Picasso.get().load("https:".concat(conditionIcon)).into(IconIV);
                    //****************
                    conditionTV.setText(condition);

                    //**********************app/src/main/res/drawable-normal/daytimebackground.jpg
                    //load night or day depending on the response we get.
                    //the file path may need to be modified. usually uses url.
                    //********************
                    if(dayTime == 1) {

                        Picasso.get().load("app/src/main/res/drawable-normal/daytimebackground.jpg").into(backIV);
                    } else {
                        Picasso.get().load("app/src/main/res/drawable-normal/nightbackground.png").into(backIV);
                    }

                    JSONObject forecastOBJ = response.getJSONObject("forecast");
                    JSONObject forecastDay0 = forecastOBJ.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hours = forecastDay0.getJSONArray("hour");
                    for ( int i = 0; i < hours.length(); i++ ) {
                        JSONObject hourOBJ = hours.getJSONObject(i);
                        String time = hourOBJ.getString("time");
                        String temp = hourOBJ.getString("temp_f");
                        String image = hourOBJ.getJSONObject("condition").getString("icon");
                        weatherRecycleArrayList.add(new WeatherRecycle(time,temp,image));
                        RVadapter.notifyDataSetChanged();
                    }
                    RVadapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mcontext, "Enter a valid City", Toast.LENGTH_SHORT).show();

            }
        });

        RQ.add(JsonOR);
    }

}