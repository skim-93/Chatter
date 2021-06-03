package edu.uw.tcss450.chatapp_group1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import edu.uw.tcss450.chatapp_group1.ui.auth.signin.ChangePasswordViewModel;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.shared_theme_pref),
                Context.MODE_PRIVATE
        );
        if (prefs.getString(getString(R.string.theme_pref),"")
                .equals("Dark Red")) {
            setTheme(R.style.Theme_DarkRed);
        } else if (prefs.getString(getString(R.string.theme_pref),"")
                .equals("Dark Orange")) {
            setTheme(R.style.Theme_DarkOrange);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // Save the email
        Intent intent = getIntent();
        ChangePasswordViewModel mModel = new ViewModelProvider(this)
                .get(ChangePasswordViewModel.class);
        mModel.setmEmail(intent.getStringExtra("email"));
    }
}