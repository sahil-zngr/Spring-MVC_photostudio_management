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

import com.photostudio.models.supplier;
import com.photostudio.models.supplier_contact;
import com.photostudio.models.supplier_email;
import com.photostudio.repository.supplierrepo;
@Controller
public class adminsuppliercontroller {

	@Autowired
	supplierrepo repo;
	
	
	
	@RequestMapping("/admin/supplier")
	public ModelAndView getAllsupplier() {
		List<supplier> suppList = repo.getallsupplier();
        ModelAndView mv = new ModelAndView("admin/library/allsupplier");
        mv.addObject("supp", suppList);
        suppliercontroller suppc = new suppliercontroller();
		mv.addObject("search", suppc.new search());
        return mv;
	}
	@GetMapping("/admin/supplier/contact/{supplier_id}")
	public ModelAndView getsuppliercontact(@PathVariable("supplier_id") int supplier_id) {
		List<supplier_contact> contactList = repo.getSuppliercontact(supplier_id);
		List<supplier_email> mailList = repo.getSuppliermail(supplier_id);
        ModelAndView mv = new ModelAndView("admin/library/suppcontact");
        mv.addObject("contact", contactList);
        mv.addObject("mail", mailList);
        return mv;
	}
	@GetMapping("/admin/supplier/{supplier_id}")
	public ModelAndView getsupplierbyid(@PathVariable("supplier_id") int supplier_id) {
		ModelAndView mv = new ModelAndView("admin/library/allsupplier");
		supplier supp = repo.getsuppbyid(supplier_id);
		mv.addObject("supp", supp);
        return mv;
	}
	
	@GetMapping("/admin/addsupplier")
	public ModelAndView getIndexPage() {
		ModelAndView mv = new ModelAndView("/admin/library/addsupplier");
		mv.addObject("supp", new supplier());
		mv.addObject("conc1",new supplier_contact());
		mv.addObject("mail1",new supplier_email());
		return mv;
		
	}
	@PostMapping("/admin/addsupplier")
	public ModelAndView addcustomerRecord(@ModelAttribute("supp") supplier supp,@ModelAttribute("conc1") supplier_contact conc1,
			 @ModelAttribute("mail1") supplier_email mail1) {
		ModelAndView mv = new ModelAndView("redirect:/admin/supplier");
		int suppid = repo.savesupplier(supp);
		conc1.setSupplier_id(suppid);
		mail1.setSupplier_id(suppid);
		repo.addSuppliercontact(conc1);;
		repo.addSuppliermail(mail1);;
		mv.addObject("supp", new supplier());
		return mv;
		
	}
	
	@GetMapping("admin/editsupplier/{supplier_id}")
	public ModelAndView findsuppbyid(@PathVariable("supplier_id") int supplier_id) {
		
		ModelAndView mv = new ModelAndView("admin/library/addsupplier");
		supplier supp = repo.getsuppbyid(supplier_id);
		mv.addObject("supp", supp);
		mv.addObject("conc1", repo.getSuppliercontact(supplier_id).get(0));
		mv.addObject("mail1", repo.getSuppliermail(supplier_id).get(0));
		
		return mv;
	}

	@PostMapping("admin/editsupplier/{supplier_id}")
    public ModelAndView updatesupp(@ModelAttribute("supp") supplier supp,@ModelAttribute("conc1") supplier_contact conc1,
			 @ModelAttribute("mail1") supplier_email mail1) {
        ModelAndView mv = new ModelAndView("redirect:/admin/supplier");
        repo.editsupplier(supp);
        repo.editsuppliercontact(conc1);
        repo.editsuppliermail(mail1);
        return mv;
    }
	
	@PostMapping("admin/supplier/search")
    public ModelAndView search(@ModelAttribute("search") com.photostudio.controllers.suppliercontroller.search srch ) {
		List<supplier> suppList = repo.search(srch);
        ModelAndView mv = new ModelAndView("admin/library/allsupplier");
        mv.addObject("supp", suppList);
        mv.addObject("search", srch);
        return mv;
    }
	
}
