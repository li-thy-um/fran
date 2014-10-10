package controller;

import helper.StringHelper
import model.orm.Product
import model.orm.Shop

public class ProductController extends BaseController<Product> {
	
    public void query(){
        renderList( Product.dao.listForCashier( getPara("cd")+"%", user() ) );
    }
	
	public void salesChart(){
		int year = getParaToInt("year");
		int week = getParaToInt("week");
		renderJson( Product.dao.findById( model.get("id") ).chart(year, week, shop()) );
	}
	
    public void sales(){	
		renderList( user().sales( Product.dao, shop()) );
	}

    public void avaliableProduct(){
		List<Map<String,String>> select = 
		
		Product.dao.listForManager( user().manager() ).collect { product ->
    		["text": product.toString() , "id": product._id]
    	}
		
		renderJson StringHelper.filter( select, getPara("query") )
    }
    
    @Override
    public void list() {
        Product dao = new Product();
    	List<Product> list = dao.listForManager( user() );
    	renderJson( dao.toJsonList(list) );
    }

    @Override
    public void create() {
    	model.set("manager_id", user()._id)
		model.unique() ? super.create() : renderJson("error", "Product "+model+" has already exsists.")
    }
	
	private Shop shop(){
		Shop.dao.findById( getPara("shop_id") );
	}
}
