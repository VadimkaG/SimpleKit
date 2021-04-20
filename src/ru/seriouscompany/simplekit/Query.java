package ru.seriouscompany.simplekit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Query {
	protected Long id;
	protected String kit;
	protected int count;
	protected String playerName;

	public Query(String kit, int count, String playerName, Long id) {
		this.id = id;
		this.kit = kit;
		this.count = count;
		this.playerName = playerName;
	}
	public Query(String kit, int count, String playerName) {
		this(kit, count, playerName, null);
	}
	/**
	 * Получить идентификатор
	 * @return
	 */
	public Long getID() {
		return id;
	}
	/**
	 * Получить наименование набора
	 * @return
	 */
	public String getKit() {
		return kit;
	}
	/**
	 * Получить количество
	 * @return
	 */
	public int getCount() {
		return count;
	}
	/**
	 * Сохранить в базе данных
	 */
	public void save() {
		Connection conn = Config.connectToDB();
		if (conn == null) return;
		if (id == null) {
			try {
				PreparedStatement state = conn.prepareStatement("INSERT INTO players(playername, kit, count) VALUES(?,?,?)");
				state.setString(1, playerName);
				state.setString(2, kit);
				state.setInt(3, count);
				state.execute();

				ResultSet rs = state.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getLong(1);
				} else
					SimpleKit.getInstance().getLogger().log(Level.WARNING,"Ошибка при создании. SQL не вернул id");
				
			} catch (SQLException e) {
				SimpleKit.getInstance().getLogger().log(Level.WARNING,"Не удалось создать",e);
			} finally {
				try {conn.close();} catch (SQLException e) {}
			}
		}
	}
	/**
	 * Удалить из базы данных
	 */
	public void delete() {
		Connection conncetion = Config.connectToDB();
		if (conncetion == null) return;
		try {
			Statement state = conncetion.createStatement();
			state.executeUpdate("delete from players where id="+String.valueOf(id));
			conncetion.close();
		} catch (SQLException e) {
			SimpleKit.getInstance().getLogger().log(Level.WARNING, "Ошибка при инициализации базы данных",e);
		}
	}
	/**
	 * Количество не выданного доната у игрока
	 * @param playerName
	 * @return
	 */
	public static int count(String playerName) {
		Connection conn = Config.connectToDB();
		if (conn == null) return 0;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT Count(*) c FROM players WHERE playername='"+playerName+"'");
			ResultSet rs = state.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			SimpleKit.getInstance().getLogger().log(Level.WARNING,"Не удалось загрузить список",e);
		} finally {
			try {conn.close();} catch (SQLException e) {}
		}
		return 0;
	}
	public static Query load(long id) {
		Connection conn = Config.connectToDB();
		if (conn == null) return null;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM players WHERE id="+String.valueOf(id)+"");
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				return new Query(
						rs.getString("kit"),
						rs.getInt("count"),
						rs.getString("playername"),
						rs.getLong("id")
					);
			}
		} catch (SQLException e) {
			SimpleKit.getInstance().getLogger().log(Level.WARNING,"Не удалось загрузить список",e);
		} finally {
			try {conn.close();} catch (SQLException e) {}
		}
		return null;
	}
	/**
	 * Загрузить список очередей по нику игрока
	 * @param playerName
	 * @return
	 */
	public static List<Query> load(String playerName) {
		List<Query> list = new ArrayList<>();
		Connection conn = Config.connectToDB();
		if (conn == null) return list;
		try {
			PreparedStatement state = conn.prepareStatement("SELECT * FROM players WHERE playername='"+playerName+"'");
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				list.add(new Query(
						rs.getString("kit"),
						rs.getInt("count"),
						rs.getString("playername"),
						rs.getLong("id")
					));
			}
		} catch (SQLException e) {
			SimpleKit.getInstance().getLogger().log(Level.WARNING,"Не удалось загрузить список",e);
		} finally {
			try {conn.close();} catch (SQLException e) {}
		}
		return list;
	}
}
