package de.sinixspielt.ultraban.commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.sinixspielt.ultraban.Main;
import de.sinixspielt.ultraban.manager.BanManager;
import de.sinixspielt.ultraban.utils.BanUnit;
import de.sinixspielt.ultraban.utils.OptionManager;

public class CommandTempban implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Dieser Befehl ist nur für Spieler bestimmt.");
			return true;
		}
		Player p = (Player) sender;

		if (!(p.hasPermission(Main.getFileManager().getConfigFile().getConfig().getString("CONIG.TEMPBAN.USEPERMISSION")))) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOPERMISSION"));
			return true;
		}
		if (args.length < 4) {
			p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.WRONGCOMMAND.TEMPBAN"));
			return true;
		}
		if (args.length >= 4) {
			String playername = args[0];
			UUID uuid = OptionManager.getUUIDFromPlayer(playername);
			
			if(uuid == null){
				p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.NOMINECRAFTACCOUNT"));
				return true;
			}
			long value;
			try {
				value = Integer.valueOf(args[1]).intValue();
			} catch (NumberFormatException e) {
				p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.WRONGCOMMAND.TEMPBAN"));
				return true;
			}
			if (BanManager.isBanned(uuid)) {
				p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.ISBANNED"));
				return true;
			}
			String unitString = args[2];
			String reason = "";

			for (int i = 3; i < args.length; i++) {
				reason = reason + args[i] + " ";
			}
			
			List<String> unitList = BanUnit.getUnitsAsString();
			if(unitList.contains(unitString.toLowerCase())){
				BanUnit unit = BanUnit.getUnit(unitString);
				long seconds = value*unit.getToSecond();
				BanManager.banPlayer(uuid, playername, false, reason, seconds, p.getName());
				String playermessage = Main.getFileManager().getMessagesFile().getMessage("CONIG.BANSYSTEM.TEMPBANPLAYER.BANMESSAGE");
				playermessage = playermessage.replace("%REASON%", reason);
				playermessage = playermessage.replace("%TIME%", value + unitString);
				playermessage = playermessage.replace("%PLAYER%", playername);
				p.sendMessage(playermessage);
	            return true;
			}else{
				p.sendMessage(Main.getFileManager().getMessagesFile().getMessage("CONIG.WRONGCOMMAND.TEMPBAN"));
		          return true;
			}
		}
		return true;
	}
}