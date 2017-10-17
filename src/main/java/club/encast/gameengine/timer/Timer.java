package club.encast.gameengine.timer;

import club.encast.gameengine.GameEngine;
import club.encast.gameengine.GameState;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {

    private GameEngine engine;
    private GameState alwaysTickState;
    private int currentStateIndex;
    private long currentTime;
    private long elapsedTime;
    private boolean running;

    public Timer(GameEngine engine) {
        this.engine = engine;
        this.alwaysTickState = null;
        this.currentStateIndex = 0;
        this.currentTime = -1;
        this.elapsedTime = 0;
        this.running = true;
        this.runTaskTimer(engine.getPlugin(), 0, 20);
    }

    public GameState getAlwaysTickState() {
        return alwaysTickState;
    }

    public void setAlwaysTickState(GameState alwaysTickState) {
        this.alwaysTickState = alwaysTickState;
    }

    public int getCurrentStateIndex() {
        return currentStateIndex;
    }

    public void setCurrentStateIndex(int currentStateIndex) {
        this.currentStateIndex = currentStateIndex;
        this.currentTime = -1; // currentTime has to be -1 so that the incrementer goes to 0 and does all the tasks.
        GameState state = getCurrentState();
        if(state != null) {
            state.onStateBegin(engine);
            engine.getGameLogger().info("Started State: " + state.getInternalName());
        }
    }

    public GameState getCurrentState() {
         return engine.getGameStates().get(currentStateIndex);
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void nextState() {
        setCurrentStateIndex(currentStateIndex + 1);
    }

    public void resetStates() {
        setCurrentStateIndex(0);
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getRemainingTime() {
        GameState state = getCurrentState();
        if(state != null) {
            return state.getDurationSeconds() - (currentTime >= 0 ? currentTime : 0);
        }
        return -1;
    }

    public boolean isRunning() {
        engine.getGameLogger().info("Paused game " + engine.getInternalName() + " (" + engine.getPublicName() + ")");
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        // If the timer is running, the following will be checked and triggered:
        // **The always tick state is an exception to this rule as it runs all the time,
        //   even if the timer isn't running.
        // **The always tick state: if this object isn't null, GameState::onStateTick will be run.
        //
        // - if the current state is not null, the following will be run:
        //   - if the current time (in seconds) is greater than or equal to the state's duration in seconds AND
        //     if the state isn't infinite, GameState::onStateEnd will be run, the current ended state will be
        //     logged, and Timer::nextState will be run.
        //   - if the current time is not greater than or equal to the duration, then GameState::onStateTick will
        //     be run.
        //   - elapsedTime and currentTime will be incremented.
        if(alwaysTickState != null) alwaysTickState.onStateTick(engine);
        if(running) {
            GameState state = getCurrentState();
            if(state != null) {
                elapsedTime++;
                currentTime++;
                state.onStateTick(engine);
                if(currentTime >= state.getDurationSeconds() && !state.isInfinite()) {
                    state.onStateEnd(engine);
                    engine.getGameLogger().info("Ended State: " + state.getInternalName());
                    nextState();
                }
            }
        }
    }
}
