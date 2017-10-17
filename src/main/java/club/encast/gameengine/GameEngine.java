package club.encast.gameengine;

import club.encast.gameengine.logger.GameLogger;
import club.encast.gameengine.timer.Timer;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public abstract class GameEngine {

    private Plugin plugin;
    private String internalName;
    private String publicName;
    private GameLogger gameLogger;
    private Timer timer;
    private Location lobbyLocation;

    private int minPlayers;
    private int maxPlayers;

    private List<GameState> gameStates;
    private Set<UUID> gamePlayers;
    private Set<UUID> spectators;

    public GameEngine(Plugin plugin, String internalName, String publicName) {
        Validate.noNullElements(Arrays.asList(plugin, internalName, publicName));

        this.plugin = plugin;
        this.internalName = internalName;
        this.publicName = publicName;
        this.gameLogger = new GameLogger(this);
        this.timer = new Timer(this);

        this.minPlayers = 4;
        this.maxPlayers = 24;

        this.gameStates = new ArrayList<>();
        this.gamePlayers = new HashSet<>();
        this.spectators = new HashSet<>();

        getGameLogger().info("Setup game " + internalName + " (" + publicName + ")");
    }

    public final Plugin getPlugin() {
        return plugin;
    }

    public final String getInternalName() {
        return internalName;
    }

    public final String getPublicName() {
        return publicName;
    }

    public final GameLogger getGameLogger() {
        return gameLogger;
    }

    public final Timer getTimer() {
        return timer;
    }

    public final Location getLobbyLocation() {
        return lobbyLocation;
    }

    public final void setLobbyLocation(Location lobbyLocation) {
        this.lobbyLocation = lobbyLocation;
    }

    public final int getMinPlayers() {
        return minPlayers;
    }

    public final void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public final int getMaxPlayers() {
        return maxPlayers;
    }

    public final void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public final List<GameState> getGameStates() {
        return gameStates;
    }

    public Set<UUID> getGamePlayers() {
        return gamePlayers;
    }

    public final void setGamePlayer(UUID uuid) {
        gamePlayers.add(uuid);
    }

    public final void removeGamePlayer(UUID uuid) {
        gamePlayers.remove(uuid);
    }

    public Set<UUID> getSpectators() {
        return spectators;
    }

    public void setSpectator(Player player) {
        spectators.add(player.getUniqueId());
    }

    public final void removeSpectator(Player player) {
        spectators.remove(player.getUniqueId());
    }

    public void sendGamePlayerMessage(String message) {
        gamePlayers.forEach(uuid -> {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) {
                p.sendMessage(message);
            }
        });
    }

     public void sendSpectatorMessage(String message) {
         spectators.forEach(uuid -> {
             Player p = Bukkit.getServer().getPlayer(uuid);
             if(p != null) {
                 p.sendMessage(message);
             }
         });
     }

    public final void broadcast(String message) {
        sendGamePlayerMessage(message);
        sendSpectatorMessage(message);
        getGameLogger().info("Broadcast: " + message);
    }
}
