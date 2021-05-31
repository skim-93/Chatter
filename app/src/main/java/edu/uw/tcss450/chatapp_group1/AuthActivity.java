package edu.uw.tcss450.chatapp_group1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;

import edu.uw.tcss450.chatapp_group1.model.PushyTokenViewModel;
import me.pushy.sdk.Pushy;

public class AuthActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_auth);

        //If it is not already running, start the Pushy listening service
        Pushy.listen(this);
        initiatePushyTokenRequest();
    }

    private void initiatePushyTokenRequest() {
        new ViewModelProvider(this).get(PushyTokenViewModel.class).retrieveToken();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0,     spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Navigate to settings activity
            Intent settingIntent = new Intent(AuthActivity.this, SettingsActivity.class);
            startActivityForResult(settingIntent, 1);
            return true;
        }
        if (id == R.id.action_sign_out) {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { // Check if coming from Settings Activity
            recreate(); // Update the Color theme by recreating activity
        }
    }

    private void signOut() {
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.keys_shared_prefs), Context.MODE_PRIVATE);
        prefs.edit().remove(getString(R.string.keys_prefs_jwt)).apply();
        //End the app completely
        finishAndRemoveTask();
    }
}
