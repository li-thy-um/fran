package model.orm;

import java.math.RoundingMode;

import helper.SQLHelper;
import model.BaseModel;

public class Inventory extends BaseModel<Inventory> {
	String _product_id;
	String _shop_id;
	BigDecimal _amount; 
	
	public static Inventory dao = new Inventory();
	
	public BigDecimal invenOf( Shop shop, Product product ){
		String sql = new SQLHelper( Inventory.class ).getSQL("shop_id = ? and product_id = ?")
		Inventory inven = findFirst(sql, shop._id, product._id)
		inven ? inven._amount : 0
	}
	
	@Override
	public Inventory refresh() {
		Inventory inven = super.refresh()
		inven.put("status", inven.status())
	}
	
	public Inventory out( TransactionProduct tp ){
		findBy( tp ).minus( tp._amount ).updates()
	}
	
	public Map<String,String> check( List<TransactionProduct> tps, Shop shop ){
		for( tp in tps ){
			Inventory inven = findBy(shop._id, tp._product_id)
			Product product = tp.product()
			if ( inven == null || inven._amount == 0 ){
				return ["error": "Stock of " + product.toString() + " is empty."]
			}else if( inven._amount < tp._amount ){
				return ["error": "Stock of " + product.toString() + " is not enough. (Only " + inven._amount + " left.)"]
			}
		}
		["success" : ""]
	}
	
	/**
	 * Estimate how many days the stock will sold out.
	 * 
	 * 1.Compute the average sale amount per day of last month.
	 * 2.Divide the inventory amount by that then get the day_count. 
	 */
	public BigDecimal status(){		
		def average = shop().averageMonthSale( product() )
		average == 0 ? Integer.MAX_VALUE :
		( _amount / average ).setScale(0, RoundingMode.FLOOR)
	}
	
	public List<Inventory> listForShop( String shop_id ){
		List<Inventory> list = findListBy("shop_id", shop_id);
		list.each { inven ->
			inven.put( "product", inven.product() )
				 .put( "status", inven.status() )
		}
		list;
	}
	
	private Inventory minus( BigDecimal amount){
		add( - amount )
	} 
	
	private Inventory add( BigDecimal amount ){
		amount = _amount + amount
		amount = amount > 0 ? amount : 0;
		set("amount", amount)
	}
	
	public Product product(){
		Product.dao.findById(_product_id)
	}
	
	public Shop shop(){
		Shop.dao.findById(_shop_id)
	}
	
	@Override
	public Inventory create() {
		Inventory inven = findBy( _shop_id, _product_id );
		inven ? inven.add( _amount ).updates() : super.create()
	}
	
	//warning : maybe not used.
	public Inventory findBy( TransactionProduct tp ){
		findBy( tp.shop()._id, tp._product_id );
	}
	
	public Inventory findBy( String shop_id, String product_id ){
		String sql = new SQLHelper( Inventory.class ).getSQL("shop_id = ? and product_id = ?");
		findFirst( sql, shop_id, product_id );
	}
}
