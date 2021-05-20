package edu.uw.tcss450.chatapp_group1.ui.contact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentContactRequestBinding;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentContactSearchBinding;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactRequestFragment extends Fragment {
    /**Initializer for contact request view model**/
    private ContactListViewModel mModel;
    /**Initializer for contact list view model**/
    private ContactListViewModel mContactListViewModel;
    /**Initializer for user info view model**/
    private UserInfoViewModel mUserInfoViewModel;
    /**
     * on create for contact request fragment
     * @param savedInstanceState saved instance state bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mContactListViewModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);

    }

    /**
     * on create view for contact request fragment
     * @param inflater layout inflater
     * @param container view group
     * @param savedInstanceState saved instance state bundle
     * @return Inflate the layout for contact request fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_request, container, false);
    }

    /**
     * on view created for contact request fragment
     * @param view view
     * @param savedInstanceState saved instance state bundle
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactRequestBinding binding = FragmentContactRequestBinding.bind(getView());
        mContactListViewModel.addRequestListObserver(getViewLifecycleOwner(), contactList -> {
                    ContactRequestRecyclerViewAdapter adapter = new ContactRequestRecyclerViewAdapter(contactList, this.getContext(), mUserInfoViewModel, mContactListViewModel);
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