package helper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


public class DateHelper {

	public enum DateType { WEEK, MONTH, YEAR, LAST_WEEK, LAST_MONTH, LAST_YEAR }

	public static final long DAY = 24*3600*1000;
	
    private static Timestamp beginOf( int bar ){
    	Calendar c = Calendar.getInstance();
        c.set( bar , 1 );
        String begin = new StringBuffer( String.valueOf( c.get(Calendar.YEAR) ))
        	.append("-").append( pre0( c.get(Calendar.MONTH) + 1 ) )
        	.append("-").append( pre0( c.get(Calendar.DATE) ) )
        	.append(" 00:00:00").toString();
        return  Timestamp.valueOf(begin);
    }

    private static String pre0( int i ){
    	String s = String.valueOf(i);
    	if( s.length() == 1 ){
    		return new StringBuilder("0").append(s).toString();
    	}
    	return String.valueOf(i);
    }

	public static Timestamp beginOf(DateType type) {
		if ( type == DateType.WEEK ){
			return beginOf(Calendar.DAY_OF_WEEK);
		}
		if ( type == DateType.MONTH ){
			return beginOf(Calendar.DAY_OF_MONTH);
		}
		if ( type == DateType.YEAR ){
			return beginOf(Calendar.DAY_OF_YEAR);
		}
		if ( type == DateType.LAST_WEEK ){
			return new Timestamp( beginOf(DateType.WEEK).getTime() - 7*DAY );
		}
		if ( type == DateType.LAST_MONTH ){
			return new Timestamp( beginOf(DateType.MONTH).getTime() - countDay( type )*DAY );
		}
		if ( type == DateType.LAST_YEAR ){
			return new Timestamp( beginOf(DateType.YEAR).getTime() - countDay( type )*DAY );
		}
		return null;
	}

	private static long countDay( DateType type ){
		Calendar c = Calendar.getInstance();
		if ( type == DateType.LAST_MONTH ){
			int month = c.get(Calendar.MONTH) - 1;
			if( month < 0 ) return 31;
			c.set( Calendar.MONTH, month );
			return c.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		if ( type == DateType.LAST_YEAR ){
			c.add(Calendar.YEAR, -1);
			return c.getActualMaximum(Calendar.DAY_OF_YEAR);
		}
		return 0;
	}

	public static Timestamp endOf( DateType dateType ) {
		if( dateType == DateType.LAST_WEEK ){
			return beginOf( DateType.WEEK );
		}
		if( dateType == DateType.LAST_MONTH ){
			return beginOf( DateType.MONTH );
		}
		if( dateType == DateType.LAST_YEAR ){
			return beginOf( DateType.YEAR );
		}
		return sysTime();
	}
	
	public static Timestamp sysTime( long addon ){
        return new Timestamp( new Date().getTime() + addon );
    }

    public static Timestamp sysTime(){
        return sysTime( 0 );
    }
	
	public static Interval intervalOf( Integer days ){
		Interval inter = new Interval();
		inter.end = sysTime();
		inter.begin = sysTime( - ( days * DAY ) );
		return inter;
	}
	
	public static Interval intervalOf( DateType type ) {
		Interval inter = new Interval();
		inter.begin = beginOf(type);
		inter.end = endOf(type);
		return inter;
	}
}
