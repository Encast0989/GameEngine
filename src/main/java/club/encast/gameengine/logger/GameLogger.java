package club.encast.gameengine.logger;

import club.encast.gameengine.GameEngine;

public class GameLogger {

    private GameEngine engine;

    public GameLogger(GameEngine engine) {
        this.engine = engine;
    }

    public void info(String message) {
        engine.getPlugin().getLogger().info("(" + engine.getPublicName() + ") " + message);
    }

    public void warning(String message) {
        engine.getPlugin().getLogger().warning("(" + engine.getPublicName() + ") " + message);
    }

    public void error(String message) {
        engine.getPlugin().getLogger().severe("(" + engine.getPublicName() + ") " + message);
    }
}
