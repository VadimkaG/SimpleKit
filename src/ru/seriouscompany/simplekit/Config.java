package ru.seriouscompany.simplekit;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class Config {

	public static File KIT_FILE;
	public static YamlConfiguration KIT_CONFIG;
	
	/**
	 * Инициализация конфигурации
	 */
	public static void init() {
		KIT_FILE = new File("plugins/SimpleKit/kits.yml");
		KIT_CONFIG = YamlConfiguration.loadConfiguration(KIT_FILE);
		Connection conncetion = connectToDB();
		if (conncetion == null) return;
		try {
			Statement state = conncetion.createStatement();
			state.executeUpdate("create table if not exists players ("
					+ "id integer primary key autoincrement, "
					+ "playername varchar(50) not null, "
					+ "kit varchar(50) not null, "
					+ "count integer(10) not null "
					+ ")");
			state.executeUpdate("create index if not exists loadByPlayer on players (playername)");
			conncetion.close();
		} catch (SQLException e) {
			SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка при инициализации базы данных",e);
		}
	}
	/**
	 * Подключение к базе данных
	 * @return
	 */
	public static Connection connectToDB() {
		File file = new File("plugins/SimpleKit/queue.db").getParentFile();
		if (!file.exists()) file.mkdirs();
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:plugins/SimpleKit/queries.db");
		} catch (ClassNotFoundException | SQLException e) {
			SimpleKit.getInstance().getLogger().log(Level.WARNING,"Не удалось подключиться к базе данных",e);
			return null;
		}
	}
}
