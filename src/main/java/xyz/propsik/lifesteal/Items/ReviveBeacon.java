package xyz.propsik.lifesteal.Items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.propsik.lifesteal.Lifesteal;

import java.util.List;
import java.util.Objects;

public class ReviveBeacon {
    private static final ItemStack ReviveBeacon = new ItemStack(Material.BEACON);
    private static final NamespacedKey key = new NamespacedKey(Lifesteal.getInstance(), "revive_beacon");

    public ReviveBeacon() {
        ItemMeta meta = ReviveBeacon.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Beacon of Life");
        meta.setLore(List.of(ChatColor.WHITE + "A beacon that can revive a player", ChatColor.WHITE + "when they run out of hearts."));
        meta.getPersistentDataContainer().set(Lifesteal.getKey(), PersistentDataType.STRING, "revive_beacon");
        ReviveBeacon.setItemMeta(meta);

        if(Bukkit.getRecipe(key) == null) {
            ShapedRecipe recipe = new ShapedRecipe(key, getItem());
            recipe.shape("HEH","RVC","HSH");
            recipe.setIngredient('H', Objects.requireNonNull(Heart.getItem().getData()));
            recipe.setIngredient('E', Material.ELYTRA);
            recipe.setIngredient('R', Material.RECOVERY_COMPASS);
            recipe.setIngredient('V', Material.HEAVY_CORE);
            recipe.setIngredient('C', Material.CONDUIT);
            recipe.setIngredient('S', Material.SKELETON_SKULL);
            Bukkit.addRecipe(recipe);
        }
    }
    public static ItemStack getItem() {
        return ReviveBeacon;
    }
}
