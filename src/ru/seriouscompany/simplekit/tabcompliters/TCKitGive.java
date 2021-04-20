package ru.seriouscompany.simplekit.tabcompliters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import ru.seriouscompany.essentials.SCCore;
import ru.seriouscompany.simplekit.Kit;

public class TCKitGive implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		
		// Первый аргумент - имя набора
		if (args.length == 1) {
			int argLen = args[0].length();
			Set<String> kits = Kit.list();
			for (String kit: kits) {
				if (
						args[0].equalsIgnoreCase("") || 
						(
							kit.length() >= argLen &&
							kit.substring(0,argLen).equalsIgnoreCase(args[0])
						)
					)
					data.add(kit);
				if (data.size() >= 8) break;
			}
		
		// Второй аргумент - ник игрока
		} else if (args.length == 2) {
			for (Player player : SCCore.getInstance().getServer().getOnlinePlayers()) {
				if (
						player.getName().length() >= args[1].length()
						&&
						player.getName().substring(0, args[1].length()).equalsIgnoreCase(args[1])
					)
					data.add(player.getName());
				if (data.size() >= 8) break;
			}
		}
		return data;
	}

}
