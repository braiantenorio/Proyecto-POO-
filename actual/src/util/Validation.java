package util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Validation {

	public static Double isDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Integer isInteger(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static LocalDate isDate(String dateStr) {
		try {
			return LocalDate.parse(dateStr);
		} catch (DateTimeParseException e) {
			return null;
		}

	}

	@Deprecated
	public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
