package xyz.propsik.lifesteal.Listeners;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import xyz.propsik.lifesteal.Items.ReviveBeacon;
import xyz.propsik.lifesteal.Lifesteal;
import xyz.propsik.lifesteal.Utilities;

import java.util.List;
import java.util.Objects;

public class ItemListeners implements Listener {
    @EventHandler
    private void onRightClick(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
            PersistentDataContainer persistentDataContainer = Objects.requireNonNull(e.getPlayer().getInventory().getItemInMainHand().getItemMeta()).getPersistentDataContainer();
            String keyValue = persistentDataContainer.get(Lifesteal.getKey(), PersistentDataType.STRING);
            if (keyValue == null) return;
            switch (keyValue) {
                case "heart":
                    if (Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).getBaseValue() < 20 * 2) {
                        Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).getBaseValue() + 2.0);
                        p.sendMessage(ChatColor.GREEN + "You gained a heart!");
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else {
                        p.sendMessage(ChatColor.RED + "You already have the maximum amount of hearts!");
                    }
                    break;
                case "revive_beacon":
                    e.setCancelled(true);
                    Inventory beacon = Bukkit.createInventory(null, 54, Utilities.color("&b&lBeacon of Life"));
                    for (OfflinePlayer plr : Bukkit.getBannedPlayers()) {
                        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta meta = (SkullMeta) head.getItemMeta();
                        assert meta != null;
                        meta.setLore(List.of(Utilities.color("&3Click to revive this player."), Utilities.color("&3Sets their hearts to 3.")));
                        meta.setOwnerProfile(plr.getPlayerProfile());
                        meta.setDisplayName(ChatColor.AQUA + plr.getName());
                        head.setItemMeta(meta);
                        beacon.addItem(head);
                    }
                    if(Bukkit.getBannedPlayers().isEmpty()){
                        ItemStack barrier = new ItemStack(Material.BARRIER);
                        ItemMeta meta = Objects.requireNonNull(barrier.getItemMeta());
                        meta.setDisplayName(Utilities.color("&cNo players to revive!"));
                        meta.setLore(List.of(Utilities.color("&4No one is banned at the moment!"), Utilities.color("&4Try again later!")));
                        barrier.setItemMeta(meta);
                        beacon.setItem(22, barrier);
                    }
                    p.openInventory(beacon);
                    break;
                case "crafted_heart":
                    if(Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).getBaseValue() < 10 * 2) {
                        Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).getBaseValue() + 2.0);
                        p.sendMessage(ChatColor.GREEN + "You gained a heart!");
                        Bukkit.getLogger().info("[USED CRAFTED HEART] "+p.getName()+ " used a crafted heart!");
                        e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                    } else {
                        p.sendMessage(ChatColor.RED + "You already have the maximum amount of hearts from crafted hearts!");
                    }
            }
        }
    }
    @EventHandler
    private void onInventoryClick(InventoryClickEvent e)
    {
        if(e.getView().getTitle().equalsIgnoreCase(Utilities.color("&b&lBeacon of Life"))) {
            e.setCancelled(true);
            if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
                assert meta != null;
                OfflinePlayer p = meta.getOwningPlayer();
                if(p != null) {
                    if (p.isBanned()) {

                        BanList<PlayerProfile> banList = Bukkit.getBanList(BanList.Type.PROFILE);
                        banList.pardon(p.getPlayerProfile());
                        e.getWhoClicked().sendMessage(ChatColor.GREEN + "You have revived " + ChatColor.AQUA + p.getName() + ChatColor.GREEN + "!");
                        e.getWhoClicked().closeInventory();
                        e.getWhoClicked().getInventory().removeItem(ReviveBeacon.getItem());
                    } else {
                        e.getWhoClicked().sendMessage(ChatColor.RED + "That player is not banned.");
                    }
                } else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "That player is not banned.");
                }
            }
        }
    }
    @EventHandler
    private void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player attacker) {
            if(attacker.hasCooldown(Material.MACE) && attacker.getInventory().getItemInMainHand().getType().equals(Material.MACE))
            {
                e.setCancelled(true);
                return;
            }
            if(attacker.getInventory().getItemInMainHand().getType().equals(Material.MACE))
            {
                attacker.setCooldown(Material.MACE, 200);
            }
        }
    }
}
