package xyz.propsik.lifesteal;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.propsik.lifesteal.Commands.GiveItemsCommand;
import xyz.propsik.lifesteal.Commands.WithdrawCommand;
import xyz.propsik.lifesteal.Items.CraftedHeart;
import xyz.propsik.lifesteal.Items.Heart;
import xyz.propsik.lifesteal.Items.ReviveBeacon;
import xyz.propsik.lifesteal.Listeners.HeartsListener;
import xyz.propsik.lifesteal.Listeners.ItemListeners;

import java.util.Objects;

public final class Lifesteal extends JavaPlugin {
    private static Lifesteal INSTANCE;
    private static NamespacedKey key;
    @Override
    public void onEnable() {
        INSTANCE = this;
        key = new NamespacedKey(INSTANCE, "heart");
        register();
    }
    public static Lifesteal getInstance() {
        return INSTANCE;
    }
    private void register()
    {
        getServer().getPluginManager().registerEvents(new HeartsListener(), this);
        getServer().getPluginManager().registerEvents(new ItemListeners(), this);

        Objects.requireNonNull(this.getCommand("giveitems")).setExecutor(new GiveItemsCommand());
        Objects.requireNonNull(this.getCommand("withdraw")).setExecutor(new WithdrawCommand());

        new Heart();
        new ReviveBeacon();
        new CraftedHeart();
    }
    public static NamespacedKey getKey() {
        return key;
    }
}
