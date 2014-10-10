package intercepter;

import java.lang.reflect.ParameterizedType;

import com.jfinal.core.Controller;

public class PrepareModel extends BaseIntercepter {

    public void before( Controller c ) {
        ParameterizedType pt = (ParameterizedType)(c.getClass().getGenericSuperclass());
        Class<?> modelClass = (Class<?>)pt.getActualTypeArguments()[0];
        Object model = c.getModel( modelClass );
        if ( model == null ) {
        	try{
        		model = modelClass.newInstance();
        	}catch( Exception e){
        		e.printStackTrace();
        	}
        }
        try {
            c.getClass().getField("model").set(c, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
