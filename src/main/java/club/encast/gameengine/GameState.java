package club.encast.gameengine;

import org.apache.commons.lang.Validate;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public abstract class GameState {

    private String internalName;
    private String publicName;
    private long timeLength;
    private boolean infinite;

    public GameState(String internalName, String publicName, long timeLength, TimeUnit unit) {
        Validate.noNullElements(Arrays.asList(internalName, publicName));
        this.internalName = internalName;
        this.publicName = publicName;
        this.timeLength = TimeUnit.SECONDS.convert(timeLength, unit);
        this.infinite = false;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getPublicName() {
        return publicName;
    }

    public long getDurationSeconds() {
        return timeLength;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public abstract void onStateBegin(GameEngine engine);

    public abstract void onStateTick(GameEngine engine);

    public abstract void onStateEnd(GameEngine engine);
}
