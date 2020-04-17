package info.simplyapps.lwp.fishes.rendering.kits;

import info.simplyapps.lwp.fishes.rendering.objects.FishAnimation;

public class AnimationKit extends info.simplyapps.gameengine.rendering.kits.AnimationKit {

    public static void addFishAnimation(FishAnimation animation, int gReference, int delay, int fishNumber, int schoolNumber) {
        addAnimation(animation, gReference, delay);
        animation.fishNumber = fishNumber;
        animation.schoolNumber = schoolNumber;
    }

}
