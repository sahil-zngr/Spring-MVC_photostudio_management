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
import com.photostudio.models.employee;
import com.photostudio.models.employee_contact;
import com.photostudio.models.employee_email;

@Transactional
@Repository
public class employeerepo {
	
	@Autowired
	JdbcTemplate jt;

	public List<employee> getallemployee(){
		
		String sql = "select * from employee";
		return jt.query( sql, new BeanPropertyRowMapper(employee.class));
	}
	
	public List<employee_contact> getemployeecontact(@Param("employee_id") int employee_id){


		//String sql = "select * from customer_contact where customer_id = ? ";
		return jt.query("select * from employee_contact where employee_id ="+employee_id, new BeanPropertyRowMapper(employee_contact.class));
		
	}
	
	public List<employee_email> getemployeemail(@Param("employee_id") int employee_id){


		//String sql = "select * from customer_email where customer_id = ? ";
		return jt.query("select * from employee_email where employee_id ="+employee_id, new BeanPropertyRowMapper(employee_email.class));
		
	}
	
	public int saveemployee(employee emp){
		
		String sql = "insert into employee (fname,lname,landmark,wardname,city,dateofbirth) values (?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//jt.update(sql, cust.getFname(),cust.getLname(),cust.getLandmark(),cust.getWardname(),cust.getCity());
		PreparedStatementCreator psc = new PreparedStatementCreator() {
				
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, emp.getFname());
				ps.setString(2, emp.getLname());
				ps.setString(3, emp.getLandmark());
				ps.setString(4, emp.getWardname());
				ps.setString(5, emp.getCity());
				ps.setDate(6, emp.getDateofbirth());
				
				
				return ps;
			}
		};
		jt.update(psc, keyHolder);
		return keyHolder.getKey().intValue();
		
	}

	public void addemployeecontact(employee_contact conc1){
	
	
		String sql = "insert into employee_contact(contact_no,employee_id) values (?,?)";
		jt.update(sql,conc1.getContact_no(),conc1.getEmployee_id());
		
	}
	
	
	public void addemployeemail(employee_email mail1){
		
		
		String sql = "insert into employee_email(email,employee_id) values (?,?)";
		jt.update(sql,mail1.getEmail(),mail1.getEmployee_id());
		
	}
	
public employee getempbyid(@Param("employee_id") int employee_id){
		
		String sql = "select * from employee where employee_id="+ employee_id;
		 return jt.queryForObject(sql, new BeanPropertyRowMapper<employee>(employee.class));
		
	}
	
	public void editemployee(employee emp){
		
		
		String sql = "update employee set landmark=?,wardname=?,city=?,dateofbirth=? where employee_id=?";
		jt.update(sql,emp.getLandmark(),emp.getWardname(),emp.getCity(),emp.getDateofbirth(),emp.getEmployee_id());
		
	}
	
	public void editemployeecontact(employee_contact conc1){
		
		
		String sql = "update employee_contact  set contact_no=? where employee_id = ?";
		jt.update(sql,conc1.getContact_no(),conc1.getEmployee_id());
		
	}
	
	
	public void editemployeemail(employee_email mail1){
		
		
		String sql = "update employee_email set email=? where employee_id=?";
		jt.update(sql,mail1.getEmail(),mail1.getEmployee_id());
		
	}
	
	public List<employee> search(com.photostudio.controllers.employeecontroller.search srch){
		int i = 0;
	    try {
	    	i=Integer.parseInt(srch.getStr()); }
	    catch(NumberFormatException nfe){
	    }
		String sql = "select * from employee where (fname like '%"+srch.getStr()+"%') or (lname like '%"+srch.getStr()+ "%') or (employee_id="+i+")";
		//String sql = "select * from customer where (fname like '%?%') or (lname like '%?%')";
		//return jt.query(sql, srch.getStr(),srch.getStr(), new BeanPropertyRowMapper(customer.class));
		return jt.query( sql, new BeanPropertyRowMapper(employee.class));
		
	}
	
	
}
