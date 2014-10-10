package controller;

import model.orm.Inventory
import model.orm.Transaction
import model.orm.TransactionProduct

import com.jfinal.aop.Before
import com.jfinal.plugin.activerecord.tx.Tx

public class TransactionController extends BaseController<Transaction> {

	@Override
	@Before(Tx.class)
	public void create() {	
		
		int size = getParaToInt("size");
		
		List<TransactionProduct> tp_list = (0 ..< size).collect { i ->
			getModel(TransactionProduct.class, "tp[" + i + "]")
	    }
		
		Map<String, String> check = Inventory.dao.check( tp_list, user().shop() )
		
		if ( check["error"] != null ){ 
			
			renderJson( check )
		
		} else {
			
			Transaction tran = Transaction.createBy( user() );
		
			tp_list = tp_list.findAll { it._amount > 0 }
			
			tp_list.each { tp -> tp.set("transaction_id", tran._id) }
		
			TransactionProduct.dao.batchCreate( tp_list )
				
			renderJson ( "success", "" )
		}
        
	}
}
