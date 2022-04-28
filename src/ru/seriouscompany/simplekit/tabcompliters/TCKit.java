package ru.seriouscompany.simplekit.tabcompliters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ru.seriouscompany.simplekit.SimpleKit;
import ru.seriouscompany.simplekit.commands.CKit;

public class TCKit implements TabCompleter {
	
	TCKitName completerName;
	TCKitGive completerGive;
	
	public TCKit() {
		completerName = new TCKitName();
		completerGive = new TCKitGive();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		if (args.length == 1) {
			if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.kit.del"))
				tryadd(args, data, CKit.PREFIX_COMMAND_DELETE);
			if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.kit.give"))
				tryadd(args, data, CKit.PREFIX_COMMAND_GIVE);
			if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.kit.list"))
				tryadd(args, data, CKit.PREFIX_COMMAND_LIST);
			if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.kit.set")) {
				tryadd(args, data, CKit.PREFIX_COMMAND_SET);
				tryadd(args, data, CKit.PREFIX_COMMAND_SET_PRICE);
			}
			tryadd(args, data, CKit.PREFIX_COMMAND_TAKE);
			if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.query.check"))
				tryadd(args, data, CKit.PREFIX_COMMAND_QUEUE_CHECK);
			if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.query.del"))
				tryadd(args, data, CKit.PREFIX_COMMAND_QUEUE_DELETE);
			if (SimpleKit.moneyController().inPointWarsFinded())
				tryadd(args, data, CKit.PREFIX_COMMAND_BUY);
		} else if (args.length > 1) {
			String[] newArgs;
			if (args.length > 1) newArgs = Arrays.copyOfRange(args, 1, args.length);
			else newArgs = new String[0];
			switch (args[0].trim()) {
			case CKit.PREFIX_COMMAND_BUY:
			case CKit.PREFIX_COMMAND_SET_PRICE:
			case CKit.PREFIX_COMMAND_SET:
			case CKit.PREFIX_COMMAND_DELETE:
				return completerName.onTabComplete(sender, cmd, label, newArgs);
			case CKit.PREFIX_COMMAND_GIVE:
				return completerGive.onTabComplete(sender, cmd, label, newArgs);
			}
			
		}
		return data;
	}
	private void tryadd(String[] args, List<String> data,String text) {
		int argLen = args[0].length();
		if (
				args[0].equalsIgnoreCase("") || 
				args[0].equalsIgnoreCase(text) || 
				(
					text.length() >= argLen &&
					text.substring(0,argLen).equalsIgnoreCase(args[0])
				)
			)
			data.add(text);
	}

}
