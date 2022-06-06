package edu.uw.tcss450.ckald.team1tcss450.ui.messages;

import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.uw.tcss450.ckald.team1tcss450.R;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentChatCardBinding;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentChatsBinding;

public class ChatsRecycleViewAdapter extends RecyclerView.Adapter<ChatsRecycleViewAdapter.ChatViewHolder> {

    private final Map<MessagesFragment, Boolean> mExpandedFlags;
    private final List<MessagesFragment> mChats;


    public ChatsRecycleViewAdapter(List<MessagesFragment> items) {
        this.mChats = items;
        mExpandedFlags = mChats.stream()
                .collect(Collectors.toMap(Function.identity(), chat -> false));
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Log.e("       checking in onBindViewHolder", mChats.get(position).toString());
        holder.setChat(mChats.get(position), position + 1);
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public FragmentChatCardBinding binding; // not sure if thats the right one
        private MessagesFragment mChat;
        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);
//            binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
        }

        private void handleMoreOrLess(final View button) {
            mExpandedFlags.put(mChat, !mExpandedFlags.get(mChats));
            displayPreview();
        }

        private void displayPreview() {
            if (mExpandedFlags.get(mChat)) {
                binding.textPreview.setVisibility(View.VISIBLE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_less_grey_24dp));
            } else {
                binding.textPreview.setVisibility(View.GONE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_more_grey_24dp));
            }
        }
        void setChat(final MessagesFragment chat, int pos) {
            this.mChat = chat;
//            mChat = MessagesFragment.newInstance(pos);
            String id = String.valueOf(pos);
            this.mChat.setHardCodedChatId(id);
            for (int i = 0; i < 3; i++) {
                Log.e("fragmentlist", mChats.get(i).toString());
            }
            binding.buttonFullPost.setOnClickListener(view -> {
                Navigation.findNavController(mView).navigate(
                        ChatsFragmentDirections
                                .actionNavigationChatsFragmentToNavigationMessagesFragment(mChat));
            });
            binding.textTitle.setText("Chat Room " + id /*mChat.toString()*/);
        }
    }
}
