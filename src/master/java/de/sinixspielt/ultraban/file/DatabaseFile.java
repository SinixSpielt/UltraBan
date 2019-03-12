package de.sinixspielt.ultraban.file;

import org.bukkit.configuration.file.FileConfiguration;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public class DatabaseFile extends FileBase{
	
	public DatabaseFile() {
		super("", "database");
		writeDefaults();
	}
	
	private void writeDefaults() {
		FileConfiguration cfg = getConfig();

		cfg.addDefault("DATABASE.HOST", "localhost");
		cfg.addDefault("DATABASE.PORT", "3306");
		cfg.addDefault("DATABASE.USER", "username");
		cfg.addDefault("DATABASE.PASSWORD", "password");
		cfg.addDefault("DATABASE.DATABASE", "dbname");
		
		cfg.options().copyDefaults(true);
		saveConfig();
	}
}