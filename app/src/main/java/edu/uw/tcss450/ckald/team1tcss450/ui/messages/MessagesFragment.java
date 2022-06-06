package edu.uw.tcss450.ckald.team1tcss450.ui.messages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

import edu.uw.tcss450.ckald.team1tcss450.R;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentMessagesBinding;
import edu.uw.tcss450.ckald.team1tcss450.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements Serializable {

    //The chat ID for "global" chat
    private static int HARD_CODED_CHAT_ID;

    private MessagesViewModel mChatModel;
    private UserInfoViewModel mUserModel;

    private MessagesSendViewModel mSendModel;

//    public MessagesFragment(int i) {
//        HARD_CODED_CHAT_ID = i;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("       new frag created","tostring: ");
        MessagesFragment[] mf = ChatGenerator.getCHATS();
        super.onCreate(savedInstanceState);
        //HARD_CODED_CHAT_ID = getArguments().getInt("idnum");
        HARD_CODED_CHAT_ID = mf[0].getArguments().getInt("idnum");
        //HARD_CODED_CHAT_ID = 1;
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        mChatModel = provider.get(MessagesViewModel.class);
        mChatModel.getFirstMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());
        mSendModel = provider.get(MessagesSendViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentMessagesBinding binding = FragmentMessagesBinding.bind(getView());

        //SetRefreshing shows the internal Swiper view progress bar. Show this until messages load
        binding.swipeContainer.setRefreshing(true);

        final RecyclerView rv = binding.recyclerMessages;
        //Set the Adapter to hold a reference to the list FOR THIS chat ID that the ViewModel
        //holds.
        rv.setAdapter(new MessagesRecyclerViewAdapter(
                mChatModel.getMessageListByChatId(HARD_CODED_CHAT_ID),
                mUserModel.getEmail()));


        //When the user scrolls to the top of the RV, the swiper list will "refresh"
        //The user is out of messages, go out to the service and get more
        binding.swipeContainer.setOnRefreshListener(() -> {
            mChatModel.getNextMessages(HARD_CODED_CHAT_ID, mUserModel.getmJwt());
        });

        mChatModel.addMessageObserver(HARD_CODED_CHAT_ID, getViewLifecycleOwner(),
                list -> {
                    /*
                     * This solution needs work on the scroll position. As a group,
                     * you will need to come up with some solution to manage the
                     * recyclerview scroll position. You also should consider a
                     * solution for when the keyboard is on the screen.
                     */
                    //inform the RV that the underlying list has (possibly) changed
                    rv.getAdapter().notifyDataSetChanged();
                    rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
                    binding.swipeContainer.setRefreshing(false);
                });

        //Send button was clicked. Send the message via the SendViewModel
        binding.buttonSend.setOnClickListener(button -> {
            mSendModel.sendMessage(HARD_CODED_CHAT_ID,
                    mUserModel.getmJwt(),
                    binding.editMessage.getText().toString());
        });
        //when we get the response back from the server, clear the edittext
        mSendModel.addResponseObserver(getViewLifecycleOwner(), response ->
                binding.editMessage.setText(/*this.toString()*/"" /*+ getArguments().getInt("idnum2")*/));


    }
    public static MessagesFragment newInstance(int idnum) {
        Bundle args = new Bundle();
        args.putInt("idnum", idnum);
        MessagesFragment m = new MessagesFragment();
        Log.e("       idnum: " + idnum,"tostring: " + m.toString());
        m.setArguments(args);
        return m;
    }
    public int setHardCodedChatId(String id) {
        int idInt = Integer.valueOf(id);
        HARD_CODED_CHAT_ID = idInt;
        return idInt;
    }
}