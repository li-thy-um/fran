package controller;

import intercepter.PrepareModel;

import java.util.List;

import model.BaseModel;
import model.orm.User;
import validator.LoginValidator;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;

@Before({LoginValidator.class, PrepareModel.class })
public class BaseController<M extends BaseModel<M>> extends Controller{

    public M model;

    @Before(Tx.class)
    public void create(){
        renderJson( model.create() );
    }

    public void list(){
        renderJson( model.toJsonList( model.all() ) );
    }

    @Before(Tx.class)
    public void updates(){
        renderJson( model.updates() );
    }

    @Before(Tx.class)
    public void destroy(){
        renderJson( model.destroy() );
    }

    public void refresh(){
    	renderJson( model.refresh().toJsonObject() ); 
    }
    
    public void renderList( List<M> list ){
    	renderJson( model.toJsonList(list) );
    }
    
    @Override
    public void renderJson(Object obj) {
        if( obj == null ) this.renderJson();
        else super.renderJson(obj);
    }

    public void renderJson(M model){
        renderJson( model.toJsonObject() );
    }

    public User user(){
    	return (User) getSessionAttr("user");
    }

}
