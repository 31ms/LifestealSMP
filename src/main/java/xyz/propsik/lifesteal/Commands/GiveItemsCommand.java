package xyz.propsik.lifesteal.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.propsik.lifesteal.Items.CraftedHeart;
import xyz.propsik.lifesteal.Items.Heart;
import xyz.propsik.lifesteal.Items.ReviveBeacon;
import xyz.propsik.lifesteal.Lifesteal;

public class GiveItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        try {
            if (commandSender instanceof Player p) {
                p.getInventory().addItem(ReviveBeacon.getItem());
                p.getInventory().addItem(Heart.getItem());
                p.getInventory().addItem(CraftedHeart.getItem());
                p.sendMessage("Gave you a Heart and a Revive Beacon!");
            }
        }
        catch (Exception e) {
            Lifesteal.getInstance().getLogger().severe(e.getMessage());
        }
        return true;
    }
}
