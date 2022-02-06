package com.juergenkleck.android.lwp.fishes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import com.juergenkleck.android.lwp.fishes.preference.FishesEnum;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class FishesSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fishes_settings, rootKey);

        final SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        CheckBoxPreference cbFishNeon = findPreference("fish_neon");
        CheckBoxPreference cbFishBlueTang = findPreference("fish_bluetang");
        CheckBoxPreference cbFishDiscus = findPreference("fish_discus");
        CheckBoxPreference cbFishCichlid = findPreference("fish_cichlid");
        CheckBoxPreference cbSwimSchool = findPreference("swim_school");
        CheckBoxPreference cbSwimRealistic = findPreference("swim_realistic");
        SeekBarPreference sbSwimSpeed = findPreference("swim_speed");
        ListPreference lsWallpaper = findPreference("wallpaper_list");

        cbFishNeon.setChecked(sharedPreferences.getBoolean("fish_" + FishesEnum.neon.name(), Boolean.TRUE));
        cbFishBlueTang.setChecked(sharedPreferences.getBoolean("fish_" + FishesEnum.bluetang.name(), Boolean.TRUE));
        cbFishDiscus.setChecked(sharedPreferences.getBoolean("fish_" + FishesEnum.discus.name(), Boolean.TRUE));
        cbFishCichlid.setChecked(sharedPreferences.getBoolean("fish_" + FishesEnum.cichlid.name(), Boolean.TRUE));
        cbSwimSchool.setChecked(sharedPreferences.getBoolean(Constants.SETTING_SWIM_SCHOOLS, Constants.SETTING_SWIM_SCHOOLS_DEFAULT));
        cbSwimRealistic.setChecked(sharedPreferences.getBoolean(Constants.SETTING_SWIM_REALISTIC, Constants.SETTING_SWIM_REALISTIC_DEFAULT));
        sbSwimSpeed.setValue(sharedPreferences.getInt(Constants.SETTING_SWIM_SPEED, Constants.SETTING_SWIM_SPEED_DEFAULT));
        lsWallpaper.setValue(sharedPreferences.getString(Constants.SETTING_WALLPAPER, Constants.SETTING_WALLPAPER_DEFAULT));

        cbFishNeon.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean("fish_" + FishesEnum.neon.name(), (Boolean) newValue).apply();
            return true;
        });
        cbFishBlueTang.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean("fish_" + FishesEnum.bluetang.name(), (Boolean) newValue).apply();
            return true;
        });
        cbFishDiscus.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean("fish_" + FishesEnum.discus.name(), (Boolean) newValue).apply();
            return true;
        });
        cbFishCichlid.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean("fish_" + FishesEnum.cichlid.name(), (Boolean) newValue).apply();
            return true;
        });
        cbSwimSchool.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean(Constants.SETTING_SWIM_SCHOOLS, (Boolean) newValue).apply();
            return true;
        });
        cbSwimRealistic.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putBoolean(Constants.SETTING_SWIM_REALISTIC, (Boolean) newValue).apply();
            return true;
        });
        sbSwimSpeed.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putInt(Constants.SETTING_SWIM_SPEED, (Integer) newValue).apply();
            return true;
        });
        lsWallpaper.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putString(Constants.SETTING_WALLPAPER, (String) newValue).apply();
            return true;
        });

    }

}