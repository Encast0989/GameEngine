package club.encast.gameengine.player;

import club.encast.gameengine.GameEngine;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SpectatorManager {

    private GameEngine engine;
    private Set<UUID> spectators;

    public SpectatorManager(GameEngine engine) {
        this.engine = engine;
        spectators = new HashSet<>();
    }

    public Set<UUID> getSpectators() {
        return spectators;
    }

    public void addSpectator(Player player) {
        spectators.add(player.getUniqueId());
    }

    public final void removeSpectator(Player player) {
        spectators.remove(player.getUniqueId());
    }

    public void openPlayerMenu() {
        //TODO: Finish code.
        //FINSH THIS
    }
}
