package de.sinixspielt.ultraban.commands;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.sinixspielt.ultraban.Main;
import de.sinixspielt.ultraban.utils.OptionManager;

public class CommandKick implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}

		Player p = (Player) sender;

		if (!(p.hasPermission(Main.getFileManager().getConfigFile().getConfig().getString("CONIG.KICK.USEPERMISSION")))) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOPERMISSION"));
			return true;
		}
		if (!(args.length >= 2)) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.WRONGCOMMAND.KICK"));
			return true;
		}
		
		if (args.length >= 2) {
			String playerName = args[0];
			if (OptionManager.getUUIDFromPlayer(playerName) == null) {
				p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOMINECRAFTACCOUNT"));
				return true;
			}
			String reason = "";
			for (int i = 1; i < args.length; i++) {
				reason = reason + args[i] + " ";
			}
			
			Player target = Bukkit.getPlayer(playerName);
			
			if(target == null){
				p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOTONLINE"));
				return true;
			}
			
			if(target.isOnline()){
				if(target.hasPermission(Main.getFileManager().getConfigFile().getConfig().getString("CONIG.KICK.BYPASSPERMISSION"))){
					p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.KICKPLAYER.PLAYERBYPASS"));
					return true;
				}else{
					String msg = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.KICKPLAYER.KICKSCREEN");
					msg = msg.replace("%REASON%", reason);
					target.kickPlayer(msg);
					
					String playermessage = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.KICKPLAYER.KICKMESSAGE");
					playermessage = playermessage.replace("%PLAYER%", target.getName());
					playermessage = playermessage.replace("%REASON%", reason);
					p.sendMessage(playermessage);
				}
				return true;
				
			}
			return true;
		}
		return true;
	}
}