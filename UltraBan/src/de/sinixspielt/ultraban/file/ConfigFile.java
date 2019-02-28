package de.sinixspielt.ultraban.file;

import org.bukkit.configuration.file.FileConfiguration;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public class ConfigFile extends FileBase {
	
	public ConfigFile() {
		super("", "config");
		writeDefaults();
	}

	private void writeDefaults() {
		FileConfiguration cfg = getConfig();
		cfg.addDefault("CONIG.BANSYSTEM.PREFIX", "&8[&cUltraBan&8]");
		
		cfg.addDefault("CONIG.BAN.USEPERMISSION", "ultraban.ban");
		cfg.addDefault("CONIG.BAN.BYPASSPERMISSION", "ultraban.ban.bypass");
		
		cfg.addDefault("CONIG.KICK.USEPERMISSION", "ultraban.kick");
		cfg.addDefault("CONIG.KICK.BYPASSPERMISSION", "ultraban.kick.bypass");
		
		cfg.addDefault("CONIG.TEMPBAN.USEPERMISSION", "ultraban.tempban");
		cfg.addDefault("CONIG.TEMPBAN.BYPASSPERMISSION", "ultraban.tempban.bypass");
		cfg.options().copyDefaults(true);
		saveConfig();
	}
}