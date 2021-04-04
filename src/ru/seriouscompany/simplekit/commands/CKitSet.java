package ru.seriouscompany.simplekit.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.seriouscompany.simplekit.Kit;
import ru.seriouscompany.simplekit.Locale;
import ru.seriouscompany.simplekit.SimpleKit;

import static ru.seriouscompany.simplekit.Locale.COMMAND_ONLY_FOR_PLAYERS;

public class CKitSet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String name;
        if (args.length < 1) {
            return false;
        }
        try {
            name = args[0];
        } catch (NumberFormatException e) {
            return false;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isPermissionSet("simplekit.kit.set")) {
                sender.sendMessage(Locale.PERMISSION_DENIED);
                return true;
            }
            ItemStack item = player.getInventory().getItemInMainHand();
            Kit kit = new Kit(name, item);
            kit.save();
            player.sendMessage(Locale.INFO_KIT_SET);
            return true;
        } else {
            SimpleKit.getInstance().getLogger().warning(COMMAND_ONLY_FOR_PLAYERS);
        }
        return true;
    }
}
