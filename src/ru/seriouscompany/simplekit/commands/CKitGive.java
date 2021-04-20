package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.Locale;
import ru.seriouscompany.simplekit.Query;
import ru.seriouscompany.simplekit.SimpleKit;

public class CKitGive implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length < 1)
			return false;
		
		String player_name = "";

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("simplekit.kit.give")) {
				sender.sendMessage(Locale.PERMISSION_DENIED);
				return true;
			}
			if (args.length < 2)
				player_name = player.getName();
		} else if (args.length < 2) return false;

		if (args.length > 1)
			player_name = args[1];

		int count = 1;
		if (args.length == 3)
			count = Integer.parseInt(args[2]);

		Kit kit = Kit.load(args[0]);
		if (kit == null) {
			sender.sendMessage(Locale.INFO_KIT_GIVE_NOT_FOUND
					.replaceAll("%NAME%", args[0]));
			return true;
		}

		Player target = SimpleKit.getInstance().getServer().getPlayer(player_name);
		if (target != null) {
			kit.give(target, count);
		} else {
			Query query = new Query(kit.getName(), count, player_name);
			query.save();
		}
		sender.sendMessage(Locale.INFO_KIT_GIVED);
		
		return true;
	}
}
