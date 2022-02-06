package com.juergenkleck.android.lwp.fishes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class FishesSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new FishesSettingsFragment())
                .commit();
    }
}
