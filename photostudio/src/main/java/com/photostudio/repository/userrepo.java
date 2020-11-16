package com.photostudio.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.photostudio.models.Userinfo;
import com.photostudio.models.material_supplier;

@Transactional
@Repository
public class userrepo {
	
	@Autowired
	JdbcTemplate jt;

	public boolean isEmployeeWithUsernameExists(String username){
        String sql = "select count(*) from userinfo where username=?";
        int count = jt.queryForObject(sql, new Object[]{username},Integer.class);
        return (count>0)?true:false;
    }
	
	public void saveuser(Userinfo user){
		
		
		String sql = "insert into userinfo(username,password,employee_id,role) values (?,?,?,?)";
		jt.update(sql,user.getUsername(),user.getPassword(),user.getEmployee_id(),user.getRole());
		
	}
	
	public int userempid(@Param("username") String username){
		
		int employee_id = (int)jt.queryForObject("select employee_id from userinfo where username=?", int.class, username);
		return employee_id;
	}
	
}
