package br.org.pmc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Classe para geração e formatação de datas
 * 
 * @author PlinioMos
 *
 */
public class MyDateGenerator {

	/**
	 * Converte uma data no formato {@link String} para o formato {@link Date}
	 * 
	 * @param dateString
	 * @return {@link Date}
	 */
	public static Date dateStringToSql(String dateString) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		try {
			return format.parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date timeStringToSql(String timeString) {
		DateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
		try {
			return format.parse(timeString);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date dateStringToDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;

		try {
			date = formatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static Date getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dateFormatted = null;
		try {
			dateFormatted = new java.sql.Date(format.parse(
					dateFormat.format(date)).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateFormatted;
	}
	
	public static Date decrementSecond(int ano, int mes, int dia, int hora,
			int minutos) {
		
		Calendar 
			cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, ano);
			cal.set(Calendar.MONTH, mes);
			cal.set(Calendar.DAY_OF_MONTH, dia);
			cal.set(Calendar.HOUR_OF_DAY, hora);
			cal.set(Calendar.MINUTE, minutos);
			cal.set(Calendar.SECOND, -1);

			Date horaSaidaFinal = cal.getTime();
			String dataString = "HH:mm:ss";
			SimpleDateFormat spd = new SimpleDateFormat(dataString);
			spd.format(horaSaidaFinal);
			
		return horaSaidaFinal;
	}
}