package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.Locale;

public class CKitDelete implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		String name;

		if (args.length < 1) {
			return false;
		}

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("simplekit.kit.del")) {
				sender.sendMessage(Locale.PERMISSION_DENIED);
				return true;
			}
		}

		name = args[0];
		Kit.delete(name);
		sender.sendMessage(Locale.INFO_KIT_DELETE
				.replaceAll("%NAME%", name));
		return true;
	}
}
