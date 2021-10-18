package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.Locale;

public class CKitPriceSet implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 2) {
            return false;
        }
        if (!sender.isPermissionSet("simplekit.kit.set")) {
            sender.sendMessage(Locale.PERMISSION_DENIED);
            return true;
        }
        int price = 0;
        try {
        	price = Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        Kit kit = Kit.load(args[0]);
        if (kit == null) {
			sender.sendMessage(Locale.INFO_KIT_GIVE_NOT_FOUND
					.replaceAll("%NAME%", args[0]));
			return true;
        }
        kit.setPrice(price);
        kit.save();
        sender.sendMessage("done");
		return true;
	}

}
