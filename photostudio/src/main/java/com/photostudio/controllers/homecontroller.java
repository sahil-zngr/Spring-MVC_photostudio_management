package com.photostudio.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photostudio.mailservice;
import com.photostudio.models.Userinfo;
import com.photostudio.models.passwordtoken;
import com.photostudio.repository.employeerepo;
import com.photostudio.repository.userrepo;

@Controller
public class homecontroller {
	
	@Autowired
	userrepo userrepo;
	
	@Autowired
	employeerepo emprepo;
	
	@Autowired
	private mailservice notificationService;
	
	
	public class message{
		private String msg;

		
		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		
	}
	
	@RequestMapping("/")
	public ModelAndView first() {
        ModelAndView mv = new ModelAndView("app/firstpage");
        return mv;
	}

		@RequestMapping("/app")
		public ModelAndView userhome() {
	        ModelAndView mv = new ModelAndView("app/index");
	        return mv;
		}
		@RequestMapping("/admin")
		public ModelAndView adminhome() {
	        ModelAndView mv = new ModelAndView("admin/index");
	        return mv;
		}
//		@RequestMapping("/")
//		public ModelAndView login() {
//	        ModelAndView mv = new ModelAndView("redirect:/app");
//	        return mv;
//		}
		@RequestMapping("/accessDenied")
		public ModelAndView error() {
	        ModelAndView mv = new ModelAndView("app/error403");
	        return mv;
		}
		
		@GetMapping("/admin/register")
		public ModelAndView getIndexPage() {
			ModelAndView mv = new ModelAndView("/admin/userreg");
			mv.addObject("message",new message());
			mv.addObject("user", new Userinfo());
			return mv;
			
		}
		@PostMapping("/admin/register")
		public ModelAndView addcustomerRecord(@ModelAttribute("user") Userinfo user) {
			message message = new message();
			if(userrepo.isEmployeeWithUsernameExists(user.getUsername()))
	        {
				ModelAndView mv = new ModelAndView("/admin/userreg");
	            String msg = "!!!username already exists.!!!";
	            message.setMsg(msg);
	            mv.addObject("message",message);
	            return mv;
	        }
			ModelAndView mv = new ModelAndView("redirect:/admin");
	        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
	        user.setPassword(encoder.encode(user.getPassword()));
	        userrepo.saveuser(user);
	        
			return mv;
			
		}
		@GetMapping("/forgot")
		public ModelAndView forgotpass() {
			ModelAndView mv = new ModelAndView("/app/resetpassword");
			mv.addObject("username", new message());
			return mv;
			
		}
		@PostMapping("/forgot")
		public ModelAndView forgotpass2(@ModelAttribute("username") message username) {
			ModelAndView mv = new ModelAndView("redirect:/login");
			int empid = userrepo.userempid(username.getMsg());
			String email = emprepo.getemployeemail(empid).get(0).getEmail();
			String token = UUID.randomUUID().toString();
			String str = "To complete the password reset process, please click here: "
		              + "http://localhost:8080/forgot/reset/"+username.getMsg()+"/"+token;
			try {
			notificationService.sendEmail(email, str);
			}
			catch (MailException mailException) {
				System.out.println(mailException);
			}
			passwordtoken token1 = new passwordtoken();
			token1.setPasswordtokentoken(token);
			token1.setUsername(username.getMsg());
			userrepo.savetoken(token1);
			return mv;
			
		}
		@GetMapping("/forgot/reset/{username}/{token}")
		public ModelAndView forgotpass3(@PathVariable("username") String username, @PathVariable("token") String token) {
			String usertoken = userrepo.usertoken(username);
			//System.out.println(token);
			//System.out.println(usertoken);
			ModelAndView mv1 = new ModelAndView("app/passerror");
			if (usertoken.equals(token)) {
				//System.out.println("here");
				ModelAndView mv = new ModelAndView("/app/resetpassword2");
				mv.addObject("username", new message());
				return mv;
			}
			else {
				//System.out.println("heer");
				userrepo.deletetoken(username);
				return mv1;
			}
			
		}
		@PostMapping("/forgot/reset/{username1}/{token}")
		public ModelAndView forgotpass4(@PathVariable("username1") String username1, @PathVariable("token") String token,@ModelAttribute("username") message username) {
			BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
	        String pass = encoder.encode(username.getMsg());
	        ModelAndView mv = new ModelAndView("redirect:/login");
	        String usertoken = userrepo.usertoken(username1);
	        if(usertoken.equals(token)) {
				userrepo.restpass(username1, pass);
				userrepo.deletetoken(username.getMsg());
			}
	        else {
	        	ModelAndView mv1 = new ModelAndView("app/passerror");
	        	userrepo.deletetoken(username.getMsg());
	        	return mv1;
	        }
			return mv;
		}
	
}
