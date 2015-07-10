package app.note.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class MyDateUtils {

	public static int daysOfMonth(int month,int year) {	
		int isBissextile =  0;
		if(year%4 == 0 ||year%100 == 0) {
			isBissextile = 1;
		}
		switch(month){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				return 28 + isBissextile;
			default:
				return -1;
		}
	}
	
	public static String dateToString(int year, int month) {
		return dateToString(year,month,1).substring(0, 7);
	}
	
	public static String dateToString(int year, int month, int day) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c  = Calendar.getInstance();
		c.set(year, month - 1, day);
		Log.d("MyDateUtils",sf.format(c.getTime()));
		return sf.format(c.getTime());
	}
	

	public static Calendar stringToDate(String date) {
		SimpleDateFormat sf;
		Calendar c = Calendar.getInstance();
		if(date.length() < 7){
			return null;
		} else if(date.length() == 7) {
			 sf = new SimpleDateFormat("yyyy-MM");
		} else {
			sf = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		
		try {
			c.setTime(sf.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;	
	}
	
 }
