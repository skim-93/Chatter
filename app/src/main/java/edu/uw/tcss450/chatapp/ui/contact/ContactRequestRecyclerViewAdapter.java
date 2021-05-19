package edu.uw.tcss450.chatapp.ui.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatapp.R;
import edu.uw.tcss450.chatapp.model.UserInfoViewModel;

public class ContactRequestRecyclerViewAdapter extends
        RecyclerView.Adapter<ContactRequestRecyclerViewAdapter.RequestViewHolder> {
    /**Initializer for contact request list**/
    private List<ContactRequest> mFriendRequest;
    /**Initializer for contact list view model**/
    private ContactListViewModel mViewModel;
    /**Initializer for user info view model**/
    private UserInfoViewModel mInfoModel;
    /**Initializer for context**/
    private Context mContext;

    /**
     * Contact request recycler view adapter
     * @param requests list of contact request
     * @param context context for recycler view adapter
     */
    public ContactRequestRecyclerViewAdapter(List<ContactRequest> requests, Context context) {
        this.mFriendRequest = requests;
        mContext = context;
        mInfoModel = new ViewModelProvider((FragmentActivity) mContext)
                .get(UserInfoViewModel.class);
        mViewModel = new ViewModelProvider((FragmentActivity) mContext)
                .get(ContactListViewModel.class);
    }

    /**
     * on create view holder for request view holder
     * @param parent view group
     * @param viewType int of view type
     * @return view holder for contact request recycler view
     */
    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.fragment_contact_request_card,parent,false);
        return new ContactRequestRecyclerViewAdapter.RequestViewHolder(contactView);
    }

    /**
     * on bind view holder for contact request recycler view
     * @param holder request view holder for contacts
     * @param position int value of position
     */
    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.setRequest(mFriendRequest.get(position),holder);
    }

    /**
     * Getter for item counts
     * @return size of request friends
     */
    @Override
    public int getItemCount() {
        return mFriendRequest.size();
    }

    /**
     * contact view holder to represent an individual row view(card) from the list
     */
    public class RequestViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private ImageButton contact_request_accept_button;
        private ImageButton contact_request_decline_button;
        private final View mView;
        private ContactRequest mRequest;
        public RequestViewHolder(View v) {
            super(v);
            mView = v;
            usernameTextView = v.findViewById(R.id.contact_email_request);
            contact_request_accept_button = v.findViewById(R.id.contact_button_request_accept);
            contact_request_decline_button = v.findViewById(R.id.contact_button_request_decline);
        }

        private void setRequest(final ContactRequest request, RequestViewHolder holder) {
            mRequest = request;
            usernameTextView.setText(request.getUsername());
            //set on click listener for accept button
            contact_request_accept_button.setOnClickListener(v -> {
                //need to connect to backend later(will be updated in sprint3)
                holder.contact_request_accept_button.setVisibility(View.GONE);
            });
            //set on click listener for decline button
            contact_request_decline_button.setOnClickListener(v -> {
                holder.contact_request_decline_button.setVisibility(View.GONE);
                //need to connect to backend later(will be updated in sprint3)
            });
        }
    }
}
