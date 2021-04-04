package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.Locale;
import ru.seriouscompany.simplekit.SimpleKit;

public class CKitGive implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		String name;
		String player_name;
		int count = 1;

		if (args.length < 2) {
			return false;
		}

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isPermissionSet("simplekit.kit.give")) {
				sender.sendMessage(Locale.PERMISSION_DENIED);
				return true;
			}
		}

		player_name = args[0];
		name = args[1];

		if (args.length > 2) {
			count = Integer.parseInt(args[2]);
		}

		Kit kit = Kit.load(name);

		if (kit == null) {
			sender.sendMessage(Locale.INFO_KIT_GIVE_NOT_FOUND
					.replaceAll("%NAME%", name));
			return true;
		}

		kit.give(player_name, count);
		return true;
	}
}
