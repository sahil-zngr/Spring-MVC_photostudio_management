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
import com.photostudio.models.customer_contact;
import com.photostudio.models.customer_email;
import com.photostudio.repository.customerrepo;

@Controller
public class admincustomercontroller {

	@Autowired
	customerrepo repo;
	
	@RequestMapping("/admin/customer")
	public ModelAndView getAllcustomer() {
		List<customer> custList = repo.getallCustomers();
        ModelAndView mv = new ModelAndView("admin/library/allcustomer");
        mv.addObject("cust", custList);
        customercontroller custc = new customercontroller();
        mv.addObject("search", custc.new search());
        return mv;
	}
	@GetMapping("/admin/customer/contact/{customer_id}")
	public ModelAndView getcustomercontact(@PathVariable("customer_id") int customer_id) {
		List<customer_contact> contactList = repo.getCustomercontact(customer_id);
		List<customer_email> mailList = repo.getCustomermail(customer_id);
        ModelAndView mv = new ModelAndView("admin/library/contact");
        mv.addObject("contact", contactList);
        mv.addObject("mail", mailList);
        return mv;
	}
	
	
	@GetMapping("/admin/addcustomer")
	public ModelAndView getIndexPage() {
		ModelAndView mv = new ModelAndView("/admin/library/addcustomer");
		mv.addObject("cust", new customer());
		mv.addObject("conc1",new customer_contact());
		mv.addObject("mail1",new customer_email());
		return mv;
		
	}
	@PostMapping("/admin/addcustomer")
	public ModelAndView addcustomerRecord(@ModelAttribute("cust") customer cust,@ModelAttribute("conc1") customer_contact conc1,
			 @ModelAttribute("mail1") customer_email mail1) {
		ModelAndView mv = new ModelAndView("redirect:/admin/customer");
		int custid = repo.savecustomer(cust);
		conc1.setCustomer_id(custid);
		mail1.setCustomer_id(custid);
		repo.addCustomercontact(conc1);
		repo.addCustomermail(mail1);
		mv.addObject("cust", new customer());
		return mv;
		
	}
	
	@GetMapping("admin/editcustomer/{customer_id}")
	public ModelAndView findcustomerbyid(@PathVariable("customer_id") int customer_id) {
		
		ModelAndView mv = new ModelAndView("admin/library/editcustomer");
		customer cust = repo.getcustomerbyid(customer_id);
		mv.addObject("cust", cust);
		mv.addObject("conc1", repo.getCustomercontact(customer_id).get(0));
		mv.addObject("mail1", repo.getCustomermail(customer_id).get(0));
		
		return mv;
	}

	@PostMapping("admin/editcustomer/{customer_id}")
    public ModelAndView updatecust(@ModelAttribute("cust") customer cust,@ModelAttribute("conc1") customer_contact conc1,
			 @ModelAttribute("mail1") customer_email mail1) {
        ModelAndView mv = new ModelAndView("redirect:/admin/customer");
        repo.editCustomer(cust);
        repo.editCustomercontact(conc1);
        repo.editCustomermail(mail1);
        return mv;
    }
	@PostMapping("admin/customer/search")
    public ModelAndView searchcust(@ModelAttribute("serach") search srch ) {
		List<customer> custList = repo.searchcust(srch);
        ModelAndView mv = new ModelAndView("admin/library/allcustomer");
        mv.addObject("cust", custList);
        mv.addObject("search", srch);
        return mv;
    }
	
}
