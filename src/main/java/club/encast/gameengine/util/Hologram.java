package club.encast.gameengine.util;

import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Hologram {

    private List<ArmorStand> holograms;

    public Hologram() {
        this.holograms = new ArrayList<>();
    }

    public List<ArmorStand> getHolograms() {
        return holograms;
    }

    public void addHologram(String text) {

    }

    public void update() {

    }
}
