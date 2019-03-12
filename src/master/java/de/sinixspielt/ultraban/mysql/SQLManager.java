package de.sinixspielt.ultraban.mysql;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.sinixspielt.ultraban.Main;

public class SQLManager {
	
	private String host;
	private String port;
	private String user;
	private String pass;
	private String database;
	private Connection conn;
	private final StayAliveTask aliveTask;
	private final AsyncHandler asyncHandler;
	private final Updater updater;

	public SQLManager(String host, String port, String user, String pass, String database) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.database = database;
		this.aliveTask = new StayAliveTask(this);
		this.asyncHandler = new AsyncHandler();
		this.updater = new Updater();
	}

	public boolean openConnection() {
		try {
			if ((this.conn != null) && (!this.conn.isClosed())) {
				return false;
			}
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
					this.user, this.pass);
			this.aliveTask.setActive(true);
			this.aliveTask.start();
			this.updater.setActive(true);
			this.updater.start();
			return true;
		} catch (Exception e) {
			Main.getInstance().getLogger().warning("Verbindung konnte nicht geöffnet werden: " + e.getMessage());
		}
		return false;
	}

	public Connection getConnection() {
		try {
			if ((this.conn == null) || (this.conn.isClosed())) {
				openConnection();
			}
		} catch (Exception localException) {
		}
		return this.conn;
	}

	public void closeConnection() {
		try {
			if ((this.conn != null) && (!this.conn.isClosed())) {
				this.conn.close();
				this.conn = null;
				this.aliveTask.setActive(false);
			}
		} catch (Exception e) {
			Main.getInstance().getLogger().warning("Verbindung konnte nicht geschlossen werden: " + e.getMessage());
		}
	}

	public void close(PreparedStatement st, ResultSet rs) {
		try {
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception localException) {
		}
	}

	public void executeUpdate(String statement) {
		try {
			PreparedStatement st = this.conn.prepareStatement(statement);
			st.executeUpdate();
			close(st, null);
		} catch (Exception e) {
			Main.getInstance().getLogger().warning("executeUpdate konnte nicht ausgeführt werden: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void executeUpdate(PreparedStatement statement) {
		try {
			statement.executeUpdate();
			close(statement, null);
		} catch (Exception e) {
			Main.getInstance().getLogger().warning("executeUpdate konnte nicht ausgeführt werden: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String statement) {
		try {
			PreparedStatement st = this.conn.prepareStatement(statement);
			return st.executeQuery();
		} catch (Exception e) {
			Main.getInstance().getLogger().warning("executeQuery konnte nicht ausgeführt werden: " + e.getMessage());
		}
		return null;
	}

	public ResultSet executeQuery(PreparedStatement statement) {
		try {
			return statement.executeQuery();
		} catch (SQLException e) {
			Main.getInstance().getLogger().warning("executeQuery konnte nicht ausgeführt werden: " + e.getMessage());
		}
		return null;
	}

	public AsyncHandler getAsyncHandler() {
		return this.asyncHandler;
	}

	public StayAliveTask getAliveTask() {
		return this.aliveTask;
	}

	public Updater getUpdater() {
		return this.updater;
	}
}