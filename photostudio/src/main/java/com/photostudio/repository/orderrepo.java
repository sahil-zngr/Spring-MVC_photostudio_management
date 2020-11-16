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

import com.photostudio.models.customer;
import com.photostudio.models.material;
import com.photostudio.models.material_sold;
import com.photostudio.models.orders;
import com.photostudio.models.outdoororder;
import com.photostudio.models.photo_print;
import com.photostudio.models.service;
import com.photostudio.models.studio_photograph;

@Transactional
@Repository
public class orderrepo {

	@Autowired
	JdbcTemplate jt;
	
	public List<orders> getorders(){
			
			String sql = "select * from orders order by order_id desc ";
			return jt.query( sql, new BeanPropertyRowMapper(orders.class));
		}
	public void updatetp(@Param("order_id") int order_id ,@Param("total_price")int total_price){
		
		
		String sql = "update orders set total_price = ? where order_id = ?";
		jt.update(sql,total_price,order_id);
		
	}
	
	public List<material> getmateriallist(){
		
		String sql = "select * from material";
		return jt.query( sql, new BeanPropertyRowMapper(material.class));
	}
	
	public List<orders> getcustorders(@Param("cust_id") int cust_id){
	
	return jt.query("select * from orders where customer_id ="+cust_id+" order by order_id desc", new BeanPropertyRowMapper(orders.class));
		
	}
	
	public List<studio_photograph> getStudiophotograaph(@Param("order_id") int order_id){


		return jt.query("select * from studio_photograph where order_id ="+order_id, new BeanPropertyRowMapper(studio_photograph.class));
		
	}
	public List<photo_print> getPhotoprint(@Param("order_id") int order_id){


		return jt.query("select * from photo_print where order_id ="+order_id, new BeanPropertyRowMapper(photo_print.class));
		
	}
	public List<outdoororder> getOutdoororder(@Param("order_id") int order_id){


		return jt.query("select * from outdoororder where order_id ="+order_id, new BeanPropertyRowMapper(outdoororder.class));
		
	}
	public List<service> getService(@Param("order_id") int order_id){


		return jt.query("select * from service where order_id ="+order_id, new BeanPropertyRowMapper(service.class));
		
	}
	public List<material_sold> getMaterialsold(@Param("order_id") int order_id){


		return jt.query("select * from material_sold where order_id ="+order_id, new BeanPropertyRowMapper(material_sold.class));
		
	}
	
	public int neworder(orders ord){
			
			String sql = "insert into orders (customer_id,employee_id,orderdate,ordertime,total_price) values (?, ?, ?, ?, ?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			//jt.update(sql, cust.getFname(),cust.getLname(),cust.getLandmark(),cust.getWardname(),cust.getCity());
			PreparedStatementCreator psc = new PreparedStatementCreator() {
					
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					
					PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, ord.getCustomer_id());
					ps.setInt(2, ord.getEmployee_id());
					ps.setDate(3,ord.getOrderdate());
					ps.setTime(4, ord.getOrdertime());
					ps.setInt(5, ord.getTotal_price());
					return ps;
				}
			};
			jt.update(psc, keyHolder);
			return keyHolder.getKey().intValue();
			
		}
	
	public void savephoto(studio_photograph photo){
		
		
		String sql = "insert into studio_photograph(order_id,photographer,size,price) values (?,?,?,?)";
		jt.update(sql,photo.getOrder_id(),photo.getPhotographer(),photo.getSize(),photo.getPrice());
		
	}
	public void saveprint(photo_print print){
		
		
		String sql = "insert into photo_print(order_id,print_size,price) values (?,?,?)";
		jt.update(sql,print.getOrder_id(),print.getPrint_size(),print.getPrice());
		
	}
	
	public void saveoutdoor(outdoororder outdoor){
		
		
		String sql = "insert into outdoororder(order_id,sdate,stime,edate,etime,ordertype,service_req,price) values (?,?,?,?,?,?,?,?)";
		jt.update(sql,outdoor.getOrder_id(),outdoor.getSdate(),outdoor.getStime(),outdoor.getEdate(),outdoor.getEtime(),outdoor.getOrdertype(),outdoor.getService_req(),outdoor.getPrice());
		
	}
	
	public void saveservice(service service){
		
		
		String sql = "insert into service(order_id,servicedate,servicetime,price) values (?,?,?,?)";
		jt.update(sql,service.getOrder_id(),service.getServicedate(),service.getServicetime(),service.getPrice());
		
	}
	
	public void savematerial(material_sold material){
		
		
		String sql = "insert into material_sold(material_id,order_id,name,price) values (?,?,?,?)";
		jt.update(sql,material.getMaterial_id(),material.getOrder_id(),material.getName(),material.getPrice());
		
	}
	
	public void descmaterialqut(int material_id){
		
		String sql = "update material set available_quantity = available_quantity-1 where material_id=?";
		jt.update(sql,material_id);
		
	}
	
	public List<orders> search(com.photostudio.controllers.ordercontroller.search srch){
		int i = 0;
	    try {
	    	i=Integer.parseInt(srch.getStr()); }
	    catch(NumberFormatException nfe){
	    }
		String sql = "select * from orders where ( order_id ="+i+") or (employee_id=" +i+ ") or (customer_id="+i+") or (orderdate >= '"+srch.getStr()+"')";
		//String sql = "select * from customer where (fname like '%?%') or (lname like '%?%')";
		//return jt.query(sql, srch.getStr(),srch.getStr(), new BeanPropertyRowMapper(customer.class));
		return jt.query( sql, new BeanPropertyRowMapper(orders.class));
		
	}
	
	public List<orders> getorderbyorderid(int order_id){
		
		String sql = "select * from orders where order_id ="+order_id;
		return jt.query( sql, new BeanPropertyRowMapper(orders.class));
	}
	
}
