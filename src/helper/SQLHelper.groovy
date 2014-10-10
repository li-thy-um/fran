package helper;

public class SQLHelper {

    private String tableName;

    public static final String TRUE = "1=1";
    public static final String IN = "in";
    public static final String BETWEEN = "between";
    public static final String LIKE = "like";
    public static final String HEAD = "fe_";


    public SQLHelper( Class<?> modelClass ){
        this.tableName = "${HEAD}${modelClass.getSimpleName().toLowerCase()}";
    }

    public String getSQL( String attr, String opr, int num ){
        getSQL ( getWhere(attr, opr, num) );
    }
    
    public String getWhere( String attr, String opr, int num ){
		return new StringBuffer(attr).append(" ")
                .append(opr).append(" ")
                .append(getValue(opr, num))
                .toString();
    }

    public String getSQL( String where ){
        return new StringBuffer("select * from ")
          .append(tableName).append(" ")
          .append("where ").append(where).append(" ")
          .append("and df<>'D' order by update_at desc")
          .toString();
    }

    private String getValue( String opr, int num ){
        String head, tail, seprator;
        if( IN.equals(opr) ){
            head = "(";
            tail = ")";
            seprator = ",";
        }else
        if( BETWEEN.equals(opr) ){
            head = "";
            tail = "";
            seprator = " and ";
        }else {
            return "?";
        }
        return getValue( head, tail, seprator, num );
    }

    private String getValue( String head, String tail, String seprator, int num ){
        StringBuffer sb = new StringBuffer(head).append("?");
        for( int i = 1; i < num; i ++ ){
            sb.append(seprator).append("?");
        }
        return sb.append(tail).toString();
    }

}