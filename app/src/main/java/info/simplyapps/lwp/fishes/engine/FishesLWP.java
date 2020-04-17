package info.simplyapps.lwp.fishes.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.MotionEvent;

import java.util.Properties;

import info.simplyapps.gameengine.EngineConstants;
import info.simplyapps.gameengine.RenderingSystem;
import info.simplyapps.gameengine.engine.BasicEngine;
import info.simplyapps.gameengine.rendering.GenericWallpaperTemplate;
import info.simplyapps.lwp.fishes.Constants;
import info.simplyapps.lwp.fishes.preference.FishesEnum;
import info.simplyapps.lwp.fishes.preference.ValueContainer;
import info.simplyapps.lwp.fishes.preference.WallpaperEnum;
import info.simplyapps.lwp.fishes.rendering.FishesRenderer;
import info.simplyapps.lwp.fishes.storage.StorageUtil;

public class FishesLWP extends GenericWallpaperTemplate {

    private ValueContainer mValueContainer;

    public FishesLWP() {
        super();
        mValueContainer = new ValueContainer();
    }

    public void prepareStorage(Context context) {
        // new method 
        StorageUtil.prepareStorage(context);
    }

    @Override
    public void touchEvent(MotionEvent event) {
        getBasicEngine().onTouchEvent(event);
    }

    @Override
    public void initSharedPrefs(info.simplyapps.gameengine.rendering.data.ValueContainer vc,
                                SharedPreferences prefs, Editor editor) {

        prepareStorage(getApplicationContext());

        mValueContainer.wallpaper = prefs.getString(Constants.SETTING_WALLPAPER, Constants.SETTING_WALLPAPER_DEFAULT);
        mValueContainer.swimSchool = prefs.getBoolean(Constants.SETTING_SWIM_SCHOOLS, Constants.SETTING_SWIM_SCHOOLS_DEFAULT);
        mValueContainer.swimSpeed = prefs.getInt(Constants.SETTING_SWIM_SPEED, Constants.SETTING_SWIM_SPEED_DEFAULT);
        mValueContainer.swimRealistic = prefs.getBoolean(Constants.SETTING_SWIM_REALISTIC, Constants.SETTING_SWIM_REALISTIC_DEFAULT);

        // load wallpaper
        if (mValueContainer.wallpaper == null) {
            mValueContainer.wallpaper = WallpaperEnum.bluewater.name();
            editor.putString(Constants.SETTING_WALLPAPER, mValueContainer.wallpaper);
        }

        boolean allEmpty = true;
        // load fishes configuration
        for (FishesEnum val : FishesEnum.values()) {
            mValueContainer.fishes.put(val.name(), prefs.getBoolean("fish_" + val, true));
            allEmpty &= !mValueContainer.fishes.get(val.name()).booleanValue();
        }

        if (allEmpty) {
            mValueContainer.fishes.put(FishesEnum.neon.name(), Boolean.TRUE);
            editor.putBoolean("fish_" + FishesEnum.neon.name(), Boolean.TRUE);
        }
        editor.putBoolean(Constants.SETTING_SWIM_SCHOOLS, mValueContainer.swimSchool);
        editor.putBoolean(Constants.SETTING_SWIM_REALISTIC, mValueContainer.swimRealistic);
    }

    @Override
    public void onSharedPreferenceChanged(
            info.simplyapps.gameengine.rendering.data.ValueContainer vc, SharedPreferences prefs,
            String key) {

        mValueContainer.wallpaper = prefs.getString(Constants.SETTING_WALLPAPER, Constants.SETTING_WALLPAPER_DEFAULT);
        mValueContainer.swimSchool = prefs.getBoolean(Constants.SETTING_SWIM_SCHOOLS, Constants.SETTING_SWIM_SCHOOLS_DEFAULT);
        mValueContainer.swimSpeed = prefs.getInt(Constants.SETTING_SWIM_SPEED, Constants.SETTING_SWIM_SPEED_DEFAULT);
        mValueContainer.swimRealistic = prefs.getBoolean(Constants.SETTING_SWIM_REALISTIC, Constants.SETTING_SWIM_REALISTIC_DEFAULT);

        for (FishesEnum val : FishesEnum.values()) {
            mValueContainer.fishes.put(val.name(), prefs.getBoolean("fish_" + val, true));
        }

    }

    @Override
    public String getSharedPrefsName() {
        return Constants.SHARED_PREFS_NAME;
    }

    @Override
    public info.simplyapps.gameengine.rendering.data.ValueContainer getValueContainer() {
        return mValueContainer;
    }

    @Override
    public BasicEngine initializeEngine() {
        Properties properties = new Properties();
        properties.put(EngineConstants.GameProperties.RENDERING_SYSTEM, RenderingSystem.SINGLE_PLAYER);
        properties.put(EngineConstants.GameProperties.SCREEN_SCALE, 0);
        properties.put(EngineConstants.GameProperties.LEVEL, 0);
        properties.put(EngineConstants.GameProperties.SPACE_LR, 0);
        properties.put(EngineConstants.GameProperties.SPACE_TB, 0);

        return new FishesRenderer(getApplicationContext(), properties, mValueContainer);
    }
}