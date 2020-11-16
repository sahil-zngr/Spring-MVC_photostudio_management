package com.photostudio.controllers;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.photostudio.mailservice;
import com.photostudio.models.material;
import com.photostudio.models.material_order;
import com.photostudio.models.material_supplier;
import com.photostudio.models.supplier;
import com.photostudio.models.supplier_email;
import com.photostudio.repository.materialrepo;
import com.photostudio.repository.supplierrepo;
import com.photostudio.repository.userrepo;

@Controller
public class materialontroller {
	
	@Autowired
	materialrepo repo;
	
	@Autowired
	supplierrepo supprepo;
	
	@Autowired
	userrepo userrepos;
	
	@Autowired
	private mailservice notificationService;
	
	public class matQuantity{
		private int quantity;

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
	}
	
	public class search{
		private String str;

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
		}
		
	}

	@RequestMapping("/app/material")
	public ModelAndView getmat() {
		List<material> materialList = repo.getmaterial();
        ModelAndView mv = new ModelAndView("app/library/allmaterial");
        mv.addObject("material", materialList);
        mv.addObject("search", new search());
        return mv;
	}
	
	@RequestMapping("/app/materialorder")
	public ModelAndView getmatorder() {
		List<material> matorderList = repo.getmatorder();
        ModelAndView mv = new ModelAndView("app/library/materialorder");
        mv.addObject("mo", matorderList);
        return mv;
	}
	
	@GetMapping("/app/addmaterial")
	public ModelAndView getIndexPage() {
		ModelAndView mv = new ModelAndView("/app/library/addmaterial");
		mv.addObject("mat", new material());
		return mv;
		
	}
	@PostMapping("/app/addmaterial")
	public ModelAndView addmaterial(@ModelAttribute("mat") material mat) {
		ModelAndView mv = new ModelAndView("redirect:/app/material");
		repo.savematerial(mat);
		mv.addObject("material", new material());
		return mv;
		
	}
	@GetMapping("/app/material/supplier/{material_id}")
	public ModelAndView matsupp(@PathVariable("material_id") int material_id) {
		ModelAndView mv = new ModelAndView("/app/library/materialsupplier");
		List<material_supplier> matsuppList = repo.getmaterialsupp(material_id);
		mv.addObject("matsupp", matsuppList);
		mv.addObject("qut",new matQuantity() );
		return mv;
		
	}
	@PostMapping("/app/materialorder/{material_id}/{supplier_id}/{price}")
	public ModelAndView matorder(@PathVariable("material_id") int material_id, @PathVariable("supplier_id") int supplier_id,@PathVariable("price") int price, @ModelAttribute("qut") matQuantity qut) {
		ModelAndView mv = new ModelAndView("redirect:/app/materialorder");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		material_order mo = new material_order();
		mo.setMaterial_id(material_id);
		mo.setSupplier_id(supplier_id);
		mo.setQuantity(qut.getQuantity());
		int empid  = userrepos.userempid(user.getUsername());
		mo.setEmployee_id(empid);
		mo.setPrice(qut.getQuantity()*price);
		long now = System.currentTimeMillis();
		java.sql.Date sqlDate = new java.sql.Date(now);
        Time sqltime = new Time(now);
        mo.setOdate(sqlDate);
		mo.setOtime(sqltime);
		repo.savematerialorder(mo);
		mv.addObject("mo", new material_order());
		supplier_email suppmail = supprepo.getSuppliermail(supplier_id).get(0);
		material mats = repo.getmaterialbyid(material_id).get(0);
		String str = "material order \n \n material id :"+material_id+"\n\n Name: "+ mats.getMaterial_name();
		str = str+"\n\n description: "+ mats.getDescription()+"\n\n Quantity:  "+ qut.getQuantity()+"		\n";
		try {
			notificationService.sendEmail(suppmail.getEmail(), str);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return mv;
		
	}
	
	@GetMapping("app/editmaterial/{material_id}")
	public ModelAndView findmatbyid(@PathVariable("material_id") int material_id) {
		
		ModelAndView mv = new ModelAndView("app/library/addmaterial");
		material mat = repo.getmaterialbyid(material_id).get(0);
		mv.addObject("mat", mat);
		return mv;
	}

	@PostMapping("app/editmaterial/{material_id}")
    public ModelAndView updatemat(@ModelAttribute("mat") material mat) {
        ModelAndView mv = new ModelAndView("redirect:/app/material");
        repo.editmaterial(mat);
        return mv;
    }
	
	@GetMapping("/app/material/addmaterialsupplier/{material_id}")
	public ModelAndView addmatsupp(@PathVariable("material_id") int material_id) {
		ModelAndView mv = new ModelAndView("/app/library/addmaterialsupplier");
		List<supplier> suppList = supprepo.getallsupplier();
		mv.addObject("supp", suppList);
		mv.addObject("material_id", material_id);
		mv.addObject("price", new matQuantity());
		return mv;
		
	}
	
	@PostMapping("/app/material/addmaterialsupplier/{material_id}/{supplier_id}")
	public ModelAndView addmatsupppost(@PathVariable("material_id") int material_id, @PathVariable("supplier_id") int supplier_id, @ModelAttribute("price") matQuantity price) {
		ModelAndView mv = new ModelAndView("redirect:/app/material/supplier/"+ material_id);
		material_supplier ms = new material_supplier();
		ms.setMaterial_id(material_id);
		ms.setSupplier_id(supplier_id);
		ms.setPrice(price.getQuantity());
		repo.savematerialsupplier(ms);
		return mv;
		
	}
	
	@PostMapping("app/material/search")
    public ModelAndView search(@ModelAttribute("search") search srch ) {
		List<material> materialList = repo.search(srch);
        ModelAndView mv = new ModelAndView("app/library/allmaterial");
        mv.addObject("material", materialList);
        mv.addObject("search", srch);
        return mv;
    }
	@ResponseBody
	@GetMapping("app/materialorder/delete/{morder_id}")
	public ModelAndView delmatord(@PathVariable("morder_id") int morder_id) {
		repo.delmatord(morder_id);
		List<material> matorderList = repo.getmatorder();
        ModelAndView mv = new ModelAndView("app/library/materialorder");
        mv.addObject("mo", matorderList);
		return mv;
	}
	
}
