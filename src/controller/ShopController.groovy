package controller;

import model.orm.Shop;

public class ShopController extends BaseController<Shop> {

	public void sales(){
		List<Shop> sales = user().sales( Shop.dao );
		Shop total = new Shop().set("id", "total_562610360_shop")
			.set("name", "Total")
		sales << user().totalSalesOf( sales, total )
        renderJson( Shop.dao.toJsonList( sales ));
	}
	
	@Override
	public void create() {
		if( ! model.unique("name") ){
			renderJson("error", "Shop "+model.getStr("name")+" has already exsists.");
			return;
		}
		super.create();
	}
	
	@Override
	public void updates() {
		if( ! model.unique("name") ){
			renderJson("error", "Shop "+model.getStr("name")+" has already exsists.");
			return;
		}
		super.updates();
	}
	
	
	 public void salesChart(){
    	int year = getParaToInt("year");
    	int week = getParaToInt("week");
    	model = Shop.dao.findById( model.get("id") );
    	renderJson( ((Shop)model).chart(year, week) );
    }
	 
	 public void inventory(){
		 renderJson Shop.dao.toJsonList( 
			 user().shops().collect{ shop->
				 shop.put("inventory", shop.invenStatus() )
		 })
	 }
	 
	 public void list(){
		 renderJson Shop.dao.toJsonList( user().shops() )
	 }
}
