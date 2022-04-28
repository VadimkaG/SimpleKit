package ru.seriouscompany.simplekit;

import org.bukkit.plugin.java.JavaPlugin;

import ru.seriouscompany.simplekit.commands.CKit;
import ru.seriouscompany.simplekit.listeners.PlayerListener;
import ru.seriouscompany.simplekit.tabcompliters.TCKit;

public class SimpleKit extends JavaPlugin {

	protected static SimpleKit INSTANCE = null;
	protected static MoneyController MONEY_CONTROLLER = null;

	public static SimpleKit getInstance() {
		return INSTANCE;
	}

	@Override
	public void onEnable() {
		INSTANCE = this;
		MONEY_CONTROLLER = new MoneyController();
		Config.init();
		Locale.load();
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getCommand("kit").setExecutor(new CKit());
		getCommand("kit").setTabCompleter(new TCKit());
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	public static MoneyController moneyController() {
		return MONEY_CONTROLLER;
	}

}
