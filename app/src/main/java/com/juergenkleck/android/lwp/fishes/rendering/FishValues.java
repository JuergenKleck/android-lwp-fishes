package com.juergenkleck.android.lwp.fishes.rendering;

import com.juergenkleck.android.gameengine.system.GameValues;
import com.juergenkleck.android.lwp.fishes.R;
import com.juergenkleck.android.lwp.fishes.preference.FishesEnum;
import com.juergenkleck.android.lwp.fishes.preference.WallpaperEnum;
import com.juergenkleck.android.lwp.fishes.rendering.objects.Fish;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class FishValues extends GameValues {

    public static final long layerSwitch = 10000l;
    public static final long layerSwitchMin = 3000l;

    public static final int fishMovement = 5;
    // lower = faster
    public static final int highestAnimationSpeed = 150;
    public static final int lowestAnimationSpeed = 75;

    // higher = faster
    public static final int smallFishMinSpeed = 5;
    public static final int smallFishMaxSpeed = 6;
    public static final int mediumFishMinSpeed = 25;
    public static final int mediumFishMaxSpeed = 50;
    public static final int largeFishMinSpeed = 50;
    public static final int largeFishMaxSpeed = 75;
    public static final int bigFishMinSpeed = 75;
    public static final int bigFixMaxSpeed = 100;

    // rotation values
    public static final float minRotation = 25.0f;
    public static final float maxRotation = 45.0f;
    public static final double distanceRevert = 0.2;
    public static final float distanceModificator = 1.2f;
    public static final float startRotation = 0.5f;

    // Y coordinate
    public static final int maxLayers = 28;
    // X coordinate
    public static final int maxWaypoints = 30;

    // amount of values equals amount of enum settings
    // neon, bluetang, discus
    public static final int[][] maxFishes = {
            // 1 fish type
            {10, 8, 5, 10},
            // 2 fish type
            {7, 5, 3, 5},
            // 3 fish type
            {6, 4, 2, 4},
            // 4 fish type
            {5, 3, 2, 3}

    };
    public static final float[] circlePos = {0.05f, 0.10f, 0.25f, 0.10f};
    public static final int[] circleY = {150, 175, 250, 175};
//	public static final int[] relativeY = { 5, 10, 30 };

    // switched by the enum setting
    public static final int[][][] fishGroups = {
            { // neon
                    {R.drawable.neon_ltr_0, R.drawable.neon_ltr_1, R.drawable.neon_ltr_2}
            }
            , { // bluetang
            {R.drawable.bluetang_ltr_0, R.drawable.bluetang_ltr_1, R.drawable.bluetang_ltr_2}
            , {R.drawable.powderbluetang_ltr_0, R.drawable.powderbluetang_ltr_1, R.drawable.powderbluetang_ltr_2}
    }
            , { // discus
            {R.drawable.discus1_ltr_0, R.drawable.discus1_ltr_1, R.drawable.discus1_ltr_2}
            , {R.drawable.discus2_ltr_0, R.drawable.discus2_ltr_1, R.drawable.discus2_ltr_2}
            , {R.drawable.discus3_ltr_0, R.drawable.discus3_ltr_1, R.drawable.discus3_ltr_2}
            , {R.drawable.discus4_ltr_0, R.drawable.discus4_ltr_1, R.drawable.discus4_ltr_2}
            , {R.drawable.discus5_ltr_0, R.drawable.discus5_ltr_1, R.drawable.discus5_ltr_2}
            , {R.drawable.discus6_ltr_0, R.drawable.discus6_ltr_1, R.drawable.discus6_ltr_2}
            , {R.drawable.discus7_ltr_0, R.drawable.discus7_ltr_1, R.drawable.discus7_ltr_2}
    }
            , { // cichlid
            {R.drawable.cichlid1_0, R.drawable.cichlid1_1, R.drawable.cichlid1_2}
            , {R.drawable.cichlid2_0, R.drawable.cichlid2_1, R.drawable.cichlid2_2}
            , {R.drawable.cichlid3_0, R.drawable.cichlid3_1, R.drawable.cichlid3_2}
            , {R.drawable.cichlid4_0, R.drawable.cichlid4_1, R.drawable.cichlid4_2}
    }
    };

    // per fish enum setting
    public static final Fish[] fishes = {
            // min speed, max speed, scaling
            new Fish(smallFishMinSpeed, smallFishMaxSpeed, 0.15f)
            , new Fish(smallFishMinSpeed, smallFishMaxSpeed, 0.15f)
            , new Fish(largeFishMinSpeed, largeFishMaxSpeed, 0.25f)
            , new Fish(mediumFishMinSpeed, mediumFishMaxSpeed, 0.25f)
    };

    // the int for the enums
    public static final int EXPANSION_SET1 = 0;

    public static final FishesEnum[][] EXPANSIONS_FISHES = {
            {FishesEnum.bluetang, FishesEnum.discus} // set 1
    };
    public static final WallpaperEnum[][] EXPANSIONS_WP = {
            {WallpaperEnum.reef1, WallpaperEnum.reef2, WallpaperEnum.rendered} // set 1
    };

    public static final int[] wallpaper = {
            R.drawable.bg_bluewater, R.drawable.bg_reef1, R.drawable.bg_reef2, R.drawable.bg_bluewater2, R.drawable.bg_reef3, -1
    };

}
