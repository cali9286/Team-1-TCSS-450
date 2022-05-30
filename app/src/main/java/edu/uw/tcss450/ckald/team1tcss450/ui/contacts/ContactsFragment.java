package edu.uw.tcss450.ckald.team1tcss450.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uw.tcss450.ckald.team1tcss450.R;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentContactsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private ContactsListViewModel mModel;

        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModel = new ViewModelProvider(getActivity()).get(ContactsListViewModel.class);
        mModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentContactsBinding binding = FragmentContactsBinding.bind(getView());

        final RecyclerView rv = binding.recyclerContacts;

        ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();
        rv.setAdapter(new ContactsRecyclerViewAdapter(arrayList));
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mModel.addContactListObserver(getViewLifecycleOwner(), list -> {
            rv.getAdapter().notifyDataSetChanged();
            rv.scrollToPosition(rv.getAdapter().getItemCount() - 1);
        });


    }
}