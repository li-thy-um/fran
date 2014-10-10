package validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LoginValidator extends Validator {

    @Override
    protected void validate(Controller c) {
        Object user = c.getSessionAttr("user");
        if( user == null ) {
            addError("error", "Sorry! You need to login before action.");
        }
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson("error", c.getAttr("error"));
    }
}
