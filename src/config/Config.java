package config;

import model.orm.Inventory;
import model.orm.Product;
import model.orm.Shop;
import model.orm.Transaction;
import model.orm.TransactionProduct;
import model.orm.User;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import controller.AppController;
import controller.InventoryController;
import controller.LoginController;
import controller.ProductController;
import controller.ShopController;
import controller.TransactionController;
import controller.UserController;

public class Config extends JFinalConfig {

    public void configConstant(Constants me) {
        me.setDevMode(Path.DEV_MODE);
        me.setViewType(ViewType.JSP);
    }

    public void configPlugin(Plugins me) {
    	C3p0Plugin cp = new C3p0Plugin(
			"jdbc:oracle:thin:@172.26.142.249:1521:nekyoiku",
            "stms140809",
            "stms140809"
        ).setDriverClass("oracle.jdbc.driver.OracleDriver");
        me.add(cp);

       ActiveRecordPlugin arp = new ActiveRecordPlugin(cp)
           .setDialect(new OracleDialect())
           .setContainerFactory(new CaseInsensitiveContainerFactory());
       mapping(arp);
       me.add(arp);
    }

    public void configInterceptor(Interceptors me) {}
    public void configHandler(Handlers me) {}

    public void configRoute(Routes me) {
        me.add("/", AppController.class);
        me.add("/login", LoginController.class);
        me.add("/user", UserController.class);
        me.add("/product", ProductController.class);
        me.add("/transaction", TransactionController.class);
        me.add("/shop", ShopController.class);
        me.add("/inventory", InventoryController.class);
    }

    private static void mapping( ActiveRecordPlugin arp ){
        arp.addMapping("fe_user", User.class);
        arp.addMapping("fe_product", Product.class);
        arp.addMapping("fe_transaction", Transaction.class);
        arp.addMapping("fe_transactionProduct", TransactionProduct.class);
        arp.addMapping("fe_shop", Shop.class);
        arp.addMapping("fe_inventory", Inventory.class);
    }
}
