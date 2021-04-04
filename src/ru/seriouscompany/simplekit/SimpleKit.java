package ru.seriouscompany.simplekit;

import org.bukkit.plugin.java.JavaPlugin;
import ru.seriouscompany.simplekit.commands.CKit;
import ru.seriouscompany.simplekit.listeners.PlayerListener;

public class SimpleKit extends JavaPlugin {

	protected static SimpleKit INSTANCE;

	public static SimpleKit getInstance() {
		return INSTANCE;
	}

	@Override
	public void onEnable() {
		INSTANCE = this;
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getCommand("kit").setExecutor(new CKit());
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

}
