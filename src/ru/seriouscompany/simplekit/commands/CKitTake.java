package ru.seriouscompany.simplekit.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.Locale;
import ru.seriouscompany.simplekit.Query;

public class CKitTake implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			List<Query> kits = Query.load(player.getName());
			if (kits.size() < 1) {
				sender.sendMessage(Locale.ERROR_QUERY_EMPTY);
				return true;
			}
			for (Query q: kits) {
				Kit kit = Kit.load(q.getKit());
				kit.give(player, q.getCount());
				q.delete();
			}
			return true;
		} else
			sender.sendMessage(Locale.COMMAND_ONLY_FOR_PLAYERS);
		return false;
	}

}
