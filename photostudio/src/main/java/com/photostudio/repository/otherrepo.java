package com.photostudio.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.photostudio.models.material_sold;
import com.photostudio.models.outdoororder;
import com.photostudio.models.passwordtoken;
import com.photostudio.models.photo_print;
import com.photostudio.models.sendemail;
import com.photostudio.models.service;
import com.photostudio.models.studio_photograph;
import com.photostudio.models.supplier_email;

@Repository
@Transactional
public class otherrepo {
	
	@Autowired
	JdbcTemplate jt;

	public List<photo_print> getallprint(){
		
		String sql = "select * from photo_print order by print_id desc ";
		return jt.query( sql, new BeanPropertyRowMapper(photo_print.class));
	}
	
	public List<studio_photograph> getallphoto(){
		
		String sql = "select * from studio_photograph order by photo_id desc ";
		return jt.query( sql, new BeanPropertyRowMapper(studio_photograph.class));
	}
	
	public List<outdoororder> getalloutdoor(){
		
		String sql = "select * from outdoororder order by outdoororder_id desc ";
		return jt.query( sql, new BeanPropertyRowMapper(outdoororder.class));
	}
	
	public List<material_sold> getallmaterialsold(){
		
		String sql = "select * from material_sold order by materialsold_id desc ";
		return jt.query( sql, new BeanPropertyRowMapper(material_sold.class));
	}
	
	public List<service> getallservice(){
		
		String sql = "select * from service order by service_id desc ";
		return jt.query( sql, new BeanPropertyRowMapper(service.class));
	}

	public void saveinvoice(com.photostudio.models.invoice inc){
		
		
		String sql = "insert into invoice(order_id,customer_id,pdate,ptime,status,amount) values (?,?,?,?,?,?)";
		jt.update(sql,inc.getOrder_id(),inc.getCustomer_id(),inc.getPdate(),inc.getPtime(),inc.getStatus(),inc.getAmount());
		
	}
	public void savesendmail(sendemail smail){
		
		
		String sql = "insert into sendemail(order_id,customer_email,sdate,stime,status) values (?,?,?,?,?)";
		jt.update(sql,smail.getOrder_id(),smail.getCustomer_email(),smail.getSdate(),smail.getStime(),smail.getStatus());
		
	}
	

	
}
