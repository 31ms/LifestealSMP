package xyz.propsik.lifesteal.Commands;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.propsik.lifesteal.Items.Heart;

import java.util.Objects;

public class WithdrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player p) {
            if (args.length == 0) {
                return false;
            } else if (args.length == 1) {
                try {
                    int amount = Integer.parseInt(args[0]);
                    if (amount <= 0) {
                        p.sendMessage(ChatColor.RED + "Amount must be higher than 0.");
                        return true;
                    }
                    if (p.getInventory().firstEmpty() == -1) {
                        p.sendMessage(ChatColor.RED + "You don't have enough space in your inventory.");
                        return true;
                    }
                    if(Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).getBaseValue() / 2 > amount) {
                        Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).setBaseValue(Objects.requireNonNull(p.getAttribute(Attribute.MAX_HEALTH)).getBaseValue() - amount * 2);
                        ItemStack stack = new ItemStack(Heart.getItem());
                        stack.setAmount(amount);
                        p.getInventory().addItem(stack);
                        p.sendMessage(ChatColor.GREEN + "Withdrew " + amount + " heart(s).");
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have enough hearts to withdraw that amount.");
                    }
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }
}
