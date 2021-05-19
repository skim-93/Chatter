package edu.uw.tcss450.chatapp_group1.ui.contact;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

public class ContactRequestViewModel extends AndroidViewModel {
    /**Initializer for request contact list**/
    private MutableLiveData<List<ContactRequest>> mRequestList;
    /**Initializer for response JSONobject**/
    private final MutableLiveData<JSONObject> mResponse;
    // request sample list generator
    // ContactRequestGenerator generator = new ContactRequestGenerator();

    // constructor for request view model
    public ContactRequestViewModel(@NonNull Application application) {
        super(application);
        mRequestList = new MutableLiveData<>(ContactRequestGenerator.getContactList());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * add observer for request contact list
     * @param owner LifecycleOwner
     * @param observer Observer List FriendRequest
     */
    public void addRequestListObserver(@NonNull LifecycleOwner owner,
                                       @NonNull Observer<? super List<ContactRequest>> observer) {
        mRequestList.observe(owner, observer);
    }

    /**
     * http request to contact request list (will be updated in sprint3)
     * @param jwt user's jwt from umodel
     */
    public void requestContactlist(final String jwt) {

    }
    /**
     * method to handle success search feedback for backend (will be updated in sprint3)
     * @param result json object for search contact list
     */
    private void handleSuccess(final JSONObject result) {
    }


    /**
     * method to handleError for backend (will be updated in sprint3)
     * @param error Volley Error.
     */
    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            try {
                mResponse.setValue(new JSONObject("{" +
                        "error:\"" + error.getMessage() +
                        "\"}"));
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        } else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset())
                    .replace('\"', '\'');
            try {
                JSONObject response = new JSONObject();
                response.put("code", error.networkResponse.statusCode);
                response.put("data", new JSONObject(data));
                mResponse.setValue(response);
            } catch (JSONException e) {
                Log.e("JSON PARSE", "JSON Parse Error in handleError");
            }
        }
        Log.e("CONNECTION ERROR", error.getLocalizedMessage());
        throw new IllegalStateException(error.getMessage());
    }


}
