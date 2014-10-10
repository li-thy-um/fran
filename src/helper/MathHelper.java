package helper;

import java.math.BigDecimal;

public class MathHelper {

	public static BigDecimal increase( BigDecimal lastSale, BigDecimal thisSale){
		if( lastSale == null || lastSale.compareTo(BigDecimal.ZERO) == 0){
			return new BigDecimal("-101"); // New Sale.
		}
		if( thisSale == null ) thisSale = BigDecimal.ZERO;
		return thisSale
				.subtract(lastSale)
				.divide(lastSale, 4, BigDecimal.ROUND_HALF_UP)
				.multiply(BigDecimal.TEN)
				.multiply(BigDecimal.TEN).setScale(2);
	}
}