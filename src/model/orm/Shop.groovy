

package model.orm;

import helper.DateHelper
import helper.Interval
import helper.MathHelper
import helper.SQLHelper
import helper.DateHelper.DateType
import model.BaseModel
import model.interfaces.Chartable
import model.interfaces.Salesable

public class Shop extends BaseModel<Shop> implements Salesable, Chartable {

	String _name;
	String _address;
	String _keeper_id;
	String _detail;
	
	public static Shop dao = new Shop();
	
	@Override
	public Shop updates() {
		if( _keeper_id != null ){
			findById( _id ).cashiers().each { cashier ->
				cashier.set("boss_id", _keeper_id).updates();
			}
		}
		return super.updates();
	}
	
	public BigDecimal invenOf( Product product ){
		Inventory.dao.invenOf( this, product );
	}
	
	@Override
	public Shop destroy() {
		findById( _id ).cashiers().each{ cashier ->
			cashier.set("shop_id", null).updates();
		}
		return super.destroy();
	}
	
	public averageMonthSale( Product product ){
		Interval inter = DateHelper.intervalOf( 30 )
		String sql = new SQLHelper( TransactionProduct.class ).getSQL(
			"transaction_id in ( select id from fe_transaction where shop_id = ? )"
		  + "and product_id = ? and create_at between ? and ?"
		)
		TransactionProduct.dao.find(sql, _id, product._id, inter.begin, inter.end )
			.inject (0) { amount, tp -> amount + tp._amount } / inter.days();
	}
	
	public BigDecimal invenStatus(){
		inventory().inject(Integer.MAX_VALUE) { days, inven ->
			Math.min(days, inven.status().toInteger())
		}
	}
	
	public List<Inventory> inventory(){
		Inventory.dao.listForShop(_id);
	}
	
	@Override
	public Map<String, Object> toJsonObject() {
		Map<String,Object> json = super.toJsonObject();
		json.put("shop.keeper_id", keeper()?.display())
		json
	}
	
	public User keeper(){
		User.dao.findById( _keeper_id )
	}
	
	public List<User> cashiers(){
		return User.dao.findListBy("boss_id", get("keeper_id"));
	}

	@Override
	public BigDecimal sales(Interval inter) {
		Transaction.of(this, inter).inject(0) { total, tran -> 
			total + tran.total()
		}
	}
	
	@Override
	public List<Salesable> listForUser(User user) {
		List<Salesable> list = new ArrayList<Salesable>();
		for( Shop s : findListBy( "keeper_id", user.keepers().toArray() ) ){
			list.add(s);
		}
		return list;
	}

	@Override
	public BigDecimal increase(Interval last, Interval thiz) {
		MathHelper.increase( sales( last ), sales( thiz ) );
	}

	@Override
	public Map<String, Object> chart(int year, int week) {
		List<Interval> intervals = Interval.getIntervals( year, week );

		["labels": Interval.labels(intervals),
		 "sales": salesList(intervals),
		 "s": this.toJsonObject()]
	}

	private List<BigDecimal> salesList(List<Interval> intervals) {
		intervals.collect { inter -> sales(inter) }
	}
}
