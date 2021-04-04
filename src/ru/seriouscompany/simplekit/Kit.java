package ru.seriouscompany.simplekit;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

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
        File f = new File("plugins/SimpleKit/kits.yml");
        YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
        y.set(name, item);
        try {y.save(f);} catch (IOException e) {
            SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка сохранения kits.yml.");
        }
    }

    public void give(String player_name, int count) {

        Player player = SimpleKit.getInstance().getServer().getPlayer(player_name);

        if (player == null) {
            File f = new File("plugins/SimpleKit/query.yml");
            YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
            int old_count = y.getInt(player_name + "." + name);
            if (old_count > 0) {
                count += old_count;
            }
            y.set(player_name + "." + name, count);
            try {y.save(f);} catch (IOException e) {
                SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка сохранения query.yml.");
            }
            return;
        }

        for (int i = 0; i < count; i++) {
            if (player.getInventory().firstEmpty() < 0) {
                ItemStack item = player.getInventory().getItem(9 + i);
                player.getInventory().setItem(9 + i, null);
                player.getWorld().dropItem(player.getLocation(), item);
            }
            player.getInventory().addItem(item);
        }
    }

    public static Set<String> list() {
        Set<String> list;
        File f = new File("plugins/SimpleKit/kits.yml");
        YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
        list = y.getKeys(false);
        return list;
    }

    public static void delete(String name) {
        File f = new File("plugins/SimpleKit/kits.yml");
        YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
        y.set(name, null);
        try {y.save(f);} catch (IOException e) {
            SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка сохранения kits.yml.");
        }
    }

    public static Kit load(String name) {
        File f = new File("plugins/SimpleKit/kits.yml");
        YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
        ItemStack item = y.getItemStack(name);
        if (item == null) {
            return null;
        }
        return new Kit(name, item);
    }

    public static void checkQuery(Player player) {
        String player_name = player.getName();
        File f = new File("plugins/SimpleKit/query.yml");
        YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
        MemorySection player_kits = (MemorySection) y.get(player_name);
        if (player_kits == null) {
            return;
        }
        Set<String> kits = player_kits.getKeys(false);
        for (String name: kits) {
            int count = y.getInt(player_name + "." + name);
            Kit kit = Kit.load(name);
            if (kit != null) {
                kit.give(player_name, count);
                try {y.save(f);} catch (IOException e) {
                    SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка сохранения query.yml.");
                }
            }
        }
        y.set(player_name, null);
    }

}
