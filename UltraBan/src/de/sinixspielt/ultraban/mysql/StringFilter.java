package de.sinixspielt.ultraban.mysql;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.sql.RowSet;
import javax.sql.rowset.Predicate;

public class StringFilter implements Predicate {
	
	List<String> strings;
	String columnName;

	public StringFilter(String columnName, String... strings) {
		this.columnName = columnName;
		this.strings = Arrays.asList(strings);
	}

	public boolean evaluate(RowSet rs) {
		try {
			if (rs.getRow() == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();

			boolean found = false;
			try {
				String current = rs.getString(this.columnName);
				for (int i = 0; i < this.strings.size(); i++) {
					String currentFilter = (String) this.strings.get(i);
					if (current.equals(currentFilter)) {
						found = true;
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return found;
		}
		return false;
	}

	public boolean evaluate(Object value, int column) throws SQLException {
		return false;
	}

	public boolean evaluate(Object value, String columnName) throws SQLException {
		if (!columnName.equals(this.columnName)) {
			return false;
		}
		boolean found = false;
		String current = (String) value;
		for (int i = 0; i < this.strings.size(); i++) {
			String currentFilter = (String) this.strings.get(i);
			if (current.equals(currentFilter)) {
				found = true;
			}
		}
		return found;
	}
}
