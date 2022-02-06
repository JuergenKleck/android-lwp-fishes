package com.juergenkleck.android.lwp.fishes.rendering;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.juergenkleck.android.gameengine.rendering.kits.ScreenKit;
import com.juergenkleck.android.gameengine.rendering.objects.Coord;
import com.juergenkleck.android.gameengine.rendering.objects.Graphic;
import com.juergenkleck.android.gameengine.system.GameState;
import com.juergenkleck.android.gameengine.system.GameSubState;
import com.juergenkleck.android.lwp.fishes.Constants;
import com.juergenkleck.android.lwp.fishes.engine.FishEngine;
import com.juergenkleck.android.lwp.fishes.preference.FishesEnum;
import com.juergenkleck.android.lwp.fishes.preference.ValueContainer;
import com.juergenkleck.android.lwp.fishes.preference.WallpaperEnum;
import com.juergenkleck.android.lwp.fishes.rendering.kits.AnimationKit;
import com.juergenkleck.android.lwp.fishes.rendering.objects.FishAnimation;
import com.juergenkleck.android.lwp.fishes.sprites.FishSprites;

/**
 * Android app - FishesLWP
 *
 * Copyright 2022 by Juergen Kleck <develop@juergenkleck.com>
 */
public class FishesRenderer extends FishRendererTemplate implements FishEngine {

    private List<List<Coord>> waypoints;

    private long layerTime;
    private int layer;
    private int layerWaypointSwitch;
    private float circlePos = 0.0f;
    private ValueContainer mValueContainer;

    /**
     * The state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN
     */
    private GameState mMode;
    private GameSubState mSubMode;

    private boolean swimInSchools = Constants.SETTING_SWIM_SCHOOLS_DEFAULT;
    private int swimSpeed = Constants.SETTING_SWIM_SPEED_DEFAULT;
    private boolean swimRealistic = Constants.SETTING_SWIM_REALISTIC_DEFAULT;

    Random rnd;

    public FishesRenderer(Context context, Properties properties, ValueContainer valueContainer) {
        super(context, properties);
        mValueContainer = valueContainer;
    }

    @Override
    public void doInitThread(long time) {
        mMode = GameState.NONE;
        mSubMode = GameSubState.NONE;
        super.sprites = new FishSprites();
        waypoints = new ArrayList<List<Coord>>();

        rnd = new Random();
    }

    /**
     * Starts the game, setting parameters for the current difficulty.
     */
    public void doStart() {
        if (mMode == GameState.NONE) {
            setMode(GameState.INIT);
        }
    }

    /**
     * Pauses the physics update & animation.
     */
    public synchronized void pause() {
        saveGameState();
        setSubMode(GameSubState.PAUSE);
    }

    /**
     * Resumes from a pause.
     */
    public synchronized void unpause() {
        //set state back to running
        setSubMode(GameSubState.NONE);
    }

    public synchronized void exit() {
        super.exit();
        getSprites().clean();
    }

    public synchronized void create() {
        super.create();
    }

    public synchronized void restoreGameState() {
        log("restoreGameState()");
    }

    public synchronized void saveGameState() {
        log("saveGameState()");
    }

    @Override
    public float getCharSpacing() {
        return 0;
    }

    /**
     * Restores game state from the indicated Bundle. Typically called when the
     * Activity is being restored after having been previously destroyed.
     *
     * @param savedState Bundle containing the game state
     */
    public synchronized void restoreState(Bundle savedState) {
        setMode(GameState.INIT);
        restoreGameState();
    }

    /**
     * Dump game state to the provided Bundle. Typically called when the
     * Activity is being suspended.
     *
     * @return Bundle with this view's state
     */
    public Bundle saveState(Bundle map) {
        if (map != null) {
            saveGameState();
        }
        return map;
    }

    @Override
    public void doUpdateRenderState() {
        switch (mMode) {
            case NONE: {
                // move to initialization
                setMode(GameState.INIT);
                setSubMode(GameSubState.NONE);
            }
            break;
            case INIT: {
                // setup the game
                swimInSchools = mValueContainer.swimSchool;
                swimSpeed = mValueContainer.swimSpeed;
                swimRealistic = mValueContainer.swimRealistic;

                waypoints.clear();
                initGraphics();

                setMode(GameState.READY);
            }
            break;
            case READY: {
                setMode(GameState.PLAY);
            }
            break;
            case PLAY: {
                // active gameplay

                // update graphic positions
                updatePhysics();

            }
            break;
            case END: {

            }
            break;
            default:
                setMode(GameState.NONE);
                break;
        }

    }

