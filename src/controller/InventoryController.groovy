package controller;

import model.orm.Inventory

public class InventoryController extends BaseController<Inventory> {
	
	@Override
	public void list() {
		List<Inventory> inven = Inventory.dao.listForShop( getPara("shop_id") );
		renderJson( Inventory.dao.toJsonList(inven) );
	}
}
