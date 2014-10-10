package controller;

import model.orm.User;

import com.jfinal.core.Controller;

public class AppController extends Controller {

    public void renderApp( String appName ){
        setAttr("title", appName);
        setAttr("path",  appName + ".jsp" );
        render("/APP/views/layout/app.jsp");
    }

    public void index(){
        User user = getSessionAttr("user");
        if( user == null){
            renderApp("user/login");
        }else{
        	String app = user.get_role();
            renderApp(app + "/"+ app);
        }
    }
}
