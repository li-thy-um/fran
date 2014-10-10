package helper;

public class RoleHelper {

	public static String subRole(String role) {
		if( "admin".equals(role)) return "manager";
		if( "manager".equals(role)) return "keeper";
		if( "keeper".equals(role)) return "cashier";
		return null;
	}

}
