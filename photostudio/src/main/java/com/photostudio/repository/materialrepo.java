package com.photostudio.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.photostudio.models.employee;
import com.photostudio.models.material;
import com.photostudio.models.material_order;
import com.photostudio.models.material_supplier;
import com.photostudio.models.orders;
import com.photostudio.models.studio_photograph;
import com.photostudio.models.supplier;

@Transactional
@Repository
public class materialrepo {

	@Autowired
	JdbcTemplate jt;
	
	public List<material> getmaterial(){
		
		String sql = "select * from material";
		return jt.query( sql, new BeanPropertyRowMapper(material.class));
	}
	public List<material> getmatorder(){
		
		String sql = "select * from material_order order by morder_id desc";
		return jt.query( sql, new BeanPropertyRowMapper(material_order.class));
	}
	
	public List<material> getmaterialbyid(@Param("material_id") int material_id){
		
		
		return jt.query("select * from material where material_id ="+material_id, new BeanPropertyRowMapper(material.class));
		
	}
	
	public void savematerial(material mat){
		
		
		String sql = "insert into material(material_name,description,unitprice,available_quantity) values (?,?,?,?)";
		jt.update(sql,mat.getMaterial_name(),mat.getDescription(),mat.getUnitprice(),mat.getAvailable_quantity());
		
	}
	
	public List<material_supplier> getmaterialsupp(@Param("material_id") int material_id){
		
		
		return jt.query("select * from material_supplier where material_id ="+material_id, new BeanPropertyRowMapper(material_supplier.class));
		
	}
	
	public void savematerialorder(material_order mo){
		
		
		String sql = "insert into material_order(material_id,supplier_id,employee_id,quantity,price,odate,otime) values (?,?,?,?,?,?,?)";
		jt.update(sql,mo.getMaterial_id(),mo.getSupplier_id(),mo.getEmployee_id(),mo.getQuantity(),mo.getPrice(),mo.getOdate(),mo.getOtime());
		
	}
	public void editmaterial(material mat){
	
	
	String sql = "update material set description=?,unitprice=?,available_quantity=? where material_id=?";
	jt.update(sql,mat.getDescription(),mat.getUnitprice(),mat.getAvailable_quantity(),mat.getMaterial_id());
	
	}
	
	public void savematerialsupplier(material_supplier ms){
		
		
		String sql = "insert into material_supplier(material_id,supplier_id,price) values (?,?,?)";
		jt.update(sql,ms.getMaterial_id(),ms.getSupplier_id(),ms.getPrice());
		
	}
	public void delmatord(@Param("morder_id") int morder_id){
		
		
		String sql = "delete from material_order where morder_id="+morder_id;
		jt.update(sql);
		
	}

	public List<material> search(com.photostudio.controllers.materialontroller.search srch){
		int i = 0;
	    try {
	    	i=Integer.parseInt(srch.getStr()); }
	    catch(NumberFormatException nfe){
	    }
		String sql = "select * from material where (material_name like '%"+srch.getStr()+"%') or (description like '%"+srch.getStr()+ "%') or (material_id="+i+")";
		//String sql = "select * from customer where (fname like '%?%') or (lname like '%?%')";
		//return jt.query(sql, srch.getStr(),srch.getStr(), new BeanPropertyRowMapper(customer.class));
		return jt.query( sql, new BeanPropertyRowMapper(material.class));
		
	}

}
