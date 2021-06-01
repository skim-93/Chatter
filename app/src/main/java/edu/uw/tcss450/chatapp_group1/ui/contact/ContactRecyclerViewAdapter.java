package edu.uw.tcss450.chatapp_group1.ui.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

public class ContactRecyclerViewAdapter extends
        RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> implements Filterable {
    /**Initializer for contact list**/
    private final List<Contact> mContactList;
    /**Initializer for search contact list**/
    private List<Contact> mSearchContacts;
    /**Initializer for context**/
    private final Context mContext;
    /**Initializer for fragment manager**/
    private final FragmentManager mFragmentManager;
    /**Initializer for user info view model**/
    private final UserInfoViewModel mUserInfoViewModel;
    /**Initializer for ocntact list view model**/
    private final ContactListViewModel mContactListViewModel;

    /**
     * contact recycler view adapter constructor
     * @param contacts list contact
     * @param context context
     * @param fragmentManager fragment manager
     * @param userModel user info view model
     * @param viewModel contact list view model
     */
    public ContactRecyclerViewAdapter(List<Contact> contacts, Context context, FragmentManager fragmentManager, UserInfoViewModel userModel,
                                      ContactListViewModel viewModel) {
        this.mContactList = contacts;
        this.mContext = context;
        this.mSearchContacts = new ArrayList<>(contacts);
        this.mFragmentManager = fragmentManager;
        this.mUserInfoViewModel = userModel;
        this.mContactListViewModel = viewModel;

    }

    /**
     * contact recycler view adapter
     * @param parent parent view group
     * @param viewType view type
     * @return contact view holder with inflate the layout for card fragment
     */
    @NonNull
    @Override
    public ContactRecyclerViewAdapter.ContactViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_contact_friend_card, parent,false));
    }

    /**
     * on bind view holder for contact list
     * @param holder contact view holder
     * @param position int value for position
     */
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setContact(mContactList.get(position));
    }

    /**
     * Getter for item count
     * @return return with contact list size
     */
    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    /**
     * contact view holder to represent an individual row view(card) from the list
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder  {
        private final TextView nameTextView;
        private final TextView usernameTextView;
        private Contact mContact;

        public ContactViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.contact_search_name);
            usernameTextView = v.findViewById(R.id.contact_search_username);

            v.setOnClickListener(view -> {
                ContactPopUpFragment popUp = new ContactPopUpFragment(mContact, mContactListViewModel,
                        mUserInfoViewModel, this);
                popUp.show(mFragmentManager,"popUpDialog");
                if(!popUp.isVisible()) {
                    notifyDataSetChanged();
                }
            });
        }

        /**
         * Sets the contact first name and username
         * @param contact the contact
         */
        private void setContact(final Contact contact) {
            mContact = contact;
            nameTextView.setText(mContact.getFirstName());
            usernameTextView.setText(mContact.getUserName());
        }

        /**
         * delete contact from the list
         */
        public void deleteContact(){
            mContactList.remove(mContact);
            notifyDataSetChanged();
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
                String searchNewPerson = constraint.toString().toLowerCase().trim();
                for (Contact contact : mSearchContacts) {
                    if (contact.getFirstName().toLowerCase().contains(searchNewPerson) ||
                            contact.getLastName().toLowerCase().contains(searchNewPerson)||
                            contact.getEmail().toLowerCase().contains(searchNewPerson)) {
                        filteredList.add(contact);
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
            mContactList.clear();
            mContactList.addAll((List<Contact>) results.values);
            notifyDataSetChanged();
        }
    };

}
