package ru.seriouscompany.simplekit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.seriouscompany.simplekit.Locale;
import ru.seriouscompany.simplekit.Query;


public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int count = Query.count(player.getName());
        if (count > 0)
        	player.sendMessage(Locale.INFO_KIT_AVAILABLE);
    }
}
