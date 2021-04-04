package ru.seriouscompany.simplekit;

import org.bukkit.configuration.file.YamlConfiguration;
import ru.seriouscompany.essentials.api.Utils;

import java.io.File;
import java.io.IOException;

public abstract class Locale {
	public static String PERMISSION_DENIED = "У вас недостаточно привилегий";
	public static String COMMAND_ONLY_FOR_PLAYERS = "Эта команда только для игроков";

	public static String LIST_KIT_HEADER = "Список наборов:";
	public static String LIST_KIT_EMPTY = "Нет доступных наборов";

	public static String INFO_KIT_SET = "Набор успешно создан!";
	public static String INFO_KIT_DELETE = "Набор %NAME% удален";
	public static String INFO_KIT_GIVE_NOT_FOUND = "Данный кит %NAME% не найден";
	public static String INFO_KIT_GIVE_PLAYER_NOT_FOUND = "Данный игрок %PLAYER% не найден";

	public static void load() {
		File f = new File("plugins/SimpleKit/messages.yml");
		YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
		boolean needSetDefault = !f.exists();
		if (needSetDefault)
			y.options().copyDefaults(true);

		PERMISSION_DENIED = procString(y,needSetDefault,"permissionDenied",PERMISSION_DENIED);
		COMMAND_ONLY_FOR_PLAYERS = procString(y,needSetDefault,"commandOnlyForPlayers",COMMAND_ONLY_FOR_PLAYERS);

		LIST_KIT_HEADER = procString(y,needSetDefault,"list.kit.header",LIST_KIT_HEADER);
		LIST_KIT_EMPTY = procString(y,needSetDefault,"list.kit.empty",LIST_KIT_EMPTY);

		INFO_KIT_SET = procString(y,needSetDefault,"info.kit.set",INFO_KIT_SET);
		INFO_KIT_DELETE = procString(y,needSetDefault,"info.kit.delete",INFO_KIT_DELETE);
		INFO_KIT_GIVE_NOT_FOUND = procString(y,needSetDefault,"info.kit.give_not_found",INFO_KIT_GIVE_NOT_FOUND);
		INFO_KIT_GIVE_PLAYER_NOT_FOUND = procString(y,needSetDefault,"info.kit.player_not_found",INFO_KIT_GIVE_PLAYER_NOT_FOUND);

		if (needSetDefault)
			try {y.save(f);} catch (IOException e) {
				SimpleKit.getInstance().getLogger().info("Ошибка сохранения messages.yml.");
			}
	}
	private static String procString(YamlConfiguration y, boolean isSetDefault, String alias, String value) {
		y.addDefault(alias, value);
		if (isSetDefault) {
			return value;
		} else
			return Utils.replaceColorCodes(y.getString(alias));
	}
}
