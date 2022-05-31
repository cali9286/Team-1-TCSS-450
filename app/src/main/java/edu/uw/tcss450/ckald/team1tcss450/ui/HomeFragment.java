package edu.uw.tcss450.ckald.team1tcss450.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.ckald.team1tcss450.R;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentHomeBinding;
import edu.uw.tcss450.ckald.team1tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context mcontext = this.getContext();
    private TextView homeTempTV;
    private ImageView homeWeatherPicIV;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        homeTempTV = v.findViewById(R.id.idTVCurrentHOMEWeather);
        homeWeatherPicIV =v.findViewById(R.id.idIVHomeCurrentPic);

        getTemp();
        return v;
    }

    void getTemp() {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=95203d195a4b434780402424221205&q=Tacoma&days=1&aqi=no&alerts=no";
        RequestQueue RQ = Volley.newRequestQueue(mcontext);
        JsonObjectRequest JsonOR = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String currentTemp = response.getJSONObject("current").getString("temp_f");
                    homeTempTV.setText("The Current temperature is ".concat(currentTemp).concat("°F"));

                    String conditionImage = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("https:".concat(conditionImage)).into(homeWeatherPicIV);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                homeTempTV.setText("That did not work");
            }
        });
        //Something
        RQ.add(JsonOR);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Local access to the ViewBinding object. No need to create as Instance Var as it is only
        //used here.
        FragmentHomeBinding binding = FragmentHomeBinding.bind(getView());

        //Note argument sent to the ViewModelProvider constructor. It is the Activity that
        //holds this fragment.
        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

    }


}