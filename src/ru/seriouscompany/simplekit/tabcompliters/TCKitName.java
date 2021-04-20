package ru.seriouscompany.simplekit.tabcompliters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import ru.seriouscompany.simplekit.Kit;

public class TCKitName implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> data = new ArrayList<String>();
		int argLen = args[0].length();
		Set<String> kits = Kit.list();
		for (String kit: kits) {
			if (
					args[0].equalsIgnoreCase("") || 
					args[0].equalsIgnoreCase(kit) || 
					(
						kit.length() >= argLen &&
						kit.substring(0,argLen).equalsIgnoreCase(args[0])
					)
				)
				data.add(kit);
			if (data.size() >= 8) break;
		}
		return data;
	}

}
