package edu.uw.tcss450.chatapp_group1.ui.auth.signin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.chatapp_group1.databinding.FragmentChangePasswordBinding;
import edu.uw.tcss450.chatapp_group1.utils.PasswordValidator;

import static edu.uw.tcss450.chatapp_group1.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.chatapp_group1.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.chatapp_group1.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.chatapp_group1.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.chatapp_group1.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.chatapp_group1.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.chatapp_group1.utils.PasswordValidator.checkPwdUpperCase;

/**
 * A simple {@link Fragment} subclass.
 */
public class InAppChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;
    private ChangePasswordViewModel mSetPasswordViewModel;

    private String mEmail;

    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.passwordField2.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSetPasswordViewModel = new ViewModelProvider(getActivity())
                .get(ChangePasswordViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSetPasswordViewModel.addResponseObserver(
                getViewLifecycleOwner(),
                this::observeSignInResponse);

        binding.cancelButton2.setOnClickListener(this::navigateBackToHomePage);
        binding.submitPasswordsButton.setOnClickListener(this::checkPasswords);

        InAppChangePasswordFragmentArgs args = InAppChangePasswordFragmentArgs.fromBundle(getArguments());

        mEmail = args.getEmail();

        Log.d("temp", "here1");
    }

    /**
     * Observes a chance in the json object
     * @param response The new json object
     */
    private void observeSignInResponse(JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.passwordField1.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateBackToHomePage(null);
                ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

    /**
     * Checks that the password is valid
     * @param view The view required to make this be an action listener
     */
    private void checkPasswords(View view) {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(binding.passwordField1.getText().toString()),
                this::attemptToSetPassword,
                result -> binding.passwordField1.setError("Please enter a valid Password."));
    }

    /**
     * Sends a request to set the given user with the given temporary password to the given new password
     */
    private void attemptToSetPassword() {
        mSetPasswordViewModel.connect(mEmail, binding.passwordField1.getText().toString());
    }

    /**
     * Navigates safely back to sign in page
     * @param view See above
     */
    private void navigateBackToHomePage(View view) {

        Navigation.findNavController(getView()).navigate(InAppChangePasswordFragmentDirections.actionInAppChangePasswordFragmentToNavigationHome() );
    }
}
