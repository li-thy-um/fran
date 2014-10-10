package model.orm;

import helper.DateHelper
import helper.EncodeHelper
import helper.Interval
import helper.MathHelper
import helper.RoleHelper
import helper.DateHelper.DateType
import model.BaseModel
import model.interfaces.Chartable
import model.interfaces.Salesable

public class User extends BaseModel<User> implements Salesable, Chartable {

    /* Fields */

    String _name;
    String _psw;
    String _role;
    String _email;

	String _shop_id; // only cashier have shop_id.
    String _boss_id;
    
    public static User dao = new User();
	
	private static final SALES_TYPE = [
			"week_sales","month_sales","year_sales",
			"last_week_sales", "last_month_sales", "last_year_sales",
			"week_increase","month_increase","year_increase",
			
		];
	
	public String checkDestroy(){
		this.is("keeper") && this.shops().size() > 0 ? 
			"The keeper has already been assigned to at least one shop, unassign him first." : null;
	}
	
	@Override
	public User destroy() {
		if( this.is("keeper") ){
			this.subUser().each { cashier -> cashier.destroy() }
		}
		if( this.is("manager") ){
			this.subUser().each { keeper -> keeper.destroy() }
		}  
		return super.destroy();
	}
	
	@Override
	public Map<String, Object> toJsonObject( ) {
		Map<String, Object> json =  super.toJsonObject()
		def s = shop()
		if( s != null ) json.put "user.shop_id", s._name
		json
	}
	
    /* Service */
    
    public String display(){
		"${_name}(${_email})"
    }
	
	public List<Shop> avaliableShop(){
		Shop.dao.findListBy("keeper_id", _id)
	}
    
    public List<User> avaliableKeeper(){
    	findListBy "boss_id", _id
    }
    
    public User login( ) {
        User user = findBy("email", _email);
		user && user._psw == encode("psw") ? user : null		
    }

    @Override
    public boolean save() {
        encode("psw");
        return super.save();
    }
    
    public User initSubUser( User subUser ){
    	subUser.set("role", RoleHelper.subRole(_role))
    		   .set("psw", EncodeHelper.randomPsw())
    		   .set("boss_id", _id);
    }
	
	public List<User> keepers() {
		if( is("manager") ){
			return dao.findListBy("boss_id", _id)
		}
		if( is("cashier") ){
			return [ boss() ]
		}
		if( is("keeper") ){
			return [ this ]
		}
		[]
	} 
    
	boolean isForbidden(){
		( is("cashier") && _shop_id == null ) ? true : false
	}
	
    private boolean is( String role ) {
		role == _role
	}

	public Shop shop(){
		Shop.dao.findById( _shop_id )
	}
	
    public List<Shop> shops(){
    	Shop.dao.findListBy("keeper_id", keepers().toArray())
    }
    
	public User boss(){
    	findById _boss_id
    }
	
	public User manager(){
		is("manager") ? this : 
		is("keeper") ? this.boss() : 
		is("cashier") ? this.boss().boss(): 
		null
	}
	
	public List<User> subUser() {
		dao.findListBy( "boss_id", _id )
	}
    
	public <M extends BaseModel<M>> M totalSalesOf( List<M> sales, M model ){		
		SALES_TYPE.eachWithIndex { type, i ->
			if( i < 6){
				 model.put(type, sales.inject(0){ total, sale -> total + sale.get(type)} )
			}else{
				model.put(type, MathHelper.increase( model.get(SALES_TYPE[i-3]), model.get(SALES_TYPE[i-6])))
			}
		}
		model
	}
		
	/**
     * 
     * @param dao Must be a Salesable.
     * @return
     */
	public <M extends BaseModel<M>> List<M> sales( M dao ) {
		
		if( ! (dao instanceof Salesable) ) return null;		
		
		((Salesable)dao).listForUser( this ).collect { s ->
			M m = (M) s;
			
			SALES_TYPE.eachWithIndex { sype, i ->
				BigDecimal sales = BigDecimal.ZERO;
				
				if( i < 6){
					
					sales = s.sales( DateHelper.intervalOf(DateType.values()[i]) );
				
				}else{
				
					DateType last = DateType.values()[i-3];
					DateType thiz = DateType.values()[i-6];
					sales = s.increase( DateHelper.intervalOf(last), DateHelper.intervalOf(thiz) );
				
				}
				
				m.put( sype, sales );
			}
			
			m
		}

	}
	
	public List<Product> sales( Product product, Shop shop ) {
		if( shop == null ) return sales( product );
		else {
			product.listForManager( this.manager() ).collect { p ->
				
				SALES_TYPE.eachWithIndex { sype, i ->
					BigDecimal sales = BigDecimal.ZERO;
					
					if( i < 6 ){
						
						sales = p.sales( DateHelper.intervalOf(DateType.values()[i]), shop );
					
					}else{
						
						DateType last = DateType.values()[i-3];
						DateType thiz = DateType.values()[i-6];
						sales = p.increase( DateHelper.intervalOf(last), DateHelper.intervalOf(thiz), shop );
					
					}
					
					p.put( sype, sales );
				}
				
				p
			}
		}
	}

	@Override
	public BigDecimal sales(Interval inter) {
		Transaction.of(this, inter).inject(0){ sales, tran ->
			sales += tran.total()
		}
	}

	@Override
	public BigDecimal increase(Interval last, Interval thiz) {
		MathHelper.increase( sales( last ), sales( thiz ) )
	}

	@Override
	public Map<String, Object> chart(int year, int week) {
		List<Interval> intervals = Interval.getIntervals( year, week );
		
		[ "labels" : Interval.labels( intervals ),
		  "sales"  : salesList( intervals ),
		  "u"      : this.toJsonObject()
		]
	}
	
	private List<BigDecimal> salesList(List<Interval> intervals) {
		intervals.collect{ inter -> sales( inter ) }
	}

	@Override
	public List<Salesable> listForUser(User user) {
		user.subUser()
	}
}