package club.encast.gameengine;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class GEPlugin extends JavaPlugin {

    private static GEPlugin instance;

    @EventHandler
    public void onEnable() {
        instance = this;
        System.out.println("[GEPlugin] GameEngine is now available for use on servers as a plugin for non-shade implementations.");
    }

    public static GEPlugin getInstance() {
        return instance;
    }
}
