package de.sinixspielt.ultraban.mysql;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

import java.sql.Connection;
import java.sql.PreparedStatement;

public class StayAliveTask extends Thread {
	private SQLManager manager;
	private boolean active;

	public StayAliveTask(SQLManager manager) {
		this.manager = manager;
		this.active = true;
	}

	public SQLManager getManager() {
		return this.manager;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void run() {
		while (this.active) {
			try {
				Connection conn = this.manager.getConnection();
				PreparedStatement st = conn.prepareStatement("/* ping */ SELECT 1");
				st.executeQuery();
				Thread.sleep(300000L);
			} catch (Exception e) {
				setActive(false);
			}
		}
	}
}