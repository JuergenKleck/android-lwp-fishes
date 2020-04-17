package info.simplyapps.lwp.fishes.preference;

import java.util.HashMap;
import java.util.Map;

public class ValueContainer extends info.simplyapps.gameengine.rendering.data.ValueContainer {

    public String wallpaper;
    public Map<String, Boolean> fishes;
    public boolean swimSchool;
    public boolean swimRealistic;
    public int swimSpeed;

    public ValueContainer() {
        fishes = new HashMap<String, Boolean>();
    }

}
