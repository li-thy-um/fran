package controller;

import java.io.IOException;

import model.orm.User;

import com.jfinal.core.Controller;

public class LoginController extends Controller{

    public void create() {
        User user = getModel(User.class).login();
        if( user == null ){
            renderJson("error", "Invalid E-mail & Password Pair." );
        }else if( user.isForbidden() ){
        	renderJson("error", "Sorry, this account is forbidden now." );
        }else{
        	setSessionAttr("user", user);
        	renderJson( user.toJsonObject() );
        }
    }

    public void destroy() throws IOException{
        getSession().removeAttribute("user");
        renderText( "success" );
    }
}
