package ru.seriouscompany.simplekit.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.simplekit.Locale;
import ru.seriouscompany.simplekit.Query;

public class CQueryCheck implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("simplekit.query.check")) {
				sender.sendMessage(Locale.PERMISSION_DENIED);
				return true;
			}
		}
		if (args.length == 1) {
			List<Query> query = Query.load(args[0]);
			if (query.size() < 1) {
				sender.sendMessage(Locale.ERROR_QUERY_EMPTY);
				return true;
			}
			for (Query q : query) {
				sender.sendMessage(String.valueOf("["+q.getID())+"]"+q.getKit()+" - "+String.valueOf(q.getCount()));
			}
			return true;
		}
		return false;
	}

}
