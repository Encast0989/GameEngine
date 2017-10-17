package club.encast.gameengine.util;

import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Collections;
import java.util.List;

public class ItemFactory implements Cloneable {

    private ItemStack item;

    public ItemFactory(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemFactory setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemFactory setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemFactory setDisplayName(String displayName) {
        Validate.notNull(displayName);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return this;
    }

    public ItemFactory setLore(List<String> lore) {
        Validate.notNull(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemFactory addLore(String lore) {
        Validate.notNull(lore);
        ItemMeta meta = item.getItemMeta();
        if(meta.getLore() != null) {
            meta.setLore(Collections.singletonList(lore));
        } else {
            meta.getLore().add(lore);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemFactory setColor(Color color) {
        Validate.notNull(color);
        if(item.getType().name().startsWith("LEATHER")) {
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(color);
        }
        return this;
    }

    public ItemStack create() {
        return item;
    }
}
