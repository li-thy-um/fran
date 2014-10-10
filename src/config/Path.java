package config;

public class Path {

    public static final boolean DEV_MODE = true;
    private static final String DEV = "/APP";
    private static final String PRO = "/fran/APP";

    public static final String WEB_CONTENT = DEV_MODE ? DEV : PRO;
    public static final String ASSETS = WEB_CONTENT + "/assets";
    public static final String IMG = ASSETS + "/img";
    public static final String JS = ASSETS + "/js";
    public static final String CSS = ASSETS + "/css";

}
