package info.simplyapps.lwp.fishes.rendering.objects;

import info.simplyapps.lwp.fishes.preference.FishesEnum;

public class FishAnimation extends info.simplyapps.gameengine.rendering.objects.Animation {

    public int fishNumber;
    // build a fish school
    public int schoolNumber;
    public long fishSpeed;
    // updated value
    public long moveTime;

    public int waypointLayer;
    public int waypoint;

    public float angle;
    public double distance;
    public float maxAngle;

    public int yMod;

    // swim in schools
    public long layertime;

    public FishesEnum type;

    public FishAnimation() {
        super();
    }

}
