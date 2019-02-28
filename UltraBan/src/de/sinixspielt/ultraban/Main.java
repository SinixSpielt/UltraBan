package de.sinixspielt.ultraban;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.sinixspielt.ultraban.commands.CommandBan;
import de.sinixspielt.ultraban.commands.CommandKick;
import de.sinixspielt.ultraban.commands.CommandUnban;
import de.sinixspielt.ultraban.file.FileManager;
import de.sinixspielt.ultraban.listener.PlayerListener;
import de.sinixspielt.ultraban.manager.BanManager;
import de.sinixspielt.ultraban.mysql.SQLManager;

public class Main extends JavaPlugin{
	
	public static Main instance;
	public static FileManager fileManager;
	public static SQLManager sqlManager;
	public static BanManager banManager;

	@Override
	public void onEnable() {
		instance = this;
		fileManager = new FileManager();
		if (!loadSQL()) {
			return;
		}
		banManager = new BanManager();
		loadListeners();
		loadCommands();
	}
	
	private void loadListeners() {
		PluginManager load = Bukkit.getPluginManager();
		load.registerEvents(new PlayerListener(), this);
	}
	
	private void loadCommands() {
		getCommand("ban").setExecutor(new CommandBan());
		getCommand("unban").setExecutor(new CommandUnban());
		getCommand("kick").setExecutor(new CommandKick());
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
	
	public static BanManager getBanManager() {
		return banManager;
	}
}