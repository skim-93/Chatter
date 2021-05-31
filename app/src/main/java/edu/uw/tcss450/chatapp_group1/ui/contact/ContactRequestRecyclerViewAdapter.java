package edu.uw.tcss450.chatapp_group1.ui.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

public class ContactRequestRecyclerViewAdapter extends
        RecyclerView.Adapter<ContactRequestRecyclerViewAdapter.RequestViewHolder> implements Filterable {
    /**Initializer for search contact list**/
    private List<Contact> mSearchContacts;
    /**Initializer for contact request list**/
    private List<Contact> mFriendRequest;
    /**Initializer for contact list view model**/
    private ContactListViewModel mViewModel;
    /**Initializer for user info view model**/
    private UserInfoViewModel mInfoModel;
    /**Initializer for context**/
    private Context mContext;

    /**
     * Contact request recycler view adapter
     * @param contacts list of contact request
     * @param context context for recycler view adapter
     */
    public ContactRequestRecyclerViewAdapter(List<Contact> contacts, Context context,UserInfoViewModel userModel,
                                             ContactListViewModel viewModel) {
        ContactGenerator generator = new ContactGenerator();
        this.mFriendRequest = contacts;
        this.mSearchContacts = new ArrayList<>(contacts);
        this.mContext = context;
        this.mInfoModel = userModel;
        this.mViewModel = viewModel;
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
        private Contact mRequest;
        public RequestViewHolder(View v) {
            super(v);
            mView = v;
            usernameTextView = v.findViewById(R.id.contact_email_request);
            contact_request_accept_button = v.findViewById(R.id.contact_button_request_accept);
            contact_request_decline_button = v.findViewById(R.id.contact_button_request_decline);
        }

        private void setRequest(final Contact request, RequestViewHolder holder) {
            mRequest = request;
            usernameTextView.setText(mRequest.getEmail());
            //set on click listener for accept button
            contact_request_accept_button.setOnClickListener(v -> {
                mViewModel.acceptRequest(mInfoModel.getmJwt(), mRequest.getmMemberID());
                holder.contact_request_accept_button.setVisibility(View.GONE);
                holder.contact_request_decline_button.setVisibility(View.GONE);
                mFriendRequest.remove(mRequest);
                notifyDataSetChanged();
            });
            //set on click listener for decline button
            contact_request_decline_button.setOnClickListener(v -> {
                mViewModel.deleteContact(mInfoModel.getmJwt(), mRequest.getmMemberID());
                holder.contact_request_accept_button.setVisibility(View.GONE);
                holder.contact_request_decline_button.setVisibility(View.GONE);
                mFriendRequest.remove(mRequest);
                notifyDataSetChanged();
            });
        }
    }

    /**
     * Getter for search filter
     * @return return with search filter
     */
    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    /**
     * method to search contact card from the list and then return with matching info
     */
    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contact> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mSearchContacts);
            } else {
                String searchRequest = constraint.toString().toLowerCase().trim();
                for (Contact contacts : mSearchContacts) {
                    if (contacts.getEmail().toLowerCase().contains(searchRequest)) {
                        filteredList.add(contacts);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        /**
         * receive filter result and apply to the list contacts
         * @param constraint readable sequence of char values
         * @param results result from filter data
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFriendRequest.clear();
            mFriendRequest.addAll((List<Contact>) results.values);
            notifyDataSetChanged();
        }
    };
}
