package com.jasonbutwell.settingspreferencefragmentexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Shows what the sort by preference actually is in the UI
        setSortByView( getSortByPreference(sharedPreferences) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // If action was the settings
        if ( id == R.id.action_settings ) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        else
            if ( id == R.id.action_favourite ) {
                Toast.makeText(this, "You clicked the favourite star icon!",Toast.LENGTH_SHORT).show();
                return true;
            }

        return super.onOptionsItemSelected(item);
    }

    // Sets a sort by UI view

    public void setSortByView( String stringValue ) {
        TextView tv = (TextView)findViewById(R.id.tv_sortby_info);
        tv.setText(stringValue);
    }

    // gets our sort by preference value

    public String getSortByPreference( SharedPreferences sharedPreferences ) {
        return getPreferenceValue( sharedPreferences, getResources().getString(R.string.pref_sortby_key) ,R.string.pref_sortby_option_value_latest );
    }

    // gets a preference value based on its key

    public String getPreferenceValue( SharedPreferences sharedPreferences, String key, int defaultValue ) {
        return sharedPreferences.getString(key,getResources().getString(defaultValue));
    }

    // Called every time a preference is changed

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if ( key.equals(getString(R.string.pref_sortby_key)) ) {
            String value = getPreferenceValue( sharedPreferences, key, R.string.pref_sortby_option_value_latest);
            setSortByView(value);
        }
    }
}
