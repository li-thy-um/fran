package model.orm;

import helper.DateHelper;
import helper.DateHelper.DateType;
import helper.Interval

import java.math.RoundingMode

import com.jfinal.plugin.activerecord.Db;

import model.BaseModel

public class TransactionProduct extends BaseModel<TransactionProduct> {

	String _cd;
    String _name;
    String _image;
    BigDecimal _price;
    String _detail;

    String _product_id;
    String _transaction_id;
    BigDecimal _amount;
    
    public static TransactionProduct dao = new TransactionProduct();

	public Shop shop(){
		Shop.dao.findById  transaction()._shop_id 
	}
	
	public Transaction transaction(){
		Transaction.dao.findById _transaction_id
	}
	
	public Product product(){
		Product.dao.findById _product_id 
	}
	
	@Override
	public TransactionProduct create() {
		Inventory.dao.out( this )
		super.create()  
	}
	
    public boolean batchCreate( List<TransactionProduct> tps ){
		tps.inject( true ) { success, tp -> success && tp.create() != null }
    }

    public BigDecimal total(){
		(_price * _amount).setScale(2, RoundingMode.HALF_EVEN);
	}
	
	public static List<TransactionProduct> of( Product product, Interval inter) {
		dao.listInBetween( "create_at", inter.begin, inter.end, "product_id", product );
	}
	
	public static List<TransactionProduct> of( Shop shop, Product product, Interval inter) {
		of( product, inter ).findAll { tp ->
			tp.shop()._id == shop._id
		}
	}
}
