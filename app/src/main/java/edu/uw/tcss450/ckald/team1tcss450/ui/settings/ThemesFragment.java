package edu.uw.tcss450.ckald.team1tcss450.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.uw.tcss450.ckald.team1tcss450.R;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentHomeBinding;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentThemesBinding;
import edu.uw.tcss450.ckald.team1tcss450.model.UserInfoViewModel;
import edu.uw.tcss450.ckald.team1tcss450.utils.Change;
import edu.uw.tcss450.ckald.team1tcss450.utils.SavePreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemesFragment extends Fragment {


    private FragmentThemesBinding binding;
    private RadioGroup radioGroup;

    public ThemesFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThemesBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.themeStandard.setOnClickListener(button -> Change.changeTheme(getActivity(),
                new SavePreference(getContext()).retrieveInt("theme", Change.STANDARD_THEME)));
        binding.themePurple.setOnClickListener(button -> Change.changeTheme(getActivity(),
                new SavePreference(getContext()).retrieveInt("theme", Change.PURPLE_THEME)));
        binding.themeTeal.setOnClickListener(button -> Change.changeTheme( getActivity(),
                new SavePreference(getContext()).retrieveInt("theme",Change.TEAL_THEME)));

    }


}




