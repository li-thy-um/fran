package model.orm;

import helper.Interval
import helper.MathHelper
import helper.SQLHelper
import model.BaseModel
import model.interfaces.Chartable
import model.interfaces.Salesable

public class Product extends BaseModel<Product> implements Salesable, Chartable {

    String _cd;
    String _name;
    String _image;
    BigDecimal _price;
    String _detail;
    String _status;
    String _manager_id;
    
    public static Product dao = new Product();
	    
	private BigDecimal averagePrice( Interval inter, Shop shop ) {
		
		BigDecimal s  = BigDecimal.ZERO;
		BigDecimal a = BigDecimal.ZERO;
		
		if( shop == null ){
			s = sales( inter );
			a = amount( inter );
		}else{
			s = sales( inter, shop );
			a = amount( inter, shop );
		}
		
		if( a.compareTo(BigDecimal.ZERO) == 0 ) return s; 
		
		return s.divide( a, 2, BigDecimal.ROUND_HALF_EVEN );
	}
	
    public List<Product> listForCashier( Object value, User cashier ) {
    	String sql = new SQLHelper(Product.class).getSQL(
    		"cd like ? and manager_id = ? and status = 'Y'"
    	);
    	find(sql, value, cashier.manager()._id).findAll { product ->
			cashier.shop().invenOf( product ) > 0
		}
    }
	
    public List<Product> listForManager( User manager ) {
    	return findListBy("manager_id", manager);
    }
	
    @Override
    public Map<String, Object> chart( int year, int week ){    	
    	List<Interval> intervals = Interval.getIntervals( year, week );

		[ "labels" : Interval.labels( intervals ),
		  "sales"  : salesList( intervals, 0, null ),
		  "price"  : salesList( intervals, 1, null ),
		  "p"      : toJsonObject()]
    }
	
	public Map<String, Object> chart( int year, int week, Shop shop ){
		List<Interval> intervals = Interval.getIntervals( year, week );

		[ "labels" : Interval.labels( intervals ),
		  "sales"  : salesList( intervals, 0, shop ),
		  "price"  : salesList( intervals, 1, shop ),
		  "p"      : toJsonObject()]
	}
    
    /**
	 * 
	 * @param type 0: sales other : average price;
	 * @return
	 */
    private List<BigDecimal> salesList( List<Interval> intervals, int type, Shop shop){
		intervals.collect { inter ->
			type == 0 ? ( shop ? sales(inter, shop) : sales(inter) ) : averagePrice(inter, shop);
		}
    }
    
	@Override
	public List<Salesable> listForUser( User user ) {
		listForManager( user.manager() ) 
	}
	
	@Override
	public BigDecimal increase( Interval last, Interval thiz ) {
		MathHelper.increase( sales( last ), sales( thiz ) );
	}
	
	public BigDecimal amount( Interval inter ) {
	    value( inter, 1 );
	}
	
	@Override
	public BigDecimal sales( Interval inter ) {	
		value( inter, 0);
	}
	
	public BigDecimal sales( Interval inter, Shop shop ){
		value( inter, 0, shop )	
	}
	
	public BigDecimal amount( Interval inter, Shop shop ){
		value( inter, 1, shop )
	}
	
	public BigDecimal increase( Interval last, Interval thiz, Shop shop ){
		MathHelper.increase( sales( last, shop ), sales( thiz, shop ) );
	}

	/**
	 * 
	 * @param type 0: price other : amount;
	 * @return
	 */
	private BigDecimal value( Interval inter, int type ){
		TransactionProduct.of( this, inter ).inject(0){ value, tp ->
			value + ( type == 0 ? tp.total() : tp._amount )
		}
	}
	
	private BigDecimal value( Interval inter, int type, Shop shop ){
		TransactionProduct.of( shop, this, inter ).inject(0){ value, tp ->
			value + ( type == 0 ? tp.total() : tp._amount )
		}
	}
	
    @Override
    public String toString() {
		"${_name}(${_cd})"
    }
    
    public boolean unique() {
    	String sql = new SQLHelper(Product.class).getSQL("manager_id = ? and cd = ?");
    	return find(sql, this.getStr("manager_id"), this.getStr("cd") ).size() == 0;
    }
}
