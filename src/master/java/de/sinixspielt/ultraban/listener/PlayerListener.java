package de.sinixspielt.ultraban.listener;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.sinixspielt.ultraban.Main;
import de.sinixspielt.ultraban.manager.BanManager;
import de.sinixspielt.ultraban.utils.OptionManager;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public class PlayerListener implements Listener{
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(p.getName().equalsIgnoreCase("SinixSpielt")){
			p.sendMessage("§7Dieser Server nutzt §4UltraBan §7auf der Version §c" + Main.getInstance().getDescription().getVersion() + "§7.");
		}
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onAsyncPreLogin(AsyncPlayerPreLoginEvent e) {
		UUID uuid = OptionManager.getUUIDFromPlayer(e.getName());
		if (Main.getBanManager().isBanned(OptionManager.getUUIDFromPlayer(e.getName()))) {
			if (Main.getBanManager().isPERMANENTBanned(uuid).intValue() == 1) {
				String msg = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.BANPLAYER.BANSCREEN");
				msg = msg.replace("%REASON%", BanManager.getReason(uuid));
				e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, msg);
				return;
			}
			long current = System.currentTimeMillis();
			long end = BanManager.getRestBannedTime(uuid);
			if ((current < end) || (end == -1L)) {
				String msg = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.TEMPBANPLAYER.BANSCREEN");
				msg = msg.replace("%REASON%", BanManager.getReason(uuid));
				msg = msg.replace("%TIME%", BanManager.getRemainingTime(uuid));
		        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, msg);
				return;
			}else{
				e.allow();
			}
			return;
		}
		return;
	}
}