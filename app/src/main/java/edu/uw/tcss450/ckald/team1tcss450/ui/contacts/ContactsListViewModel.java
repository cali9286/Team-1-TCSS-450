package edu.uw.tcss450.ckald.team1tcss450.ui.contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.ckald.team1tcss450.R;
import edu.uw.tcss450.ckald.team1tcss450.databinding.FragmentContactsBinding;

public class ContactsListViewModel extends AndroidViewModel {
       FragmentContactsBinding mBinding;
       ArrayList<ContactModel> arrayList = new ArrayList<ContactModel>();

    private MutableLiveData<List<ContactModel>> mContactList;

    public ContactsListViewModel(@NonNull Application application) {
        super(application);
        mContactList = new MutableLiveData<>(new ArrayList<>());
    }

    public void addContactListObserver(@NonNull LifecycleOwner owner,
                                       @NonNull Observer<? super List<ContactModel>> observer) {
        mContactList.observe(owner, observer);
    }

    public List<ContactModel> getContactModels() {
        return mContactList.getValue();
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

    private void handleResult(final JSONObject result) {

        for (int i = 0; i < 5; i++) {
            ContactModel contact = new ContactModel("contact " + i);
            mContactList.getValue().add(contact);
            Log.e("for loop", mContactList.getValue().get(i).getEmail());
        }

        Log.e("test", "made it");

        Log.e("after for", mContactList.getValue().get(0).getEmail());

        mContactList.setValue(mContactList.getValue());

    }
    public void connectGet() {
        String url = getApplication().getResources().getString(R.string.base_url_service) +
                "contacts";
       Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                this::handleResult,
               this::handleError);
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);

        handleResult(new JSONObject());
    }
}

