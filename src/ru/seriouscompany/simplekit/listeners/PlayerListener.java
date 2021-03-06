package ru.seriouscompany.simplekit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.Locale;
import ru.seriouscompany.simplekit.Query;


public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore() && Kit.has("start")) {
        	Kit kit = Kit.load("start");
        	kit.give(player, 1);
        }
        int count = Query.count(player.getName());
        if (count > 0)
        	player.sendMessage(Locale.INFO_KIT_AVAILABLE);
    }
}
