package de.sinixspielt.ultraban.utils;

import java.util.ArrayList;
import java.util.List;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public enum BanUnit {
	
	SECOND("Sekunde(n)", 1L, "s"), MINUTE("Minute(n)", 60L, "m"), HOUR("Stunde(n)", 3600L, "h"), DAY("Tag(e)", 86400L,"d"), WEEK("Woche(n)", 604800L, "w");

	private String name;
	private long toSecond;
	private String shortcut;

	private BanUnit(String name, long toSecond, String shortcut) {
		this.name = name;
		this.toSecond = toSecond;
		this.shortcut = shortcut;
	}

	public long getToSecond() {
		return this.toSecond;
	}

	public String getName() {
		return this.name;
	}

	public String getShortcut() {
		return this.shortcut;
	}

	public static List<String> getUnitsAsString() {
		List<String> units = new ArrayList();
		BanUnit[] arrayOfBanUnit;
		int j = (arrayOfBanUnit = values()).length;
		for (int i = 0; i < j; i++) {
			BanUnit unit = arrayOfBanUnit[i];
			units.add(unit.getShortcut());
		}
		return units;
	}

	public static BanUnit getUnit(String unit) {
		BanUnit[] arrayOfBanUnit;
		int j = (arrayOfBanUnit = values()).length;
		for (int i = 0; i < j; i++) {
			BanUnit units = arrayOfBanUnit[i];
			if (units.getShortcut().toLowerCase().equals(unit.toLowerCase())) {
				return units;
			}
		}
		return null;
	}
}