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

import com.photostudio.models.customer;
import com.photostudio.models.customer_contact;
import com.photostudio.models.customer_email;
import com.photostudio.repository.customerrepo;

@Controller
public class customercontroller {

	@Autowired
	customerrepo repo;
	
	
	public class search{
		private String str;

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}
		
	}
	
	@RequestMapping("/app/customer")
	public ModelAndView getAllcustomer() {
		List<customer> custList = repo.getallCustomers();
        ModelAndView mv = new ModelAndView("app/library/allcustomer");
        mv.addObject("cust", custList);
        mv.addObject("search", new search());
        return mv;
	}
	@GetMapping("/app/customer/contact/{customer_id}")
	public ModelAndView getcustomercontact(@PathVariable("customer_id") int customer_id) {
		List<customer_contact> contactList = repo.getCustomercontact(customer_id);
		List<customer_email> mailList = repo.getCustomermail(customer_id);
        ModelAndView mv = new ModelAndView("app/library/contact");
        mv.addObject("contact", contactList);
        mv.addObject("mail", mailList);
        return mv;
	}
	
	
	@GetMapping("/app/addcustomer")
	public ModelAndView getIndexPage() {
		ModelAndView mv = new ModelAndView("/app/library/addcustomer");
		mv.addObject("cust", new customer());
		mv.addObject("conc1",new customer_contact());
		mv.addObject("mail1",new customer_email());
		return mv;
		
	}
	@PostMapping("/app/addcustomer")
	public ModelAndView addcustomerRecord(@ModelAttribute("cust") customer cust,@ModelAttribute("conc1") customer_contact conc1,
			 @ModelAttribute("mail1") customer_email mail1) {
		ModelAndView mv = new ModelAndView("redirect:/app/customer");
		int custid = repo.savecustomer(cust);
		conc1.setCustomer_id(custid);
		mail1.setCustomer_id(custid);
		repo.addCustomercontact(conc1);
		repo.addCustomermail(mail1);
		mv.addObject("cust", new customer());
		return mv;
		
	}
	
	@GetMapping("app/editcustomer/{customer_id}")
	public ModelAndView findcustomerbyid(@PathVariable("customer_id") int customer_id) {
		
		ModelAndView mv = new ModelAndView("app/library/editcustomer");
		customer cust = repo.getcustomerbyid(customer_id);
		mv.addObject("cust", cust);
		mv.addObject("conc1", repo.getCustomercontact(customer_id).get(0));
		mv.addObject("mail1", repo.getCustomermail(customer_id).get(0));
		
		return mv;
	}

	@PostMapping("app/editcustomer/{customer_id}")
    public ModelAndView updatecust(@ModelAttribute("cust") customer cust,@ModelAttribute("conc1") customer_contact conc1,
			 @ModelAttribute("mail1") customer_email mail1) {
        ModelAndView mv = new ModelAndView("redirect:/app/customer");
        repo.editCustomer(cust);
        repo.editCustomercontact(conc1);
        repo.editCustomermail(mail1);
        return mv;
    }
	
	@PostMapping("app/customer/search")
    public ModelAndView searchcust(@ModelAttribute("serach") search srch ) {
		List<customer> custList = repo.searchcust(srch);
        ModelAndView mv = new ModelAndView("app/library/allcustomer");
        mv.addObject("cust", custList);
        mv.addObject("search", srch);
        return mv;
    }

}
