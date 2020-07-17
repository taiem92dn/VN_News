package com.tngdev.vnnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tngdev.vnnews.ui.main.MainFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        MenuItem item = menu.findItem(R.id.item_menu_dark_mode);
        item.setChecked(preferencesManager.isDarkMode());
        applyDarkMode(item.isChecked());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_dark_mode:
                item.setChecked(!item.isChecked());
                preferencesManager.toggleDarkMode();
                applyDarkMode(item.isChecked());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyDarkMode(boolean itemChecked) {
        AppCompatDelegate.setDefaultNightMode(
                itemChecked ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO
        );
    }
}