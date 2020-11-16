package com.photostudio.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.photostudio.controllers.customercontroller.search;
import com.photostudio.models.customer;
import com.photostudio.models.customer_contact;
import com.photostudio.models.customer_email;

@Transactional
@Repository
public class customerrepo {
	
	@Autowired
	JdbcTemplate jt;

	public List<customer> getallCustomers(){
		
		String sql = "select * from customer order by customer_id desc ";
		return jt.query( sql, new BeanPropertyRowMapper(customer.class));
	}
	public customer getcustomerbyid(@Param("customer_id") int customer_id){
		
		String sql = "select * from customer where customer_id="+ customer_id;
		customer cust =  jt.queryForObject(sql, new BeanPropertyRowMapper<customer>(customer.class));
		return cust;
	}
	
	public List<customer_contact> getCustomercontact(@Param("customer_id") int customer_id){


		//String sql = "select * from customer_contact where customer_id = ? ";
		return jt.query("select * from customer_contact where customer_id ="+customer_id, new BeanPropertyRowMapper(customer_contact.class));
		
	}
	
	public List<customer_email> getCustomermail(@Param("customer_id") int customer_id){


		//String sql = "select * from customer_email where customer_id = ? ";
		return jt.query("select * from customer_email where customer_id ="+customer_id, new BeanPropertyRowMapper(customer_email.class));
		
	}
	
	public int savecustomer(customer cust){
		
		String sql = "insert into customer (fname,lname,landmark,wardname,city) values (?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//jt.update(sql, cust.getFname(),cust.getLname(),cust.getLandmark(),cust.getWardname(),cust.getCity());
		PreparedStatementCreator psc = new PreparedStatementCreator() {
				
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, cust.getFname());
				ps.setString(2, cust.getLname());
				ps.setString(3, cust.getLandmark());
				ps.setString(4, cust.getWardname());
				ps.setString(5, cust.getCity());
				
				return ps;
			}
		};
		jt.update(psc, keyHolder);
		return keyHolder.getKey().intValue();
		
	}

	public void addCustomercontact(customer_contact conc1){
	
	
		String sql = "insert into customer_contact(contact_no,customer_id) values (?,?)";
		jt.update(sql,conc1.getContact_no(),conc1.getCustomer_id());
		
	}
	
	
	public void addCustomermail(customer_email mail1){
		
		
		String sql = "insert into customer_email(email,customer_id) values (?,?)";
		jt.update(sql,mail1.getEmail(),mail1.getCustomer_id());
		
	}
	public void editCustomer(customer cust){
		
		
		String sql = "update customer set landmark=?,wardname=?,city=? where customer_id=?";
		jt.update(sql,cust.getLandmark(),cust.getWardname(),cust.getCity(),cust.getCustomer_id());
		
	}
	
	public void editCustomercontact(customer_contact conc1){
		
		
		String sql = "update customer_contact  set contact_no=? where customer_id = ?";
		jt.update(sql,conc1.getContact_no(),conc1.getCustomer_id());
		
	}
	
	
	public void editCustomermail(customer_email mail1){
		
		
		String sql = "update customer_email set email=? where customer_id=?";
		jt.update(sql,mail1.getEmail(),mail1.getCustomer_id());
		
	}
	
	public List<customer> searchcust(search srch){
		int i = 0;
	    try {
	    	i=Integer.parseInt(srch.getStr()); }
	    catch(NumberFormatException nfe){
	    }
		String sql = "select * from customer where (fname like '%"+srch.getStr()+"%') or (lname like '%"+srch.getStr()+ "%') or (customer_id="+i+")";
		//String sql = "select * from customer where (fname like '%?%') or (lname like '%?%')";
		//return jt.query(sql, srch.getStr(),srch.getStr(), new BeanPropertyRowMapper(customer.class));
		return jt.query( sql, new BeanPropertyRowMapper(customer.class));
		
	}

}