    /**
     * Used to signal the thread whether it should be running or not. Passing
     * true allows the thread to run; passing false will shut it down if it's
     * already running. Calling start() after this was most recently called with
     * false will result in an immediate shutdown.
     *
     * @param b true to run, false to shut down
     */
    public void setRunning(boolean b) {
        super.setRunning(b);
    }

    public boolean onTouchEvent(MotionEvent event) {
        final long time = System.currentTimeMillis();

        if (mMode == GameState.PLAY) {
            if (mSubMode == GameSubState.NONE) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:

                        if (getSprites().aFish.size() <= 0) {
                            break;
                        }

                        int x = Float.valueOf(event.getX()).intValue();
                        int y = Float.valueOf(event.getY()).intValue();

                        int radiusX = ScreenKit.scaleWidth(50, screenWidth);
                        int radiusY = ScreenKit.scaleHeight(50, screenHeight);

                        Rect target = new Rect(x - radiusX, y - radiusY, x + radiusX, y + radiusY);

                        boolean found = false;
                        int l = 0;
                        // detect waypoint
                        for (List<Coord> coords : waypoints) {
                            for (Coord coord : coords) {
                                if (target.contains(coord.x, coord.y)) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found) break;
                            l++;
                        }
                        if (found) {
                            layerTime = time + FishValues.layerSwitch;
                            layer = l;
                            layerWaypointSwitch = getSprites().aFish.get(0).waypoint;
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        return true;
                    case MotionEvent.ACTION_MOVE:
                        // move
                        break;
                }
            }
        }
        return true;
    }

    /**
     * Update the graphic x/y values in real time. This is called before the
     * draw() method
     */
    private void updatePhysics() {
        // the fixed time for drawing this frame
        final long time = System.currentTimeMillis();

        if (mMode == GameState.PLAY) {

            if (mSubMode == GameSubState.NONE) {


                // create missing fishes
                createFishes(screenWidth, screenHeight, time);

                // only if there are fish
                if (getSprites().aFish.size() > 0) {
                    if (layerTime == 0) {
                        layerTime = time + FishValues.layerSwitch;
                    }

                    if (layerTime < time) {
                        layer = rnd.nextInt(FishValues.maxLayers);
                        if (layer > FishValues.maxLayers) {
                            layer = 0;
                        }
                        layerTime = time + FishValues.layerSwitch;
                        if (getSprites().aFish.size() > 0) {
                            layerWaypointSwitch = getSprites().aFish.get(0).waypoint;
                        }
                    }
                }

                // fish animation - move fish
                for (FishAnimation a : getSprites().aFish) {
                    a.nextFrame(time);
                    // movetime expired - move fish, set new movetime
                    if (a.moveTime < time) {

                        // calculate fish here dynamically as we use a endless loop
                        int fishMove = ScreenKit.scaleWidth(Long.valueOf(FishValues.fishMovement).intValue(), screenWidth);
                        if (fishMove < 1) {
                            fishMove = 1;
                        }

                        // move fish toward next waypoint
                        Coord target = waypoints.get(a.waypointLayer).get(a.waypoint);

                        if (a.coord.x < target.x) {
                            a.ltr = true;
                        } else {
                            a.ltr = false;
                        }

                        if (a.coord.x <= target.x) {
                            a.coord.x += fishMove;
                        } else {
                            a.coord.x -= fishMove;
                        }
                        int y = a.coord.y + a.yMod;
                        // fish movement
                        if (y > target.y && (y - fishMove > target.y)) {
                            a.coord.y -= fishMove;
                        } else if (y < target.y && (y + fishMove < target.y)) {
                            a.coord.y += fishMove;
                        }

                        if (swimRealistic) {
                            // rotation calculation
                            if (y > target.y && (y - fishMove > target.y)) {
                                double distance = Math.sqrt(Math.pow(target.y - a.coord.y, 2) + Math.pow(target.x - a.coord.x, 2));
                                if (a.distance * FishValues.distanceRevert < distance) {
                                    a.angle *= FishValues.distanceModificator;
                                } else {
                                    a.angle /= FishValues.distanceModificator;
                                }
                                if (a.ltr && a.angle > 0f) {
                                    a.angle *= -1.0f;
                                } else if (!a.ltr && a.angle < 0f) {
                                    a.angle *= -1.0f;
                                }
                            } else if (y < target.y && (y + fishMove < target.y)) {
                                double distance = Math.sqrt(Math.pow(target.y - a.coord.y, 2) + Math.pow(target.x - a.coord.x, 2));
                                if (a.distance * FishValues.distanceRevert < distance) {
                                    a.angle *= FishValues.distanceModificator;
                                } else {
                                    a.angle /= FishValues.distanceModificator;
                                }
                                if (a.ltr && a.angle < 0f) {
                                    a.angle *= -1.0f;
                                } else if (!a.ltr && a.angle > 0f) {
                                    a.angle *= -1.0f;
                                }
                            } else {
                                a.angle = 0.0f;
                            }
                            if (a.angle > a.maxAngle) {
                                a.angle = a.maxAngle;
                            } else if (a.angle < 0.0f && a.angle * -1f > a.maxAngle) {
                                a.angle = a.maxAngle * -1f;
                            }
                        }

                        // limit to the bottom 15% of the screen as there might be an on-screen button overlay which will cause rendering problems
                        if (a.coord.y > Integer.valueOf(screenHeight).floatValue() * 0.75f) {
                            a.coord.y = Float.valueOf(Integer.valueOf(screenHeight).floatValue() * 0.75f).intValue();
                            a.angle = 0.0f;
                        }

                        // z coordinate is the 360 flattened circle for the fish swimming left to right
                        a.coord.z = target.z;

                        // update next move time
                        a.moveTime = time + a.fishSpeed;

                        // check for waypoint arrival
                        if (
                            // x
                                ((a.ltr && a.coord.x >= target.x) || (!a.ltr && a.coord.x <= target.x))
                                        &&
                                        // y
                                        ((y < target.y && (y - fishMove < target.y)) || (y > target.y && (y + fishMove > target.y)))
                        ) {
                            a.waypoint++;
                            if (a.waypoint >= waypoints.get(a.waypointLayer).size()) {
                                a.waypoint = 0;
                            }
                            if (swimInSchools) {
                                // group all fish together for the next layer
                                if (a.waypoint >= layerWaypointSwitch && a.waypointLayer != layer) {
                                    if (swimRealistic) {
                                        // calculate a fish specific rotation value
                                        a.angle = FishValues.startRotation;
                                        a.maxAngle = rnd.nextFloat() * 100f;
                                        while (a.maxAngle < FishValues.minRotation || a.maxAngle > FishValues.maxRotation) {
                                            a.maxAngle = rnd.nextFloat() * 100f;
                                        }
                                    }
                                    a.waypointLayer = layer;
                                    if (swimRealistic) {
                                        // rotation calculation
                                        target = waypoints.get(a.waypointLayer).get(a.waypoint);
                                        a.distance = Math.sqrt(Math.pow(target.y - a.coord.y, 2) + Math.pow(target.x - a.coord.x, 2));
                                    }
                                }
                            } else {
                                // each fish can swim free
                                if (a.layertime < time) {
                                    layer = rnd.nextInt(FishValues.maxLayers);
                                    if (layer > FishValues.maxLayers) {
                                        layer = 0;
                                    }
                                    long nextLayer = Integer.valueOf(rnd.nextInt(Long.valueOf(FishValues.layerSwitch).intValue())).longValue();
                                    if (nextLayer < FishValues.layerSwitchMin) {
                                        nextLayer = FishValues.layerSwitchMin;
                                    }
                                    a.layertime = time + nextLayer;
                                    if (swimRealistic) {
                                        // calculate a fish specific rotation value
                                        a.angle = FishValues.startRotation;
                                        a.maxAngle = rnd.nextFloat() * 100f;
                                        while (a.maxAngle < FishValues.minRotation || a.maxAngle > FishValues.maxRotation) {
                                            a.maxAngle = rnd.nextFloat() * 100f;
                                        }
                                    }

                                    a.waypointLayer = layer;
                                    if (swimRealistic) {
                                        // rotation calculation
                                        target = waypoints.get(a.waypointLayer).get(a.waypoint);
                                        a.distance = Math.sqrt(Math.pow(target.y - a.coord.y, 2) + Math.pow(target.x - a.coord.x, 2));
                                    }
                                }

                            }
                        }

                    }

                    // fish moved outside of the screen - initialize again ( by removing the fish )
                    if ((a.ltr && a.rect.left > screenWidth + a.rect.width()) || (!a.ltr && a.rect.right < 0)) {
                        a.ltr = !a.ltr;
                    }
                }
            }
        }
    }

    /**
     * Draws the graphics onto the Canvas.
     */
    @Override
    public void doDrawRenderer(Canvas canvas) {
        if (getSprites().pBackground != null) {
            canvas.drawRect(getSprites().rBackground, getSprites().pBackground);
        }
        if (getSprites().gBackground != null) {
            getSprites().gBackground.image.draw(canvas);
        }

        // the fixed time for drawing this frame
//        final long time = System.currentTimeMillis();

        if (mMode == GameState.PLAY) {

            if (mSubMode == GameSubState.NONE) {


                // Show debug - waypoints
//              Paint p = new Paint();
//              p.setARGB(255, 255, 255, 0);
//              p.setStyle(Style.FILL_AND_STROKE);
//              for(List<Coord> coords : waypoints) {
//                  for(Coord coord : coords) {
//                      canvas.drawCircle(coord.x, coord.y, 1.5f, p);
//                  }
//              }

                // fish animation - draw fishes
                for (FishAnimation a : getSprites().aFish) {
                    canvas.save();
                    if (swimRealistic) {
                        canvas.rotate(a.angle, a.coord.x, a.coord.y);
                    }
                    if (a.ltr) {
                        a.rect.offsetTo(a.coord.x, a.coord.y);
                        getSprites().gFish[a.nextFrame().gReference].image.setBounds(a.rect);
                        if (getSprites().gFish[a.nextFrame().gReference].image.getBounds().bottom < Integer.valueOf(screenHeight).floatValue() * 0.90f)
                            getSprites().gFish[a.nextFrame().gReference].image.draw(canvas);
                    } else {
                        // inverse screen coordinate
                        a.rect.offsetTo(screenWidth - a.coord.x, a.coord.y);
                        // flip screen
                        canvas.scale(-1.0f, 1.0f);
                        // translate to new screenview
                        canvas.translate(-screenWidth, 0);
                        getSprites().gFish[a.nextFrame().gReference].image.setBounds(a.rect);
                        if (getSprites().gFish[a.nextFrame().gReference].image.getBounds().bottom < Integer.valueOf(screenHeight).floatValue() * 0.90f)
                            getSprites().gFish[a.nextFrame().gReference].image.draw(canvas);
                    }
                    canvas.restore();
                }


            }

        }

        if (mSubMode == GameSubState.PAUSE) {

        }

        if (mMode == GameState.READY) {
        }

        if (mMode == GameState.END) {
        }

        if (mMode == GameState.END && mSubMode == GameSubState.SYNC_COMPLETE) {
        }

    }

    public synchronized void setMode(GameState mode) {
        synchronized (mMode) {
            mMode = mode;
        }
    }

    public synchronized GameState getMode() {
        return mMode;
    }

    public synchronized void setSubMode(GameSubState mode) {
        mSubMode = mode;
    }

    public synchronized GameSubState getSubMode() {
        return mSubMode;
    }

    @Override
    public void actionHandler(int action) {
        // handle click actions directly to the game screen
    }

    public FishSprites getSprites() {
        return FishSprites.class.cast(super.sprites);
    }

    private void initGraphics() {

        // Draw backgrounds

        int drawBG = FishValues.wallpaper[WallpaperEnum.valueOf(mValueContainer.wallpaper).number];
        if (drawBG > -1) {
            getSprites().gBackground = loadGraphic(drawBG, 0, 0);
            getSprites().gBackground.image.setBounds(0, 0, screenWidth, realScreenHeight);
            getSprites().pBackground = null;
        } else {
            getSprites().gBackground = null;
            Shader shader = new LinearGradient(0, 0, 0, realScreenHeight, Color.parseColor("#88c1d2"), Color.parseColor("#005aa2"), TileMode.CLAMP);
            getSprites().pBackground = new Paint();
            getSprites().pBackground.setShader(shader);
            getSprites().rBackground = new Rect(0, 0, screenWidth, realScreenHeight);
        }

        // scan all groups
        int size = 0;
        for (int i = 0; i < FishValues.fishGroups.length; i++) {
            size = size + FishValues.fishGroups[i].length * 3;
        }
        getSprites().gFish = new Graphic[size];

        // load all fishes
        for (int s = 0, g = 0; g < FishValues.fishGroups.length; g++) {
            boolean create = false;
            // only load fish graphic if checked
            for (FishesEnum val : FishesEnum.values()) {
                Boolean b = mValueContainer.fishes.get(val.name());
                if (b != null && b.booleanValue() && g == val.number) {
                    create = true;
                }
            }

            for (int i = 0; i < FishValues.fishGroups[g].length; i++) {
                for (int j = 0; j < 3; j++, s++) {
                    getSprites().gFish[s] = create ? loadGraphic(FishValues.fishGroups[g][i][j], 0, 0) : null;
                }
            }
        }

        layer = 0;
        createWaypoints();

        if (getSprites().aFish != null) {
            getSprites().aFish.clear();
        }
    }

    /**
     * Create a 3 dimensional waypoint map with 3 circles and connection paths
     */
    private void createWaypoints() {

        final float fishWidth = Float.valueOf(screenWidth * FishValues.fishes[0].screenWidthScaling);

        int topLayer = (screenHeight - (screenHeight / FishValues.maxLayers)) / FishValues.maxLayers;
        int wp = FishValues.maxWaypoints;
        int halfwp = wp / 2;

        for (int layer = 0; layer < FishValues.maxLayers; layer++) {
            List<Coord> coords = new ArrayList<Coord>();
            // create a 360 circle around the width
            // j is used to generate a z-coordinate
            for (int i = 0, j = 0; i < wp; i++) {
                Coord c = new Coord();
                c.x = Math.round(Float.valueOf((screenWidth + fishWidth * 2.5f) / halfwp * j) - fishWidth);
                c.y = topLayer + (layer * topLayer);
                c.z = 1.0f - Double.valueOf(Math.sin(Float.valueOf(j) / 90)).floatValue();
                coords.add(c);
                if (i > halfwp) {
                    j--;
                } else {
                    j++;
                }
            }
            waypoints.add(coords);
        }
    }

    /**
     * Create fishes Assume 80% of the screenheight are available for fishes to
     * start the swimming ltr and rtl
     *
     * @param screenWidth
     * @param screenHeight
     * @param time
     */
    private void createFishes(int screenWidth, int screenHeight, long time) {

        int maxTypes = -1;
        for (FishesEnum val : FishesEnum.values()) {
            Boolean b = mValueContainer.fishes.get(val.name());
            if (b != null && b) {
                maxTypes++;
            }
        }

        int max = 0;
        int count = 0;

        if (maxTypes >= 0) {
            for (FishesEnum val : FishesEnum.values()) {
                Boolean b = mValueContainer.fishes.get(val.name());
                if (b != null && b) {
                    count = countFishes(val);
                    max = FishValues.maxFishes[maxTypes][val.number];
                    if (count < max) {
                        internalCreateFishes(val, max - count, screenWidth, screenHeight, time);
                    }
                }
            }
        }
    }

    private int countFishes(FishesEnum val) {
        int fishes = 0;
        for (FishAnimation a : getSprites().aFish) {
            if (a.type.equals(val)) {
                fishes++;
            }
        }
        return fishes;
    }

    private void internalCreateFishes(FishesEnum val, int count, int screenWidth, int screenHeight, long time) {
        // animations
        for (int i = 0; i < count; i++) {
            boolean ltr = true;
            if (!swimInSchools) {
                ltr = rnd.nextBoolean();
            }
            // get the fish graphic from all graphics
            int minRef = 0;
            for (int g = 0; g < val.number; g++) {
                minRef += FishValues.fishGroups[g].length;
            }

            int fish = rnd.nextInt(FishValues.fishGroups[val.number].length + minRef);
            if (fish < minRef) {
                fish = minRef;
            }
            // multiply with images per fish to have the correct image position in the flattened array
            fish *= 3;

            // get animation speed
            int rand = rnd.nextInt(FishValues.highestAnimationSpeed);
            if (rand < FishValues.lowestAnimationSpeed) {
                rand = FishValues.lowestAnimationSpeed;
            }
            int fishSpeed = rnd.nextInt(Long.valueOf(FishValues.fishes[val.number].speedMax).intValue());
            while (fishSpeed < Long.valueOf(FishValues.fishes[val.number].speedMin).intValue()) {
                fishSpeed = rnd.nextInt(Long.valueOf(FishValues.fishes[val.number].speedMax).intValue());
            }

            // update speed with round increasement
            fishSpeed = Float.valueOf(fishSpeed).intValue();

            // change speed by setting
            fishSpeed = Double.valueOf(Math.floor(Integer.valueOf(fishSpeed).doubleValue() / (Integer.valueOf(swimSpeed).doubleValue() / 100.0))).intValue();

            rand += fishSpeed;

            // initialize animation
            getSprites().aFish.add(new FishAnimation());
            getSprites().aFish.get(getSprites().aFish.size() - 1).fishSpeed = Integer.valueOf(fishSpeed).longValue();
            getSprites().aFish.get(getSprites().aFish.size() - 1).moveTime = time + getSprites().aFish.get(getSprites().aFish.size() - 1).fishSpeed;
            getSprites().aFish.get(getSprites().aFish.size() - 1).type = val;

            int school = getNextSchoolNumber();
            // first fish builds leader in school
            AnimationKit.addFishAnimation(getSprites().aFish.get(getSprites().aFish.size() - 1), fish + 0, rand, 0, school);
            AnimationKit.addFishAnimation(getSprites().aFish.get(getSprites().aFish.size() - 1), fish + 1, rand, 0, school);
            AnimationKit.addFishAnimation(getSprites().aFish.get(getSprites().aFish.size() - 1), fish + 2, rand, 0, school);
            calculateStartPosition(getSprites().aFish.get(getSprites().aFish.size() - 1), ltr, screenWidth, screenHeight);
        }
    }

    private int getLastSchoolNumber() {
        int school = -1;
        if (getSprites().aFish.size() > 1) {
            for (int i = 0; i < getSprites().aFish.size(); i++) {
                if (getSprites().aFish.get(i).schoolNumber > school) {
                    school = getSprites().aFish.get(i).schoolNumber;
                }
            }
        }
        return school;
    }

    private int getNextSchoolNumber() {
        return getLastSchoolNumber() + 1;
    }

    private Coord getPreviousFishPosition(int schoolNumber) {
        Coord bounds = null;
        if (getSprites().aFish.size() > 1) {
            for (int i = 0; i < getSprites().aFish.size(); i++) {
                if (getSprites().aFish.get(i).schoolNumber == schoolNumber - 1) {
                    bounds = getSprites().aFish.get(i).coord;
                }
            }
        }
        return bounds;
    }

    private void calculateStartPosition(FishAnimation a, boolean ltr, int screenWidth, int screenHeight) {
        a.ltr = ltr;
        a.waypoint = 0;
        a.waypointLayer = 0;

        if (!swimInSchools) {
            // determine starting position
            a.waypointLayer = rnd.nextInt(FishValues.maxLayers);
        }

        final int fishWidth = Float.valueOf(screenWidth * FishValues.fishes[a.type.number].screenWidthScaling).intValue();

        Coord target = waypoints.get(a.waypointLayer).get(a.waypoint);

//		boolean isLead = a.schoolNumber == 0;
        Coord previousFish = getPreviousFishPosition(a.schoolNumber);

        // get a cyclic cone of positions
        int screenHeightStart = target.y;
        int screenWidthStart = target.x;

        if (swimInSchools) {
            if (previousFish != null) {
                screenHeightStart = previousFish.y + Float.valueOf(ScreenKit.scaleHeight(FishValues.circleY[a.type.number], screenHeight) * circlePos).intValue();
                screenWidthStart = ltr ? previousFish.x + ScreenKit.scaleWidth(30, screenWidth) : previousFish.x - ScreenKit.scaleWidth(30, screenWidth);
                a.coord.z = circlePos;
                circlePos += FishValues.circlePos[a.type.number];
                if (circlePos > 1.0f) {
                    circlePos = -1.0f;
                }
            }
        } else {
            screenHeightStart = rnd.nextInt(screenHeight) + ScreenKit.scaleHeight(FishValues.circleY[a.type.number], screenHeight);
            screenWidthStart = ltr ? rnd.nextInt(screenWidth) + ScreenKit.scaleWidth(30, screenWidth) : rnd.nextInt(screenWidth) - ScreenKit.scaleWidth(30, screenWidth);
            a.coord.z = target.z;
        }

        a.coord.x = screenWidthStart;
        a.coord.y = screenHeightStart;
        a.yMod = Float.valueOf(Double.valueOf(Math.sin(screenHeightStart)).floatValue() * ScreenKit.scaleHeight(FishValues.circleY[a.type.number], screenHeight)).intValue();

        Rect current = getSprites().gFish[a.nextFrame().gReference].image.getBounds();

        // initialize the rect on the target fish size
        a.rect.set(0,
                0 - ((fishWidth * (current.bottom - current.top) / current.right)),
                0 + fishWidth,
                0);

    }

    @Override
    public void reset() {
        setMode(GameState.NONE);
    }


}
