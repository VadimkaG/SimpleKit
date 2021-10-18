package ru.seriouscompany.simplekit;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit {

    protected String    name;
    protected ItemStack item;
    protected int       price;

    public Kit(String name, ItemStack item, int price) {
        this.item  = item;
        this.name  = name;
        this.price = price;
    }
    public Kit(String name, ItemStack item) {
    	this(name,item,-1);
    }
    /**
     * Установить цену
     * @param price
     */
    public void setPrice(int price) {
    	this.price = price;
    }
    public int getPrice() {
    	return price;
    }

    public String getName() { return name; }

    public ItemStack getItemStack() {
        return item;
    }

    public void save() {
    	
        Config.KIT_CONFIG.set(name+".item", item);
        Config.KIT_CONFIG.set(name+".price", price);
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
    /**
     * Список наборов
     * @return
     */
    public static Set<String> list() {
        Set<String> list;
        list = Config.KIT_CONFIG.getKeys(false);
        return list;
    }
    /**
     * Удалить набор
     * @param name
     */
    public static void delete(String name) {
    	Config.KIT_CONFIG.set(name, null);
        try {Config.KIT_CONFIG.save(Config.KIT_FILE);} catch (IOException e) {
            SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка сохранения kits.yml.");
        }
    }
    /**
     * Существует ли набор с именем name
     * @param name
     * @return
     */
    public static boolean has(String name) {
    	return Config.KIT_CONFIG.contains(name);
    }
    /**
     * Загрузить набор
     * @param name
     * @return
     */
    public static Kit load(String name) {
    	if (!has(name)) return null;
        ItemStack item = Config.KIT_CONFIG.getItemStack(name+".item");
        int price = Config.KIT_CONFIG.getInt(name+".price");
        if (item == null) {
            return null;
        }
        return new Kit(name, item, price);
    }

}
