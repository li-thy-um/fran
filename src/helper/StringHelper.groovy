package helper;

import java.util.List;
import java.util.Map;

public class StringHelper {

    public static String capitalize(String fieldName) {
        return String.valueOf( fieldName.charAt(0) ).toUpperCase() + fieldName.substring(1);
    }
	
	public static List<Map<String,String>> filter( List<Map<String,String>> select, String query ){
		select.findAll { m ->
			String text = m.get("text");
			text.matches(".*"+query+".*");
		}
	}

}
