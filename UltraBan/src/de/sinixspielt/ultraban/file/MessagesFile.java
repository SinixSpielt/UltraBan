package de.sinixspielt.ultraban.file;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import de.sinixspielt.ultraban.Main;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public class MessagesFile extends FileBase {

	public MessagesFile() {
		super("", "message");
		writeDefaults();
	}

	private void writeDefaults() {
		FileConfiguration cfg = getConfig();
		cfg.addDefault("CONIG.BANSYSTEM.NOPERMISSION", "%PREFIX% &4Keine Berechtigung!");
		cfg.addDefault("CONIG.BANSYSTEM.NOMINECRAFTACCOUNT", "%PREFIX% &cEs existiert kein Minecraftaccount mit diesem Namen!");
		cfg.addDefault("CONIG.BANSYSTEM.NOTONLINE", "%PREFIX% &cDieser Spieler ist nicht Online!");
		cfg.addDefault("CONIG.BANSYSTEM.NOTBANNED", "%PREFIX% &cDieser Spieler ist nicht gebannt!");
		cfg.addDefault("CONIG.BANSYSTEM.ISBANNED", "%PREFIX% &cDieser Spieler ist bereits gebannt!");
		cfg.addDefault("CONIG.WRONGCOMMAND.BAN", "%PREFIX% &8» &7Verwendung &e/ban <Name> <Grund>");
		cfg.addDefault("CONIG.WRONGCOMMAND.UNBAN", "%PREFIX% &8» &7Verwendung &e/unban <Name>");
		cfg.addDefault("CONIG.WRONGCOMMAND.KICK", "%PREFIX% &8» &7Verwendung &e/kick <Name>");
		cfg.addDefault("CONIG.WRONGCOMMAND.TEMPBAN", "%PREFIX% &8» &7Verwendung &e/tempban <Spieler> <Zeit> <Form(Sekunden=s, Minuten=m, Stunden=h, Tage=d> <Grund>");
		//------------------------------------------------------------------------------------------------\\
		List<String> banscreen = new ArrayList<String>();
		banscreen.add("&8-= &eServername &8=-");
		banscreen.add("%NewLine% %NewLine%");
		banscreen.add("&7Du wudrest vom Server gebannt!");
		banscreen.add("%NewLine% %NewLine%");
		banscreen.add("&7Grund: %REASON%");
		banscreen.add("%NewLine% %NewLine%");
		banscreen.add("&8-= &eServername &8=-");
		cfg.addDefault("CONIG.BANSYSTEM.BANPLAYER.BANSCREEN", banscreen);
		cfg.addDefault("CONIG.BANSYSTEM.BANPLAYER.BANMESSAGE", "%PREFIX% &7Du hast &6%PLAYER% &7wegen &e%REASON% &7gebannt!");
		cfg.addDefault("CONIG.BANSYSTEM.BANPLAYER.PLAYERBYPASS", "%PREFIX% &cDieser Spieler kann nicht gebannt werden!");
		//------------------------------------------------------------------------------------------------\\
		List<String> tempscreen = new ArrayList<String>();
		tempscreen.add("&8-= &eServername &8=-");
		tempscreen.add("%NewLine% %NewLine%");
		tempscreen.add("&7Du wudrest vom Zeitlich vom Server gebannt!");
		tempscreen.add("%NewLine% %NewLine%");
		tempscreen.add("&7Grund: %REASON%");
		tempscreen.add("%NewLine% %NewLine%");
		tempscreen.add("&7Verbleibende Zeit: &7%TIME%");
		tempscreen.add("%NewLine% %NewLine%");
		tempscreen.add("&8-= &eServername &8=-");
		cfg.addDefault("CONIG.BANSYSTEM.TEMPBANPLAYER.BANSCREEN", banscreen);
		cfg.addDefault("CONIG.BANSYSTEM.TEMPBANPLAYER.BANMESSAGE", "%PREFIX% &7Du hast &6%PLAYER% &7wegen &e%REASON% &7für &6%TIME% &7gebannt!");
		//------------------------------------------------------------------------------------------------\\
		List<String> kickscreen = new ArrayList<String>();
		kickscreen.add("&8-= &eServername &8=-");
		kickscreen.add("%NewLine% %NewLine%");
		kickscreen.add("&7Du wudrest vom Server gekickt!");
		kickscreen.add("%NewLine% %NewLine%");
		kickscreen.add("&7Grund: %REASON%");
		kickscreen.add("%NewLine% %NewLine%");
		kickscreen.add("&8-= &eServername &8=-");
		cfg.addDefault("CONIG.BANSYSTEM.KICKPLAYER.KICKSCREEN", kickscreen);
		cfg.addDefault("CONIG.BANSYSTEM.KICKPLAYER.KICKMESSAGE", "%PREFIX% &7Du hast &6%PLAYER% &7wegen &e%REASON% &7gekickt!");
		cfg.addDefault("CONIG.BANSYSTEM.KICKPLAYER.PLAYERBYPASS", "%PREFIX% &cDieser Spieler kann nicht gekickt werden!");
		//------------------------------------------------------------------------------------------------\\
		cfg.addDefault("CONIG.BANSYSTEM.UNBANPLAYER", "%PREFIX% &7Du hast &e%PLAYER% &7entbannt!");
		cfg.options().copyDefaults(true);
		saveConfig();
	}
	
	public String getMessage(String path){
		FileConfiguration cfg = Main.getFileManager().getMessagesFile().getConfig();
		FileConfiguration cfg2 = Main.getFileManager().getConfigFile().getConfig();
		String prefix = cfg2.getString("CONIG.BANSYSTEM.PREFIX").replace("&", "§");
		String message = cfg.getString(path).replace("&", "§");
		if(message.contains("%NewLine%")){
			message = message.replace("%NewLine%", "\n");
		}
		if(message.contains("%PREFIX%")){
			message = message.replace("%PREFIX%", prefix);
		}
		return message;
	}
}