package edu.uw.tcss450.chatapp_group1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

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
        setTitle("Settings");
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            ListPreference themePref = (ListPreference) findPreference("theme");

            themePref.setOnPreferenceChangeListener((preference, newValue) -> {

                int index = themePref.findIndexOfValue(newValue.toString());

                if (index == 1) { // Dark red theme
                    SharedPreferences prefs = getActivity().getSharedPreferences(
                            getString(R.string.shared_theme_pref),
                            Context.MODE_PRIVATE
                    );
                    prefs.edit().putString(getString(R.string.theme_pref), "Dark Red").apply();
                } else if (index == 0) { // Dark orange theme
                    SharedPreferences prefs = getActivity().getSharedPreferences(
                            getString(R.string.shared_theme_pref),
                            Context.MODE_PRIVATE
                    );
                    prefs.edit().putString(getString(R.string.theme_pref), "Dark Orange").apply();
                }
                getActivity().recreate();
                return true;
            });
        }
    }
}