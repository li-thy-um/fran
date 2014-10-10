package model.orm;

import helper.Interval;

import java.util.List;

import model.BaseModel;

public class Transaction extends BaseModel<Transaction> {

	String _cashier_id;
	String _keeper_id;
	String _manager_id;
	String _shop_id;
	
	public static Transaction dao = new Transaction();

	public static Transaction createBy( User user ){
		return new Transaction()
			.set("cashier_id", user._id)
			.set("keeper_id", user.boss()._id)
			.set("manager_id", user.manager()._id)
			.set("shop_id", user.shop()._id)
			.create();
	}
	
	public static List<Transaction> of( Shop shop , Interval inter ){
		dao.listInBetween( "create_at", inter.begin, inter.end, "shop_id", shop );
	}
	
	public static List<Transaction> of( User user, Interval inter ){
		dao.listInBetween( "create_at", inter.begin, inter.end, "${user._role}_id", user );
	}
	
	public BigDecimal total(){
		items().inject(0) { total, tp -> total + tp.total() }
	}

	public List<TransactionProduct> items(){
		TransactionProduct.dao.findListBy("transaction_id", _id);
	}
}
