package controller;

import helper.EncodeHelper
import helper.StringHelper
import model.orm.Shop
import model.orm.User

public class UserController extends BaseController<User> {

	@Override
	public void destroy() {
		String error = ((User)model).checkDestroy()
		if( error ) {
			renderJson("error" : error)
		}else{
			super.destroy();
		}
	}
	
	public void sales(){
		renderJson( User.dao.toJsonList( user().sales( User.dao ) ) );
	}
	
	public void salesChart(){
		int year = getParaToInt("year");
		int week = getParaToInt("week");
		model = User.dao.findById( model.get("id") );
		renderJson( ((User)model).chart(year, week) );
	}
	
	public void avaliableShop(){
		List<Map<String,String>> select = user().avaliableShop().collect{ shop ->
			[ "text": shop._name, "id": shop._id ]
		}
		
		renderJson StringHelper.filter( select, getPara("query") )
	}
	
	public void avaliableKeeper(){		
		List<Map<String,String>> select = user().avaliableKeeper().collect{ keeper ->
			[ "text": keeper.display(), "id": keeper._id ]
		}
		
		renderJson StringHelper.filter( select, getPara("query") )
	}
	
    @Override
    public void create() {
    	if( ! model.unique("email") ){
            renderJson("error", "User "+model.getStr("email")+" has already exsists.");
            return;
        }
        renderJson( user().initSubUser( model ).create() );
    }
    
    @Override
    public void updates() {
    	if( ! model.unique("email") ){
            renderJson("error", "User "+model.getStr("email")+" has already exsists.");
            return;
        }
    	super.updates();
    }
    
    public void changePsw(){
    	String old_ = EncodeHelper.encrypt( getPara("old_psw") );
    	String new_ = getPara("psw");
    	String confirm = getPara("pswc");
    	
    	if( ! new_.equals(confirm) ){
    		renderJson( "error", "Confirm Password should be the same as Password." );
    		return;
    	}
    	if( user().getStr("psw").equals(old_) ){
    		new_ = EncodeHelper.encrypt(new_);
    		renderJson( user().set("psw", new_).updates() );
    	}else{
    		renderJson( "error", "Old password is incorrect." );
    	}
    }
    
    @Override
    public void list() {
    	renderJson( model.toJsonList( user().subUser() ) );
    }
}