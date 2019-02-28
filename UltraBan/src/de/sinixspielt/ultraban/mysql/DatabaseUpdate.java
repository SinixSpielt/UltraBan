package de.sinixspielt.ultraban.mysql;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

import java.util.ArrayList;
import java.util.List;

import de.sinixspielt.ultraban.Main;

public abstract class DatabaseUpdate {
	
	private boolean update;
	private boolean ready;
	private boolean forceUpdate;
	private List<ReadyExecutor> readyExecutors;

	public DatabaseUpdate() {
		this.readyExecutors = new ArrayList();
		this.forceUpdate = false;
	}

	public List<ReadyExecutor> getReadyExecutors() {
		return this.readyExecutors;
	}

	public void addReadyExecutor(ReadyExecutor exec) {
		if (this.ready) {
			exec.ready();
			return;
		}
		this.readyExecutors.add(exec);
	}

	public boolean isUpdate() {
		return this.update;
	}

	public boolean isReady() {
		return this.ready;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
		if (ready) {
			for (ReadyExecutor exec : this.readyExecutors) {
				exec.ready();
			}
			this.readyExecutors.clear();
		}
	}

	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public boolean isForceUpdate() {
		return this.forceUpdate;
	}

	public void addToUpdater() {
		Main.getSqlManager().getUpdater().addToUpdater(this);
	}

	public void removeFromUpdater() {
		Main.getSqlManager().getUpdater().removeFromUpdater(this);
	}

	public abstract void saveData();

	public abstract void saveDataAsync();

	public abstract void loadData();

	public abstract void loadDataAsync();

	public static abstract interface ReadyExecutor {
		public abstract void ready();
	}
}