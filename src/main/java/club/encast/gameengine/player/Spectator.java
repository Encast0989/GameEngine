package club.encast.gameengine.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Spectator {

    private UUID uuid;
    private Player player;
    private boolean speed;
    private boolean nightVision;

    public Spectator(UUID uuid) {
        this.uuid = uuid;
        this.player = Bukkit.getServer().getPlayer(uuid);
        this.speed = false;
        this.nightVision = false;

        if(player != null) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 1, false, false));
            player.sendMessage("§7You are now a player.");
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isSpeed() {
        return speed;
    }

    public void setSpeed(boolean speed) {
        if(player != null && speed) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1, false, false));
        }
        this.speed = speed;
    }

    public boolean isNightVision() {
        return nightVision;
    }

    public void setNightVision(boolean nightVision) {
        if(player != null && nightVision) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1, false, false));
        }
        this.nightVision = nightVision;
    }
}
