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

import com.photostudio.models.employee;
import com.photostudio.models.employee_contact;
import com.photostudio.models.employee_email;
import com.photostudio.models.supplier;
import com.photostudio.models.supplier_contact;
import com.photostudio.models.supplier_email;

@Transactional
@Repository
public class supplierrepo {
	
	@Autowired
	JdbcTemplate jt;

	public List<supplier> getallsupplier(){
		
		String sql = "select * from supplier";
		return jt.query( sql, new BeanPropertyRowMapper(supplier.class));
	}
	
	public List<supplier_contact> getSuppliercontact(@Param("supplier_id") int supplier_id){


		//String sql = "select * from customer_contact where customer_id = ? ";
		return jt.query("select * from supplier_contact where supplier_id ="+supplier_id, new BeanPropertyRowMapper(supplier_contact.class));
		
	}
	
	public List<supplier_email> getSuppliermail(@Param("supplier_id") int supplier_id){


		//String sql = "select * from customer_email where customer_id = ? ";
		return jt.query("select * from supplier_email where supplier_id ="+supplier_id, new BeanPropertyRowMapper(supplier_email.class));
		
	}
	
	public int savesupplier(supplier supp){
		
		String sql = "insert into supplier (fname,lname,landmark,wardname,city) values (?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//jt.update(sql, cust.getFname(),cust.getLname(),cust.getLandmark(),cust.getWardname(),cust.getCity());
		PreparedStatementCreator psc = new PreparedStatementCreator() {
				
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, supp.getFname());
				ps.setString(2, supp.getLname());
				ps.setString(3, supp.getLandmark());
				ps.setString(4, supp.getWardname());
				ps.setString(5, supp.getCity());
				
				return ps;
			}
		};
		jt.update(psc, keyHolder);
		return keyHolder.getKey().intValue();
		
	}

	public void addSuppliercontact(supplier_contact conc1){
	
	
		String sql = "insert into supplier_contact(contact_no,supplier_id) values (?,?)";
		jt.update(sql,conc1.getContact_no(),conc1.getSupplier_id());
		
	}
	
	
	public void addSuppliermail(supplier_email mail1){
		
		
		String sql = "insert into supplier_email(email,supplier_id) values (?,?)";
		jt.update(sql,mail1.getEmail(),mail1.getSupplier_id());
		
	}
	
public supplier getsuppbyid(@Param("supplier_id") int supplier_id){
		
		String sql = "select * from supplier where supplier_id="+ supplier_id;
		 return jt.queryForObject(sql, new BeanPropertyRowMapper<supplier>(supplier.class));
		
	}
	
	public void editsupplier(supplier supp){
		
		
		String sql = "update supplier set landmark=?,wardname=?,city=? where supplier_id=?";
		jt.update(sql,supp.getLandmark(),supp.getWardname(),supp.getCity(),supp.getSupplier_id());
		
	}
	
	public void editsuppliercontact(supplier_contact conc1){
		
		
		String sql = "update supplier_contact  set contact_no=? where supplier_id = ?";
		jt.update(sql,conc1.getContact_no(),conc1.getSupplier_id());
		
	}
	
	
	public void editsuppliermail(supplier_email mail1){
		
		
		String sql = "update supplier_email set email=? where supplier_id=?";
		jt.update(sql,mail1.getEmail(),mail1.getSupplier_id());
		
	}
	public List<supplier> search(com.photostudio.controllers.suppliercontroller.search srch){
		int i = 0;
	    try {
	    	i=Integer.parseInt(srch.getStr()); }
	    catch(NumberFormatException nfe){
	    }
		String sql = "select * from supplier where (fname like '%"+srch.getStr()+"%') or (lname like '%"+srch.getStr()+ "%') or (supplier_id="+i+")";
		//String sql = "select * from customer where (fname like '%?%') or (lname like '%?%')";
		//return jt.query(sql, srch.getStr(),srch.getStr(), new BeanPropertyRowMapper(customer.class));
		return jt.query( sql, new BeanPropertyRowMapper(supplier.class));
		
	}
	
}
