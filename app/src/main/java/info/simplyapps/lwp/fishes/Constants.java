package info.simplyapps.lwp.fishes;

import info.simplyapps.lwp.fishes.preference.WallpaperEnum;

public class Constants {

    public static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=";
    public static final String UPDATE_CHECK_URL = "http://game.simplyapps.info/fisheslswp.xml";

    public static final String DATABASE = "fisheslwp.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SHARED_PREFS_NAME = "fisheslwpprefs";

    public static final String SETTING_SWIM_SCHOOLS = "swim_school";
    public static final boolean SETTING_SWIM_SCHOOLS_DEFAULT = false;
    public static final String SETTING_WALLPAPER = "wallpaper_list";
    public static final String SETTING_WALLPAPER_DEFAULT = WallpaperEnum.bluewater.name();
    public static final String SETTING_SWIM_SPEED = "swim_speed";
    public static final int SETTING_SWIM_SPEED_DEFAULT = 75;
    public static final String SETTING_SWIM_REALISTIC = "swim_realistic";
    public static final boolean SETTING_SWIM_REALISTIC_DEFAULT = false;

}
