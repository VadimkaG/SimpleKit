package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.Locale;

import java.util.List;
import java.util.Set;

public class CKitList  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isPermissionSet("simplekit.kit.list")) {
                sender.sendMessage(Locale.PERMISSION_DENIED);
                return true;
            }
        }

        Set<String> list = Kit.list();
        if (list.size() > 0) {
            sender.sendMessage(Locale.LIST_KIT_HEADER + "\n - " + String.join("\n - ", list));
        } else {
            sender.sendMessage(Locale.LIST_KIT_EMPTY);
        }
        return true;
    }
}
