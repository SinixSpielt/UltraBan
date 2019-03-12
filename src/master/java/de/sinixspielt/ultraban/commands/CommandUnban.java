package de.sinixspielt.ultraban.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.sinixspielt.ultraban.Main;
import de.sinixspielt.ultraban.manager.BanManager;
import de.sinixspielt.ultraban.utils.OptionManager;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public class CommandUnban implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		final Player p = (Player) sender;

		if (!(p.hasPermission("ultraban.unban"))) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOPERMISSION"));
			return true;
		}

		if (args.length != 1) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.WRONGCOMMAND.UNBAN"));
			return true;
		}

		String playerName = args[0];
		final UUID uuid = OptionManager.getUUIDFromPlayer(playerName);
		if (uuid == null) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOMINECRAFTACCOUNT"));
			return true;
		}
		final OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (BanManager.isBanned(uuid)) {
					BanManager.unban(uuid);
					String playermessage = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.UNBANPLAYER");
					playermessage = playermessage.replace("%PLAYER%", target.getName());
					p.sendMessage(playermessage);
					return;
				} else {
					p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOTBANNED"));
					return;
				}
			}
		}.runTaskAsynchronously(Main.getInstance());
		return true;
	}
}