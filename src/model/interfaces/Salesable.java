package model.interfaces;

import helper.Interval;

import java.math.BigDecimal;
import java.util.List;

import model.orm.User;

public interface Salesable {

	public BigDecimal sales( Interval interval );
		
	public BigDecimal increase( Interval last, Interval thiz );

	public List<Salesable> listForUser( User user );
}
