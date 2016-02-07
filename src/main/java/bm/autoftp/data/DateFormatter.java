package bm.autoftp.data;


import java.text.SimpleDateFormat;

public class DateFormatter {
	private static SimpleDateFormat instance = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	private DateFormatter(){
		
	}
	
	 public static SimpleDateFormat getInstance(){
	      return instance;
	   }
	
	
}
