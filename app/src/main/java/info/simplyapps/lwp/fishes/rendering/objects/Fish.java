package info.simplyapps.lwp.fishes.rendering.objects;


public class Fish {

    public long speedMin;
    public long speedMax;
    public float screenWidthScaling;

    public Fish(long speedMin, long speedMax, float screenWidthScaling) {
        this.speedMin = speedMin;
        this.speedMax = speedMax;
        this.screenWidthScaling = screenWidthScaling;
    }

}
