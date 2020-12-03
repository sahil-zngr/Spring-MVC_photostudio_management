package com.photostudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photostudio.controllers.customercontroller.search;
import com.photostudio.models.customer;
import com.photostudio.models.employee;
import com.photostudio.models.employee_contact;
import com.photostudio.models.employee_email;
import com.photostudio.repository.employeerepo;

@Controller
public class employeecontroller {

	@Autowired
	employeerepo repo;
	
	public class search{
		private String str;

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}
		
	}
	
	@RequestMapping("/admin/employee")
	public ModelAndView getAllemployee() {
		List<employee> empList = repo.getallemployee();
        ModelAndView mv = new ModelAndView("admin/library/allemployee");
        mv.addObject("emp", empList);
        mv.addObject("search", new search());
        return mv;
	}
	@GetMapping("/admin/employee/contact/{employee_id}")
	public ModelAndView getemployeecontact(@PathVariable("employee_id") int employee_id) {
		List<employee_contact> contactList = repo.getemployeecontact(employee_id);
		List<employee_email> mailList = repo.getemployeemail(employee_id);
        ModelAndView mv = new ModelAndView("admin/library/empcontact");
        mv.addObject("contact", contactList);
        mv.addObject("mail", mailList);
        return mv;
	}
	
	
	@GetMapping("/admin/addemployee")
	public ModelAndView getaddcustomerRecord() {
		ModelAndView mv = new ModelAndView("/admin/library/addemployee");
		mv.addObject("emp", new employee());
		mv.addObject("conc1",new employee_contact());
		mv.addObject("mail1",new employee_email());
		return mv;
		
	}
	@PostMapping("/admin/addemployee")
	public ModelAndView addcustomerRecord(@ModelAttribute("emp") employee emp,@ModelAttribute("conc1") employee_contact conc1,
			 @ModelAttribute("mail1") employee_email mail1) {
		ModelAndView mv = new ModelAndView("redirect:/admin/employee");
		int empid = repo.saveemployee(emp);
		conc1.setEmployee_id(empid);
		mail1.setEmployee_id(empid);
		repo.addemployeecontact(conc1);;
		repo.addemployeemail(mail1);;
		mv.addObject("emp", new employee());
		return mv;
		
	}
	
	@GetMapping("admin/editemployee/{employee_id}")
	public ModelAndView findemprbyid(@PathVariable("employee_id") int employee_id) {
		
		ModelAndView mv = new ModelAndView("admin/library/addemployee");
		employee emp = repo.getempbyid(employee_id);
		mv.addObject("emp", emp);
		mv.addObject("conc1", repo.getemployeecontact(employee_id).get(0));
		mv.addObject("mail1", repo.getemployeemail(employee_id).get(0));
		
		return mv;
	}

	@PostMapping("admin/editemployee/{employee_id}")
    public ModelAndView updateemp(@ModelAttribute("emp") employee emp,@ModelAttribute("conc1") employee_contact conc1,
			 @ModelAttribute("mail1") employee_email mail1) {
        ModelAndView mv = new ModelAndView("redirect:/admin/employee");
        repo.editemployee(emp);
        repo.editemployeecontact(conc1);
        repo.editemployeemail(mail1);
        return mv;
    }
	
	@PostMapping("admin/employee/search")
    public ModelAndView search(@ModelAttribute("search") search srch ) {
		List<employee> empList = repo.search(srch);
        ModelAndView mv = new ModelAndView("admin/library/allemployee");
        mv.addObject("emp", empList);
        mv.addObject("search", srch);
        return mv;
    }
	
}
