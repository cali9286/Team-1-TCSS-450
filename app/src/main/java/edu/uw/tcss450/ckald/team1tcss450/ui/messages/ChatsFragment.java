package edu.uw.tcss450.ckald.team1tcss450.ui.messages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.ckald.team1tcss450.R;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentChatsBinding;

/**
 * create an instance of this fragment.
 */
public class ChatsFragment extends Fragment {

    private ChatsViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatsViewModel.class);
        mModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentChatsBinding binding = FragmentChatsBinding.bind(getView());

        mModel.addChatsObserver(getViewLifecycleOwner(), mChats -> {
            if (!mChats.isEmpty()) {
                binding.listRoot.setAdapter(
                        new ChatsRecycleViewAdapter(mChats)
                );
                binding.layoutWait.setVisibility(View.GONE);
            }
        });

    }
}