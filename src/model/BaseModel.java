package model;

import helper.DateHelper;
import helper.EncodeHelper;
import helper.FieldHelper;
import helper.SQLHelper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import oracle.sql.TIMESTAMP;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class BaseModel<M extends BaseModel<M>> extends Model<M> {

    private static final String DELETE = "D";
    private static final String UPDATE = "U";
    private static final String CREATE = "N";

    private SQLHelper helper = new SQLHelper( this.getClass() );

    /* Fields Define should start with "_" */

    private String _id;
	private Timestamp _update_at;
    private Timestamp _create_at;
    private String _df;
    
    public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Timestamp get_update_at() {
		return _update_at;
	}

	public void set_update_at(Timestamp _update_at) {
		this._update_at = _update_at;
	}

	public Timestamp get_create_at() {
		return _create_at;
	}

	public void set_create_at(Timestamp _create_at) {
		this._create_at = _create_at;
	}

	public String get_df() {
		return _df;
	}

	public void set_df(String _df) {
		this._df = _df;
	}

    public Map<String, Object> toJsonObject(){
        Map<String, Object> json = new HashMap<String, Object>();
        this.toJsonObject( json );
        return json;
    }

    public List<Map<String,Object>> toJsonList( List<M> list ){
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        for( M model : list ){
            result.add( model.toJsonObject() );
        }
        return result;
    }

    private void toJsonObject( Map<String,Object> json ){
        String class_name = this.getClass().getSimpleName().toLowerCase() + ".";
        for( String name : this.getAttrNames()){
            Object attr = this.get(name);
            name = name.toLowerCase();
            if( attr == null ){
                json.put( class_name + name, null );
                continue;
            }
            if( attr instanceof String ){
                json.put( class_name + name, attr );
                continue;
            }
            if( attr instanceof BigDecimal ){
                json.put( class_name + name, attr );
                continue;
            }
            if( attr instanceof TIMESTAMP ){
                try {
                    json.put( class_name + name, ((TIMESTAMP) attr).dateValue().toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if( attr instanceof BaseModel<?> ){
                ((BaseModel<?>) attr).toJsonObject(json);
                continue;
            }
        }
    }

    /* Set Fields While Set Attributes */

    @Override
    public M put(String key, Object value) {
        key = key.toLowerCase();
        setField(key, value);
        return super.put(key, value);
    };

    @Override
    public M set(String attr, Object value) {
        setField(attr, value);
        return super.set(attr, value);
    }

    @Override
    public M setAttrs(Map<String, Object> attrs) {
        for( Entry<String,Object> e : attrs.entrySet() ){
            setField(e.getKey(), e.getValue());
        }
        return super.setAttrs(attrs);
    }

    private void setField(String attr, Object value) {
        FieldHelper.setBeanField(BaseModel.class, this, "_"+attr, value);
    }

    /* Validation */

    public boolean unique( String name ){
        return this.findBy(name, this.get(name)) == null;
    }

    /* BASIC CRUD */

    public M create() {
        M model = set("id", UUID.randomUUID().toString())
            .setFlag(CREATE);
        model = model.save() ? model : null;
        return model;
    }

    public M destroy() {
        if ( isDeleted() ) return null;
        M deleted = setFlag(DELETE).update() ? findById( _id ) : null;
        return deleted;
    }

    public M updates() {
        M oldOne = findById( _id );
        if ( oldOne.isDeleted() ) return null;
        M newOne = setFlag(UPDATE).update() ? findById( _id ) : null;
        return newOne;
    }
    
    @SuppressWarnings("unchecked")
	public M refresh(){
    	if( _id == null ) return (M)this;
    	return findById(_id);
    }

    /* SEARCH */

    public List<M> all() {
        return find( helper.getSQL(SQLHelper.TRUE) );
    }

    /**
     * @return The first model which attribute IN values.
     */
    public M findBy( String attr, Object... value ){
        List<M> rslt = findListBy(attr, value);
        if( rslt.size() > 0 ){
            return rslt.get(0);
        }
        return null;
    }

    /**
     * @return A list of models which value match attribute value.
     */
    public List<M> queryListBy( String attr, Object value ){
        return find(
            helper.getSQL(attr, SQLHelper.LIKE, 1)
            , value
        );
    }

    /**
     * @return A list of models which attribute IN values.
     */
    public List<M> findListBy( String attr, Object... values ){
    	List<M> list = check_and_reform( values );
    	if( list != null ) return list;
    	return find(
            helper.getSQL(attr, SQLHelper.IN, values.length)
            , values
        );
    }

    /**
     * If value instanceof BaseModel, then replace it with its id.
     * To support IN search of BaseModel.
     * @param values 
     * @return if values.length == 0 EmptyList
     * 		   else null.
     */
    private List<M> check_and_reform(Object[] values) {
    	if( values.length == 0 ) return new ArrayList<M>();
    	if( values[0] instanceof BaseModel ){
            for( int i = 0; i < values.length; i++){
            	values[i] = ((BaseModel<?>)values[i]).getStr("id");
            }
        }
    	return null;
	}

	/**
     * @return A list of models which attribute BETWEEN from AND to.
     */
    public List<M> findListBetween( String attr, Object from, Object to ){
        return find(
          helper.getSQL(attr, SQLHelper.BETWEEN, 2)
          , from
          , to
          );
    }
    
    /**
     * @return A list of models which attr1 BETWEEN from AND to and attr2 in values.
     */
    public List<M> listInBetween( String attr1, Object from, Object to, String attr2, Object... values ){
    	List<M> list = check_and_reform( values );
    	if( list != null ) return list; 
		
    	List<Object> params = new ArrayList<Object>();
    	params.add(from);
    	params.add(to);
    	params.addAll( Arrays.asList(values) );
    	
    	String where = new StringBuilder("( ")
    		.append(helper.getWhere(attr1, SQLHelper.BETWEEN, 2))
    		.append(" ) and ")
			.append( helper.getWhere(attr2, SQLHelper.IN, values.length) ).toString();
		
		return find( helper.getSQL( where ), params.toArray() );
	}

    protected String encode( String attr ){
        Object value = this.get(attr);
        if( value instanceof String ){
            String code = EncodeHelper.encrypt(value.toString());
            this.set(attr, code);
            return code;
        }
        else return "";
    }

    private boolean isDeleted() {
        return "D".equals( this.getStr("df") );
    }

    private M setFlag( String df ){
        Timestamp now = DateHelper.sysTime();
        if( CREATE.equals(df) )
            this.set("create_at", now);
        return this.set("update_at", now)
                   .set("df", df);
    }
}