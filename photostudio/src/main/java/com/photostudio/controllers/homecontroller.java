package com.photostudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photostudio.models.Userinfo;
import com.photostudio.repository.userrepo;

@Controller
public class homecontroller {
	
	@Autowired
	userrepo userrepo;
	
	
	public class message{
		private String msg;

		
		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		
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
		@RequestMapping("/")
		public ModelAndView login() {
	        ModelAndView mv = new ModelAndView("redirect:/app");
	        return mv;
		}
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
	
}
