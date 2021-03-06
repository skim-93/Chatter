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
import edu.uw.tcss450.chatapp_group1.databinding.FragmentContactListBinding;
import edu.uw.tcss450.chatapp_group1.databinding.FragmentContactSearchBinding;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactListFragment extends Fragment {

    private ContactListViewModel mContactListViewModel;
    private UserInfoViewModel mUserInfoViewModel;
    private int mChatId;
    private boolean mFromAddChatMember;

    /**
     * On create for contact list fragment
     * @param savedInstanceState saved instance state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactListViewModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
        mUserInfoViewModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactListViewModel.connectContactFriendList(mUserInfoViewModel.getmJwt());
    }

    /**
     * on create view for contact list fragment
     * @param inflater layout inflater for contact list fragment
     * @param container view group container
     * @param savedInstanceState saved instance state
     * @return inflate the layout for contact list fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    /**
     * On view created for contact list fragment
     * @param view view for contact list fragment
     * @param savedInstanceState saved instance state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());
        if (getArguments() != null && !getArguments().isEmpty()) {
            ContactListFragmentArgs args = ContactListFragmentArgs.fromBundle(getArguments());
            mChatId = args.getChatid();
            mFromAddChatMember = args.getFromAddChatMember();
        }
        mContactListViewModel.addContactFriendListObserver(getViewLifecycleOwner(), contactList -> {
                    ContactRecyclerViewAdapter adapter = new ContactRecyclerViewAdapter(contactList, this.getContext(),
                        getChildFragmentManager(), mUserInfoViewModel, mContactListViewModel, mChatId, mFromAddChatMember);
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