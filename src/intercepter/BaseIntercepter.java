package intercepter;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class BaseIntercepter implements Interceptor {

    @Override
    public void intercept(ActionInvocation ai) {
        before( ai.getController() );
        ai.invoke();
        after( ai.getController() );
    }

    public void before(Controller controller) {}
    public void after(Controller controller) {}
}
