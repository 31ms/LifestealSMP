package xyz.propsik.lifesteal.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Date;

public class HeartsListener implements Listener {
    @EventHandler
    private void onPlayerKill(EntityDeathEvent e)
    {
        if(e.getEntity() instanceof Player victim)
        {
            AttributeInstance victimHearts = victim.getAttribute(Attribute.MAX_HEALTH);
            if (e.getEntity().getKiller() != null) {
                Player killer = e.getEntity().getKiller();

                assert killer != null;
                AttributeInstance killerHearts = killer.getAttribute(Attribute.MAX_HEALTH);

                assert killerHearts != null;
                if (killerHearts.getBaseValue() < 20.0 * 2)
                    killerHearts.setBaseValue(killerHearts.getBaseValue() + 2.0);
            }
            assert victimHearts != null;
            if(victimHearts.getBaseValue() > 1.0 * 2) {
                victimHearts.setBaseValue(victimHearts.getBaseValue() - 2.0);
            }
            else
            {
                victimHearts.setBaseValue(3.0 * 2);
                banPlayer(victim);
            }
        }
    }
    private void banPlayer(Player p)
    {
        p.ban(ChatColor.RED + "You ran out of hearts!", (Date) null, "CONSOLE");
        Bukkit.broadcastMessage(ChatColor.DARK_RED + p.getName() + ChatColor.DARK_RED + " has been banned.");
        p.getWorld().strikeLightning(p.getLocation());
    }
}
