package edu.uw.tcss450.chatapp_group1.ui.contact;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

public class ContactSearchRecyclerViewAdapter extends
        RecyclerView.Adapter<ContactSearchRecyclerViewAdapter.SearchViewHolder> implements Filterable {
    /**Initializer for contact list**/
    private List<Contact> mContacts;
    /**Initializer for search contact list**/
    private List<Contact> mSearchContacts;
    /**Initializer for cuser info view model**/
    private UserInfoViewModel mUserModel;
    /**Initializer for contact list view model**/
    private ContactListViewModel mViewModel;

    /**
     * Constructor for search recycler view adapter
     * @param contacts list of contacts
     * @param userModel user info view model
     * @param viewModel contact list view model
     */
    public ContactSearchRecyclerViewAdapter(List<Contact> contacts, UserInfoViewModel userModel,
                                            ContactListViewModel viewModel) {
        this.mContacts = contacts;
        this.mSearchContacts = new ArrayList<>(contacts);
        this.mUserModel = userModel;
        this.mViewModel = viewModel;
    }

    /**
     * on create view holder for contact search view holder
     * @param parent view group for context
     * @param viewType int of view type
     * @return view holder search recycler view
     */
    @Override
    @NonNull
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View searchView = inflater.inflate(R.layout.fragment_contact_search_card, parent, false);
        return new SearchViewHolder(searchView);
    }

    /**
     * on bind view holder for contact search recycler view
     * @param holder search view holder for contacts
     * @param position int value of position
     */
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            Contact currentItem = mContacts.get(position);
            holder.usernameTextView.setText(currentItem.getEmail());
            holder.nameTextView.setText(currentItem.getFirstName() + " " + currentItem.getLastName());
            holder.searchAddButton.setOnClickListener(v -> {
                holder.searchAddButton.setVisibility(View.GONE);
                mViewModel.addFriendsList(mUserModel.getmJwt(), mContacts.get(position).getmMemberID());
                notifyDataSetChanged();
            });
    }

    /**
     * Getter for item counts
     * @return contact size
     */
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * contact search view holder to represent an individual row view(card) from the list
     */
    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView usernameTextView;
        private final ImageButton searchAddButton;

        public SearchViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.contact_search_name);
            usernameTextView = view.findViewById(R.id.contact_search_username);
            searchAddButton = view.findViewById(R.id.contact_search_add_button);
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
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0 || constraint.toString()=="") {
                  filteredList.clear();
            } else {
                String searchNewPerson = constraint.toString().toLowerCase().trim();
                for (Contact contact : mSearchContacts) {
                    if (contact.getFirstName().toLowerCase().contains(searchNewPerson) ||
                            contact.getLastName().toLowerCase().contains(searchNewPerson)||
                            contact.getEmail().toLowerCase().contains(searchNewPerson)) {
                        filteredList.add(contact);
                    }
                }
            }
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
            mContacts.clear();
            mContacts.addAll((List<Contact>) results.values);
            notifyDataSetChanged();
        }
    };
}
