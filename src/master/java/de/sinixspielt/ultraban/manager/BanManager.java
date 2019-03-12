package de.sinixspielt.ultraban.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import de.sinixspielt.ultraban.Main;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public class BanManager {

	public BanManager() {
		Main.getSqlManager().executeUpdate("CREATE TABLE IF NOT EXISTS UltraBans (UUID VARCHAR(100), NAME VARCHAR(100), PERMANENT INT(100), END VARCHAR(100), REASON VARCHAR(100), BANNEDBY VARCHAR(100), BANDATE VARCHAR(100))");
	}

	public static void banPlayer(UUID uuid, String NAME, boolean PERMANENT ,String reason, long seconds, String GebanntBANNEDBY) {
		String uuidconvert = String.valueOf(uuid);
		long current = System.currentTimeMillis();
		long millis = seconds * 1000L;
		long end = 0;
		if(seconds != -1L){
			end = current + millis;	
		}else{
			end = current + (Integer.MAX_VALUE * 5);
		}
		
		SimpleDateFormat date = new SimpleDateFormat("dd.MM-yyyy HH:mm:ss");
		String datum = date.format(new Date());

		if(PERMANENT == false){
			Main.getSqlManager().executeUpdate("INSERT INTO UltraBans (UUID, NAME, PERMANENT, END, REASON, BANNEDBY, BANDATE) VALUES ('"+ uuidconvert + "','" + NAME + "', '0' , '" + end + "','" + reason + "', '" + GebanntBANNEDBY + "', '" + datum + "')");
		}else if(PERMANENT == true){
			Main.getSqlManager().executeUpdate("INSERT INTO UltraBans (UUID, NAME, PERMANENT, END, REASON, BANNEDBY, BANDATE) VALUES ('"+ uuidconvert + "','" + NAME + "', '1' , '" + end + "','" + reason + "', '" + GebanntBANNEDBY + "', '" + datum + "')");	
		}
	}

	public static void unban(UUID uuid) {
		String uuidconvert = String.valueOf(uuid);
		Main.getSqlManager().executeUpdate("DELETE FROM UltraBans WHERE UUID='" + uuidconvert + "'");
	}

	public static boolean isBanned(UUID uuid) {
		String uuidconvert = String.valueOf(uuid);
		try {
			ResultSet rs = Main.getSqlManager().executeQuery("SELECT * FROM  `UltraBans` WHERE  `UUID` = '" + uuidconvert + "'");
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Integer isPERMANENTBanned(UUID uuid) {
		int i = 0;
		String uuidconvert = String.valueOf(uuid);
		try {
			ResultSet rs = Main.getSqlManager().executeQuery("SELECT * FROM  `UltraBans` WHERE  `UUID` = '" + uuidconvert + "'");
			if (rs.next()) {
				i = rs.getInt("PERMANENT");
			}
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public static String getReason(UUID uuid) {
		String reason = "";
		String uuidconvert = String.valueOf(uuid);
		if (isBanned(uuid)) {
			try {
				ResultSet rs = Main.getSqlManager().executeQuery("SELECT * FROM UltraBans WHERE UUID= '" + uuidconvert + "'");
				if (!rs.next() || (String.valueOf(rs.getString("REASON")) == null));
				reason = rs.getString("REASON");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reason;
	}

	public static Long getRestBannedTime(UUID uuid) {
		Long time = null;
		String uuidconvert = String.valueOf(uuid);
		if (isBanned(uuid)) {
			if(isPERMANENTBanned(uuid) == 0){
				try {
					ResultSet rs = Main.getSqlManager().executeQuery("SELECT * FROM UltraBans WHERE UUID= '" + uuidconvert + "'");
					if (!rs.next() || (Long.valueOf(rs.getLong("END")) == null));
					time = rs.getLong("END");
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
			return time;
		}
		return time;
	}

	public static String getRemainingTime(UUID uuid) {
		String result = "";
		if(isPERMANENTBanned(uuid) == 1){
			result = "§4PERMANENT";
			return result;
		}
		long current = System.currentTimeMillis();
		long end = getRestBannedTime(uuid);
		if (current == -1L) {
			result = "§4PERMANENT";
			return result;
		}
		long diff = end - current;

		long years = 0L;
		long months = 0L;
		long weeks = 0L;
		long days = 0L;
		long hours = 0L;
		long minutes = 0L;
		long seconds = 0L;
		while (diff >= 1000L) {
			diff -= 1000L;
			seconds += 1L;
		}
		while (seconds >= 60L) {
			seconds -= 60L;
			minutes += 1L;
		}
		while (minutes >= 60L) {
			minutes -= 60L;
			hours += 1L;
		}
		while (hours >= 24L) {
			hours -= 24L;
			days += 1L;
		}
		while (days >= 7L) {
			days -= 7L;
			weeks += 1L;
		}
		if (weeks > 0L) {
			if (weeks > 1L) {
				result = weeks + " Wochen ";
			} else {
				result = weeks + " Woche ";
			}
		}
		if (days > 0L) {
			if (days > 1L) {
				result = result + days + " Tage ";
			} else {
				result = result + days + " Tag ";
			}
		}
		if (hours > 0L) {
			if (hours > 1L) {
				result = result + hours + " Stunden ";
			} else {
				result = result + hours + " Stunde ";
			}
		}
		if (minutes > 0L) {
			if (minutes > 1L) {
				result = result + minutes + " Minuten ";
			} else {
				result = result + minutes + " Minute ";
			}
		}
		if (seconds > 0L) {
			if (seconds > 1L) {
				result = result + seconds + " Sekunden ";
			} else {
				result = result + seconds + " Sekunde ";
			}
		}
		return result;
	}
}