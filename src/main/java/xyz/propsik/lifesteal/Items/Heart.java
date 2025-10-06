package xyz.propsik.lifesteal.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.propsik.lifesteal.Lifesteal;

import java.util.List;

public class Heart {
    private static final ItemStack heart = new ItemStack(Material.NETHER_STAR);

    public Heart() {
        ItemMeta meta = heart.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "Heart");
        meta.setLore(List.of(ChatColor.GOLD + "Right click to gain a heart!"));
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        persistentDataContainer.set(Lifesteal.getKey(), PersistentDataType.STRING, "heart");
        heart.setItemMeta(meta);

    }
    public static ItemStack getItem() {
        return heart;
    }
}
