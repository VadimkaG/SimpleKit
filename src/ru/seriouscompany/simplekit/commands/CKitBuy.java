package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.pointwar.Clan;
import ru.seriouscompany.pointwar.PlayerStats;
import ru.seriouscompany.simplekit.Kit;

public class CKitBuy implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            return false;
        }
        if (!(sender instanceof Player)) {
        	sender.sendMessage(Config.COMMAND_FOR_PLAYERS);
        	return true;
        }
        
        Kit kit = Kit.load(args[0]);
        
        if (kit == null) {
        	sender.sendMessage("Набор не существует");
        	return true;
        }
        
        if (kit.getPrice() < 0) {
        	sender.sendMessage("Этот набор не продается");
        	return true;
        } else if (kit.getPrice() == 0) {
        	kit.give((Player)sender, 1);
        	return true;
        }

        try {
        
	        PlayerStats playerStats = PlayerStats.loadByPlayerName(((Player)sender).getName());
	        
	        if (playerStats.getClanId() < 1) {
	        	sender.sendMessage("Вы не состоите в клане");
	        	return true;
	        }
	        
	        if (playerStats.getClanPerm() != PlayerStats.CLAN_PERM_ALL) {
	        	sender.sendMessage("Вы не лидер!");
	        	return true;
	        }
        
	        Clan clan = Clan.load(playerStats.getClanId());
	
	        if (clan == null) {
	        	sender.sendMessage("Клан не найден");
	        	return true;
	        }
	        
	        if (clan.getMoney() < kit.getPrice()) {
	        	sender.sendMessage("У вас недостаточно денег. Для покупки необходимо "+String.valueOf(kit.getPrice())+" монет.");
	        	return true;
	        }
	        
	        clan.addMoney(0-kit.getPrice());
	        clan.save();
	
	    	kit.give((Player)sender, 1);
        } catch (NoClassDefFoundError ex) {
        	sender.sendMessage("Не найден плагин PointWars. Покупка не возможна.");
        }
        
		return true;
	}

}
