package edu.uw.tcss450.chatapp_group1.ui.chat;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.io.RequestQueueSingleton;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

/**
 * View model for the chat list. Stores data about chat lists across app lifecycles.
 *
 * @author Joseph
 */
public class ChatListViewModel extends AndroidViewModel {

    /**
     * Fields to store states/data.
     */
    private MutableLiveData<List<ChatRoomViewModel>> mChatRoomList;
    private final MutableLiveData<JSONObject> mResponse;
    private UserInfoViewModel userInfoViewModel;

    /**
     * Constructor
     * @param application app
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChatRoomList = new MutableLiveData<>(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Set this user info view model to ours.
     * @param theUserInfoViewModel
     */
    public void setUserInfoViewModel(UserInfoViewModel theUserInfoViewModel) {
        userInfoViewModel = theUserInfoViewModel;
    }

    /**
     * Set observer for changes to chat list.
     * @param owner owner
     * @param observer the observer
     */
    public void addChatListObserver(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<ChatRoomViewModel>> observer) {
        mChatRoomList.observe(owner, observer);
    }

    /**
     * Connect to get the list of chat rooms that the user is associated with.
     * @param jwt the current user's jwt
     */
    public void connectGet(String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chatrooms";
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, // no body
                this::handleSuccess,
                this::handleError
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Handle HTTP success response
     * @param result the json result
     */
    private void handleSuccess(final JSONObject result) {
        ArrayList<ChatRoomViewModel> temp = new ArrayList<>();
        try {
            JSONArray chats = result.getJSONArray("chats");
            for (int i = 0; i < chats.length(); i++) {
                JSONObject chat = chats.getJSONObject(i);
                int chatid = chat.getInt("chat");
                String chatTitle = chat.getString("name");
                ChatRoomViewModel post = new ChatRoomViewModel(chatid, chatTitle);
                temp.add(post);
            }
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "handleSuccess:");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
        mChatRoomList.setValue(temp);
    }

    /**
     * Deletes a chat room.
     * @param chatId the chat room id
     */
    public void deleteChat(final int chatId) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats/"
                + chatId + "/" + userInfoViewModel.getEmail();

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                mResponse::setValue,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", userInfoViewModel.getmJwt());
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }

    /**
     * Add a chat with a given name.
     * @param jwt the user's jwt
     * @param name the name of the chatroom
     */
    public void addChat(final String jwt, final String name) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats";

        JSONObject body = new JSONObject();
        try {
            body.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                response -> handleAddChat(jwt, response),
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }

    /**
     * Helper for adding chats.
     * @param jwt jwt
     * @param response the json response
     */
    private void handleAddChat(final String jwt, final JSONObject response) {
        try {
            int chatID = response.getInt("chatID");
            putMembers(jwt, chatID);
            connectGet(jwt);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "handleAddChat:");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }

    /**
     * Adding members to chat.
     * @param jwt jwt
     * @param chatID the chat room id
     */
    public void putMembers(final String jwt, int chatID) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats/" + chatID;
        JSONObject body = new JSONObject();
        try {
            body.put("chatid", chatID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body,
                mResponse::setValue,
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    /**
     * Handle HTTP error response.
     * @param error the error
     */
    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", "No Chat Info");
    }
}


