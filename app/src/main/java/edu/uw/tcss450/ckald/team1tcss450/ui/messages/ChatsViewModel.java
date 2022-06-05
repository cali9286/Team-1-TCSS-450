package edu.uw.tcss450.ckald.team1tcss450.ui.messages;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

import edu.uw.tcss450.ckald.team1tcss450.R;

public class ChatsViewModel extends AndroidViewModel {
    private MutableLiveData<List<MessagesFragment>> mChatsList;
    public ChatsViewModel(@NonNull Application application) {
        super(application);
        mChatsList = new MutableLiveData<>();
        mChatsList.setValue(new ArrayList<>());
    }

    public void addChatsObserver(@NonNull LifecycleOwner owner,
                                 @NonNull Observer<? super List<MessagesFragment>> observer) {
        mChatsList.observe(owner, observer);
    }

    private void handleError(final VolleyError error) {
        //you should add much better error handling in a production release.
        //i.e. YOUR PROJECT
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }

//    private void handleResult(final JSONObject result) {
//        IntFunction<String> getString =
//                getApplication().getResources()::getString;
//
//        try {
//            JSONObject root = result;
//            if (root.has(getString.apply(R.string.keys_json_blogs_response))) {
//                JSONObject response =
//                        root.getJSONObject(getString.apply(
//                                R.string.keys_json_blogs_response));
//                if (response.has(getString.apply(R.string.keys_json_blogs_data))) {
//                    JSONArray data = response.getJSONArray(
//                            getString.apply(R.string.keys_json_blogs_data));
//                    for(int i = 0; i < data.length(); i++) {
//                        JSONObject jsonChat = data.getJSONObject(i);
//                        Chat chat = new Chat.Builder(
//                                jsonChat.getString(
//                                        getString.apply(
//                                                R.string.keys_json_chats_chatID))).build();
//                        if (!mChatsList.getValue().contains(chat)) {
//                            mChatsList.getValue().add(chat);
//                        }
//                    }
//                } else {
//                    Log.e("ERROR!", "No data array");
//                }
//            } else {
//                Log.e("ERROR!", "No response");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.e("ERROR!", e.getMessage());
//        }
//        mChatsList.setValue(mChatsList.getValue());
//    }

    public void connectGet() {
        MessagesFragment[] mclist = ChatGenerator.getCHATS();
//        for (int i = 0; i < mclist.length; i++) {
//            mChatsList.setValue(mclist);
//        }
        mChatsList.setValue(ChatGenerator.getChat());
        //mChatsList = ChatGenerator.getCHATS();
//        String url =
//                "https://team1-tcss450-labs-service.herokuapp.com/phish/blog/get";
//        Request request = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                null, //no body for this get request
//                this::handleResult,
//                this::handleError) {
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> headers = new HashMap<>();
//                // add headers <key,value>
//                headers.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.ey" +
//                        "JlbWFpbCI6ImNmYjNAdXcuZWR1IiwibWVtYmVyaWQiOjEsImlhdCI6MTY0OT" +
//                        "g3MzY5MCwiZXhwIjoxNjU4NTEzNjkwfQ.Q5xHvBMsiBrTjB1hNibUkVvZpZC" +
//                        "kXKNeHOIASE7fSPo");
//                return headers;
//            }
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                10_000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        //Instantiate the RequestQueue and add the request to the queue
//        Volley.newRequestQueue(getApplication().getApplicationContext())
//                .add(request);
    }


}
