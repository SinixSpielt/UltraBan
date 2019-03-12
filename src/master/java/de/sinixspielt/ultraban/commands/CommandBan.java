package de.sinixspielt.ultraban.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
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

public class CommandBan implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		final Player p = (Player) sender;

		if (!(p.hasPermission(Main.getFileManager().getConfigFile().getConfig().getString("CONIG.BAN.USEPERMISSION")))) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOPERMISSION"));
			return true;
		}
		if (!(args.length >= 2)) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.WRONGCOMMAND.BAN"));
			return true;
		}

		if (args.length >= 2) {
			final String playerName = args[0];
			if (OptionManager.getUUIDFromPlayer(playerName) == null) {
				p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOMINECRAFTACCOUNT"));
				return true;
			}
			String reason = "";
			final UUID uuid = OptionManager.getUUIDFromPlayer(playerName);
			for (int i = 1; i < args.length; i++) {
				reason = reason + args[i] + " ";
			}
			final String grund = reason;
			final Player target = Bukkit.getPlayer(playerName);
			if (target != null) {
				if (target.hasPermission(Main.getFileManager().getConfigFile().getConfig().getString("CONIG.BAN.BYPASSPERMISSION"))) {
					p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.BANPLAYER.PLAYERBYPASS"));
					return true;
				}
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					if (BanManager.isBanned(uuid)) {
						p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.ISBANNED"));
						return;
					}
					if (target != null) {
						BanManager.banPlayer(uuid, playerName, true, grund, -1L, p.getName());
						String msg = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.BANPLAYER.BANSCREEN");
						msg = msg.replace("%REASON%", grund);
						final String msg2 = msg;
						new BukkitRunnable() {
							@Override
							public void run() {
								target.kickPlayer(msg2);
							}
						}.runTaskLater(Main.getInstance(), 0L);
						String playermessage = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.BANPLAYER.BANMESSAGE");
						playermessage = playermessage.replace("%REASON%", grund);
						playermessage = playermessage.replace("%PLAYER%", target.getName());
						p.sendMessage(playermessage);
						return;
					} else {
						BanManager.banPlayer(uuid, playerName, true, grund, -1L, p.getName());
						String playermessage = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.BANPLAYER.BANMESSAGE");
						playermessage = playermessage.replace("%REASON%", grund);
						playermessage = playermessage.replace("%PLAYER%", playerName);
						p.sendMessage(playermessage);
						return;
					}
				}
			}.runTaskAsynchronously(Main.getInstance());
		}
		return true;
	}
}