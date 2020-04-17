package info.simplyapps.lwp.fishes.rendering;

import android.content.Context;

import java.util.Properties;

import info.simplyapps.gameengine.rendering.GenericRendererTemplate;

public abstract class FishRendererTemplate extends GenericRendererTemplate {

    public FishRendererTemplate(Context context, Properties properties) {
        super(context, properties);
    }

    public boolean logEnabled() {
        return false;
    }

}
