package helper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Interval {
	public Timestamp begin;
	public Timestamp end;
	public String label;
	
	/**
	 * Count the days include in this interval.
	 */
	public int days(){
		( end.getTime() - begin.getTime() ) / ( 24 * 3600 * 1000 )
	}
	
	public String toString(){
		return begin.toString().split(" ")[0] + "|" + end.toString().split(" ")[0];
	}
	
	private static Timestamp getTimestamp(int y, int m, int d, String time){
		String timeString = y + "-"+( m < 10 ? "0"+m : m )+"-"+( d < 10 ? "0"+d : d )+" "+time;
		return Timestamp.valueOf(timeString);
	}
	
	public static List<Interval> getIntervals( int year, int week ) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		
		List<Interval> rslt = new ArrayList<Interval>();
	
		for( int day = 1; day <= 7; day ++ ){
		    c.set(Calendar.DAY_OF_WEEK, day);
		    int month = c.get(Calendar.MONTH)+1;
		    int date = c.get(Calendar.DATE);
			Timestamp begin = getTimestamp( year, month, date, "00:00:00" );
			Timestamp end = getTimestamp( year, month, date, "23:59:59" );
			Interval inter = new Interval();
			inter.begin = begin;
		    inter.end = end;
		    inter.label = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.PRC);
			rslt.add( inter );
		}
		
		return rslt;
	}

	public static List<String> labels(List<Interval> intervals) {
		intervals.collect { inter -> inter.label }
	}
}
