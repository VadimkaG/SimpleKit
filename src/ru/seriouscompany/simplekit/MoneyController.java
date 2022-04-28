package ru.seriouscompany.simplekit;

import org.bukkit.entity.Player;

import ru.seriouscompany.pointwar.Clan;
import ru.seriouscompany.pointwar.PlayerStats;

public class MoneyController {
	
	public boolean pointWarFinded;
	
	public MoneyController() {
		try {
			Class.forName("ru.seriouscompany.pointwar.Clan");
			pointWarFinded = true;
		} catch (ClassNotFoundException e) {
			pointWarFinded = false;
		}
	}
	/**
	 * Подключен ли модуль PointWar
	 * @return
	 */
	public boolean inPointWarsFinded() {
		return pointWarFinded;
	}
	/**
	 * Взять предметы с игрока
	 * @param money
	 * @throws Exception 
	 */
	public void takeMoney(Player player, int money) throws Exception {
		if (!pointWarFinded) 
			throw new Exception("Не возможно получить деньги. Плагин PointWar не найден.");
		
        PlayerStats playerStats = PlayerStats.loadByPlayerName(player.getName());
        
        if (playerStats.getClanId() < 1)
        	throw new Exception("Вы не состоите в клане");
        
        if (playerStats.getClanPerm() != PlayerStats.CLAN_PERM_ALL)
        	throw new Exception("Вы не лидер!");
    
        Clan clan = Clan.load(playerStats.getClanId());

        if (clan == null)
        	throw new Exception("Клан не найден");
        
        if (clan.getMoney() < money)
        	throw new Exception("У вас недостаточно денег. Для покупки необходимо "+String.valueOf(money)+" монет.");
        
        clan.addMoney(0-money);
        clan.save();
	}
}
