package ru.seriouscompany.simplekit;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {

    protected String name;
    protected ItemStack item;

    public Kit(String name, ItemStack item) {
        this.item = item;
        this.name = name;
    }

    public String getName() { return name; }

    public ItemStack getItemStack() {
        return item;
    }

    public void save() {
        Config.KIT_CONFIG.set(name, item);
        try {Config.KIT_CONFIG.save(Config.KIT_FILE);} catch (IOException e) {
            SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка сохранения kits.yml.");
        }
    }

    public void give(Player player, int count) {

    	boolean drop = false;
        for (int i = 0; i < count; i++) {
            if (player.getInventory().firstEmpty() < 0) {
            	player.getWorld().dropItem(player.getLocation(), item);
            	drop = true;
            } else
            	player.getInventory().addItem(item);
        }
        if (drop)
            player.sendMessage(Locale.INFO_YOU_TAKE_KIT_AND_DROP
            		.replaceAll("%KIT%", name)
            		.replaceAll("%COUNT%", String.valueOf(count))
            		);
        else
            player.sendMessage(Locale.INFO_YOU_TAKE_KIT
            		.replaceAll("%KIT%", name)
            		.replaceAll("%COUNT%", String.valueOf(count))
            		);
    }

    public static Set<String> list() {
        Set<String> list;
        list = Config.KIT_CONFIG.getKeys(false);
        return list;
    }

    public static void delete(String name) {
    	Config.KIT_CONFIG.set(name, null);
        try {Config.KIT_CONFIG.save(Config.KIT_FILE);} catch (IOException e) {
            SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка сохранения kits.yml.");
        }
    }

    public static Kit load(String name) {
        ItemStack item = Config.KIT_CONFIG.getItemStack(name);
        if (item == null) {
            return null;
        }
        return new Kit(name, item);
    }

}
