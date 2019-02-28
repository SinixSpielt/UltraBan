package de.sinixspielt.ultraban;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.sinixspielt.ultraban.file.FileManager;
import de.sinixspielt.ultraban.mysql.SQLManager;

public class Main extends JavaPlugin{
	
	public static Main instance;
	public static SQLManager sqlManager;
	public static FileManager fileManager;

	@Override
	public void onEnable() {
		fileManager = new FileManager();
	}
	
	private boolean loadSQL() {
		FileConfiguration cfg = fileManager.getDatabaseFile().getConfig();
		String host = cfg.getString("DATABASE.HOST");
		String port = cfg.getString("DATABASE.PORT");
		String user = cfg.getString("DATABASE.USER");
		String pass = cfg.getString("DATABASE.PASSWORD");
		String database = cfg.getString("DATABASE.DATABASE");
		sqlManager = new SQLManager(host, port, user, pass, database);
		return sqlManager.openConnection();
	}

	@Override
	public void onDisable() {
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static SQLManager getSqlManager() {
		return sqlManager;
	}
	
	public static FileManager getFileManager() {
		return fileManager;
	}
}