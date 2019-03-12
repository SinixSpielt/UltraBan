package de.sinixspielt.ultraban.file;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public class FileManager {

	private ConfigFile configFile;
	private DatabaseFile databaseFile;
	private MessagesFile messagesFile;
	
	public FileManager() {
		this.configFile = new ConfigFile();
		this.databaseFile = new DatabaseFile();
		this.messagesFile = new MessagesFile();
	}
	
	public MessagesFile getMessagesFile() {
		return messagesFile;
	}
	
	public ConfigFile getConfigFile() {
		return configFile;
	}
	
	public DatabaseFile getDatabaseFile() {
		return databaseFile;
	}
}