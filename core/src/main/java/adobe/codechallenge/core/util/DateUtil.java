package adobe.codechallenge.core.util;

import java.util.Date;

public class DateUtil {
	
	public static String getCurrentDateTime() {
		
		Date date = new Date();
		return date.toString()+date.getTime();
	}
}
