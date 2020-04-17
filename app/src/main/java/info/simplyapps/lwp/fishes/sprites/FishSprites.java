package info.simplyapps.lwp.fishes.sprites;

import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import info.simplyapps.gameengine.rendering.objects.Graphic;
import info.simplyapps.gameengine.sprites.ViewSprites;
import info.simplyapps.lwp.fishes.rendering.objects.FishAnimation;

public class FishSprites implements ViewSprites {

    // main
    public Graphic gBackground;
    public Paint pBackground;
    public Rect rBackground;

    public Graphic[] gFish;
    public List<FishAnimation> aFish;

    public FishSprites() {
        aFish = new ArrayList<FishAnimation>();
    }

    @Override
    public void init() {
    }

    @Override
    public void clean() {
    }

}
