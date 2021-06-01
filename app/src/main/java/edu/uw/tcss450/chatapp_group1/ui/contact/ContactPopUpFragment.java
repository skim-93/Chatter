package edu.uw.tcss450.chatapp_group1.ui.contact;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.uw.tcss450.chatapp_group1.R;
import edu.uw.tcss450.chatapp_group1.model.UserInfoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactPopUpFragment extends DialogFragment {
    /**Initializer for contact**/
    private final Contact mContact;
    /**Initializer for contact list view model **/
    private ContactListViewModel mContactModel;
    /** Initializer for user info view model**/
    private final UserInfoViewModel mUserModel;
    /** Initializer for contact recycler view adapter**/
    private final ContactRecyclerViewAdapter.ContactViewHolder mUpdater;
    /** Initializer for activity**/
    private Activity activity;

    /**
     * constructor for contactPopup
     * @param contact contact info for popup
     * @param contactModel contact list view model for popup
     * @param userInfoModel user info view model
     * @param updater contact view holder
     */
    public ContactPopUpFragment(Contact contact, ContactListViewModel contactModel,
                                UserInfoViewModel userInfoModel,
                                ContactRecyclerViewAdapter.ContactViewHolder updater) {
        this.mContact = contact;
        this.mContactModel = contactModel;
        this.mUserModel = userInfoModel;
        mUpdater = updater;
    }

    /**
     * On create for contact popup
     * @param savedInstanceState bundle saved instance state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactModel =  new ViewModelProvider(getActivity()).get(ContactListViewModel.class);
    }

    /**
     * On view created for contact popup
     * @param view view for contact popup
     * @param savedInstanceState bundle saved instance state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * on create dialog for popup
     * @param savedInstanceState saved instance state
     * @return return alert dialog popup with builder
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_contact_pop_up, null);
        //setting user name in popup (last, first)
        TextView userName = view.findViewById(R.id.contact_pop_up_name);
        userName.setText( mContact.getLastName()+ ",  " + mContact.getFirstName());
        //setting user email in popup
        TextView userEmail = view.findViewById(R.id.contact_pop_up_email);
        userEmail.setText(mContact.getEmail());
        //add button for deleting contact card from friend list
        Button deleteButton = view.findViewById(R.id.contact_pop_up_delete_button);
        deleteButton.setOnClickListener(v -> {
            mContactModel.deleteContact(mUserModel.getmJwt(), mContact.getmMemberID());
            mUpdater.deleteContact();
            dismiss();
        });
        builder.setView(view);
        return builder.create();
    }

    /**
     * on create view for popup(dialog)
     * @param inflater layout inflater for contact popup
     * @param container viewgroup container for contact popup
     * @param savedInstanceState saved instance state bundle for contact popup
     * @return inflate the layout for contact popup fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_pop_up, container, false);
    }

}