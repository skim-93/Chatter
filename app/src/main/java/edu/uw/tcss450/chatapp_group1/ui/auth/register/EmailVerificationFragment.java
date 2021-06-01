package edu.uw.tcss450.chatapp_group1.ui.auth.register;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.chatapp_group1.databinding.FragmentEmailVerificationBinding;

public class EmailVerificationFragment extends Fragment {

    private FragmentEmailVerificationBinding binding;
    private EmailVerificationViewModel mVerificationModel;
    private final String TAG = "Email Verification Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmailVerificationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVerificationModel = new ViewModelProvider(getActivity())
                .get(EmailVerificationViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVerificationModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeEmailVerificationResponse);

        binding.submitEmailVerifyCode.setOnClickListener(button -> {
            // Send request to get verification code.
            mVerificationModel.sendVerificationRequest();
        });
    }

    private void observeEmailVerificationResponse(final JSONObject response) {
        Context context = getContext();
        int toast_duration = Toast.LENGTH_SHORT;
        Toast toast;
        CharSequence message;
        try {
            String verificationCode = response.get("verification").toString();
            String userVerificationCode = binding.enterEmailVerify.getText().toString();

            if (verificationCode.equals(userVerificationCode)) {
                message = "Success!";
                toast = Toast.makeText(context, message, toast_duration);
                toast.show();
                navigateAway();
            } else {
                message = "Invalid! Try again";
                toast = Toast.makeText(context, message, toast_duration);
                toast.show();
            }
        } catch (JSONException e) {
            Log.e("JSON Parse Error", e.getMessage());
        }
    }

    private void navigateAway() {
        EmailVerificationFragmentArgs args = EmailVerificationFragmentArgs
                .fromBundle(getArguments());
        EmailVerificationFragmentDirections.ActionEmailVerificationFragmentToSignInFragment directions =
                EmailVerificationFragmentDirections.actionEmailVerificationFragmentToSignInFragment(
                );
        if (args.getPassword().equals("RECOVER")) { // Navigate to password recovery
            EmailVerificationFragmentDirections.ActionEmailVerificationFragmentToChangePasswordFragment passwordDirections =
                    EmailVerificationFragmentDirections.actionEmailVerificationFragmentToChangePasswordFragment(args.getEmail());

            Navigation.findNavController(getView()).navigate(passwordDirections);
            return;
        }
        // Hit endpoint so that the user is now verified
        mVerificationModel.verifyUserRequest(args.getEmail());

        directions.setEmail(args.getEmail());
        directions.setPassword(args.getPassword());
        Navigation.findNavController(getView()).navigate(directions);
    }
}
