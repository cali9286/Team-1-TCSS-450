package edu.uw.tcss450.ckald.team1tcss450.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import edu.uw.tcss450.ckald.team1tcss450.R;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentThemesBinding;
import edu.uw.tcss450.ckald.team1tcss450.utils.Change;
import edu.uw.tcss450.ckald.team1tcss450.utils.SharedPreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemesFragment extends Fragment {


    private FragmentThemesBinding binding;
    private RadioGroup radioGroup;

    /**
     * Empty constructor
     */

    public ThemesFragment(){
        // Required empty public constructor
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
                new SharedPreferenceManager(getContext()).retrieveInt("theme", Change.STANDARD_THEME)));
        binding.themePurple.setOnClickListener(button -> Change.changeTheme(getActivity(),
                new SharedPreferenceManager(getContext()).retrieveInt("theme", Change.PURPLE_THEME)));
        binding.themeTeal.setOnClickListener(button -> Change.changeTheme( getActivity(),
                new SharedPreferenceManager(getContext()).retrieveInt("theme",Change.TEAL_THEME)));

    }


}




