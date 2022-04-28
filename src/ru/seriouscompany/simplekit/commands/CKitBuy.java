package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.Config;
import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.SimpleKit;

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
        
        	SimpleKit.moneyController().takeMoney((Player)sender, kit.getPrice());
	
	    	kit.give((Player)sender, 1);
        } catch (Exception ex) {
        	sender.sendMessage(ex.getMessage());
        }
        
		return true;
	}

}
