package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.simplekit.Locale;
import ru.seriouscompany.simplekit.Query;

public class CQueryDel implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("simplekit.query.del")) {
				sender.sendMessage(Locale.PERMISSION_DENIED);
				return true;
			}
		}
		if (args.length == 1) {
			Query q = Query.load(Integer.parseInt(args[0]));
			q.delete();
			sender.sendMessage(Locale.INFO_QUERY_DELETED);
			return true;
		}
		return false;
	}

}
