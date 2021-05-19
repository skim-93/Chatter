package edu.uw.tcss450.chatapp.ui.contact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.chatapp.R;
import edu.uw.tcss450.chatapp.databinding.FragmentContactRequestBinding;
import edu.uw.tcss450.chatapp.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactRequestFragment extends Fragment {
    /**Initializer for contact request view model**/
    private ContactRequestViewModel mModel;

    /**
     * on create for contact request fragment
     * @param savedInstanceState saved instance state bundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactRequestViewModel.class);
        UserInfoViewModel model = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
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

        mModel.addRequestListObserver(getViewLifecycleOwner(), requestList -> binding.contactListRoot.setAdapter(new ContactRequestRecyclerViewAdapter(requestList, this.getContext())));
    }

}