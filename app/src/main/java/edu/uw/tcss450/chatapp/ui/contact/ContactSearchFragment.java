package edu.uw.tcss450.chatapp.ui.contact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import edu.uw.tcss450.chatapp.R;
import edu.uw.tcss450.chatapp.databinding.FragmentContactSearchBinding;
import edu.uw.tcss450.chatapp.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactSearchFragment extends Fragment {
    /**Initializer for contact list view model**/
    private ContactListViewModel mContactListViewModel;
    /**Initializer for user info view model**/
    private UserInfoViewModel mUserInfoViewModel;

    /**
     * on create for search fragment
     * @param savedInstanceState saved instance state bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactListViewModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactListViewModel.connectContactSearchList(mUserInfoViewModel.getmJwt());
    }

    /**
     * on create view for search fragment
     * @param inflater layout inflater for search fragment
     * @param container viewgroup container for search fragment
     * @param savedInstanceState saved instance state bundle for search fragment
     * @return inflate the layout for contact search fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_search, container, false);
    }

    /**
     * on view created for contact search fragment
     * @param view view for search fragment
     * @param savedInstanceState saved instance state bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactSearchBinding binding = FragmentContactSearchBinding.bind(getView());
        mContactListViewModel.addContactSearchObserver(getViewLifecycleOwner(), contactList -> {
                    ContactSearchRecyclerViewAdapter adapter = new ContactSearchRecyclerViewAdapter(contactList, mUserInfoViewModel, mContactListViewModel);
                    binding.contactListRoot.setAdapter(adapter);
                    //setup search tab for text listener

                    binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            return false;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            adapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                }
        );
    }

}