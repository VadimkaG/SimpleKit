package ru.seriouscompany.simplekit.commands;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.seriouscompany.simplekit.SimpleKit;

public class CKit implements CommandExecutor {
    public static final String PREFIX_COMMAND_SET    = "set";
    public static final String PREFIX_COMMAND_LIST   = "list";
    public static final String PREFIX_COMMAND_DELETE = "del";
    public static final String PREFIX_COMMAND_GIVE = "give";
    public static final String PREFIX_COMMAND_TAKE = "take";
    public static final String PREFIX_COMMAND_QUEUE_CHECK = "qcheck";
    public static final String PREFIX_COMMAND_QUEUE_DELETE = "qdel";
    public static final String PREFIX_COMMAND_SET_PRICE    = "setprice";
    public static final String PREFIX_COMMAND_BUY    = "buy";

    private final CKitSet         COMMAND_SET;
    private final CKitList        COMMAND_LIST;
    private final CKitDelete      COMMAND_DELETE;
    private final CKitTake        COMMAND_TAKE;
    private final CKitGive        COMMAND_GIVE;
    private final CQueryCheck     COMMAND_QUERY_CHECK;
    private final CQueryDel       COMMAND_QUERY_DEL;
    private final CKitPriceSet    COMMAND_SET_PRICE;
    private final CKitBuy         COMMAND_BUY;

    public CKit() {
        COMMAND_SET         = new CKitSet();
        COMMAND_LIST        = new CKitList();
        COMMAND_DELETE      = new CKitDelete();
        COMMAND_GIVE        = new CKitGive();
        COMMAND_TAKE        = new CKitTake();
        COMMAND_QUERY_CHECK = new CQueryCheck();
        COMMAND_QUERY_DEL   = new CQueryDel();
        COMMAND_SET_PRICE   = new CKitPriceSet();
        COMMAND_BUY         = new CKitBuy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            String[] newArgs;
            if (args.length > 1) newArgs = Arrays.copyOfRange(args, 1, args.length);
            else newArgs = new String[0];
            boolean isSuccess = false;
            switch (args[0].trim()) {
                case PREFIX_COMMAND_SET:
                    isSuccess = COMMAND_SET.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_SET, newArgs);
                    break;
                case PREFIX_COMMAND_LIST:
                    isSuccess = COMMAND_LIST.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_LIST, newArgs);
                    break;
                case PREFIX_COMMAND_DELETE:
                    isSuccess = COMMAND_DELETE.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_DELETE, newArgs);
                    break;
                case PREFIX_COMMAND_GIVE:
                    isSuccess = COMMAND_GIVE.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_GIVE, newArgs);
                    break;
                case PREFIX_COMMAND_TAKE:
                	isSuccess = COMMAND_TAKE.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_TAKE, newArgs);
                	break;
                case PREFIX_COMMAND_QUEUE_CHECK:
                	isSuccess = COMMAND_QUERY_CHECK.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_QUEUE_CHECK, newArgs);
                	break;
                case PREFIX_COMMAND_QUEUE_DELETE:
                	isSuccess = COMMAND_QUERY_DEL.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_QUEUE_CHECK, newArgs);
                	break;
                case PREFIX_COMMAND_SET_PRICE:
                	isSuccess = COMMAND_SET_PRICE.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_SET_PRICE, newArgs);
                	break;
                case PREFIX_COMMAND_BUY:
                	isSuccess = COMMAND_BUY.onCommand(sender, cmd, label+" "+PREFIX_COMMAND_BUY, newArgs);
                	break;
                default:
                    isSuccess = false;
                    break;
            }
            if (!isSuccess)
                printHelp(sender, label);
        } else
            printHelp(sender, label);
        return true;
    }
    private void printHelp(CommandSender sender, String label) {
        if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.kit.set")) {
            sender.sendMessage("/"+label+" "+PREFIX_COMMAND_SET+" <????????????????> - ?????????????? ?????????? ???? ???????????????? ?? ?????????? ????????");
            sender.sendMessage("/"+label+" "+PREFIX_COMMAND_SET_PRICE+" <????????????????> <????????> - ???????????? ???????? ????????????????");
        }
        if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.kit.list"))
            sender.sendMessage("/"+label+" "+PREFIX_COMMAND_LIST+" - ???????????? ?????????????????? ??????????????");
        if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.kit.del"))
            sender.sendMessage("/"+label+" "+PREFIX_COMMAND_DELETE+" <????????????????>  - ?????????????? ??????????");
        if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.kit.give"))
            sender.sendMessage("/"+label+" "+PREFIX_COMMAND_GIVE+" <??????????> [??????????] [????????????????????] - ???????????? ??????????");
        sender.sendMessage("/"+label+" "+PREFIX_COMMAND_TAKE+" - ???????????????? ????????????, ?????????????? ???? ?????????????? ???????????? ?????? ?? ??????????????");
        if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.query.check"))
            sender.sendMessage("/"+label+" "+PREFIX_COMMAND_QUEUE_CHECK+" <??????????> - ???????????????????? ?????????????? ???? ????????????");
        if (!(sender instanceof Player) ||  ((Player)sender).isPermissionSet("simplekit.query.del"))
            sender.sendMessage("/"+label+" "+PREFIX_COMMAND_QUEUE_DELETE+" <id> - ?????????????? ?????????? ???? ??????????????");
        if (SimpleKit.moneyController().inPointWarsFinded())
        	sender.sendMessage("/"+label+" "+PREFIX_COMMAND_BUY+" <????????????????> - ???????????? ??????????????");
    }
}
