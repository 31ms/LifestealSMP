package xyz.propsik.lifesteal.Items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.propsik.lifesteal.Lifesteal;

import java.util.List;

public class CraftedHeart {
    private static final ItemStack craftedHeart = new ItemStack(Material.RED_DYE);
    private static final NamespacedKey key = new NamespacedKey(Lifesteal.getInstance(), "crafted_heart");
    public CraftedHeart() {
        ItemMeta meta = craftedHeart.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.RED + "Crafted Heart");
        meta.setLore(List.of(ChatColor.GOLD + "Right click to gain a heart!", ChatColor.DARK_GRAY + "Maximum of 10 crafted hearts."));
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        persistentDataContainer.set(Lifesteal.getKey(), PersistentDataType.STRING, "crafted_heart");
        craftedHeart.setItemMeta(meta);

        if(Bukkit.getRecipe(key) == null) {
            ShapedRecipe recipe = new ShapedRecipe(key, getItem());
            recipe.shape("SNS", "NWN", "SNS");
            recipe.setIngredient('S', Material.NAUTILUS_SHELL);
            recipe.setIngredient('N', Material.NETHERITE_INGOT);
            recipe.setIngredient('W', Material.WITHER_SKELETON_SKULL);
            Bukkit.addRecipe(recipe);
        }
    }
    public static ItemStack getItem() {
        return craftedHeart;
    }
}
