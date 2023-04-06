package br.com.diegomarques.meusgastos.commons;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ConverterLocalDate {
	
	public static String Converter(LocalDate localDate) {
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYY HH:mm:ss");
		return format.format(localDate);
	}

}
