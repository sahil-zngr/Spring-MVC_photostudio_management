package com.photostudio.controllers;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photostudio.controllers.ordercontroller.search;
import com.photostudio.models.material;
import com.photostudio.models.material_sold;
import com.photostudio.models.orders;
import com.photostudio.models.outdoororder;
import com.photostudio.models.photo_print;
import com.photostudio.models.service;
import com.photostudio.models.studio_photograph;
import com.photostudio.repository.orderrepo;

@Controller
public class adminordercontroller {

	@Autowired
	orderrepo repo;
	
	public class Itemlist{
		
		public List<studio_photograph> photo = new ArrayList<studio_photograph>();
		public List<photo_print> print=new ArrayList<photo_print>();
		public List<material_sold> mat = new ArrayList<material_sold>();
		public List<outdoororder> outdoor = new ArrayList<outdoororder>();
		public List<service> ser = new ArrayList<service>();
	}
	Itemlist itemlist =  new Itemlist();
	List<studio_photograph> photo1 = new ArrayList<studio_photograph>();
	
	
	@RequestMapping("/admin/order")
	public ModelAndView getorders() {
		List<orders> orderList = repo.getorders();
		ordercontroller ordc = new ordercontroller();
        ModelAndView mv = new ModelAndView("admin/library/allorders");
        mv.addObject("order", orderList);
        mv.addObject("search", ordc.new search());
        return mv;
	}
	@RequestMapping("/admin/customer/order/{cust_id}")
	public ModelAndView getcustorders(@PathVariable("cust_id") int cust_id) {
		List<orders> orderList = repo.getcustorders(cust_id);
        ModelAndView mv = new ModelAndView("admin/library/allorders");
        mv.addObject("order", orderList);
        ordercontroller ordc = new ordercontroller();
        mv.addObject("search", ordc.new search());
        return mv;
	}
	
	@GetMapping("/admin/order/{order_id}")
	public ModelAndView getitems(@PathVariable("order_id") int order_id) {
		List<studio_photograph> photoList = repo.getStudiophotograaph(order_id);
		List<photo_print> printList = repo.getPhotoprint(order_id);
		List<outdoororder> outdoorList = repo.getOutdoororder(order_id);
		List<service> serviceList = repo.getService(order_id);
		List<material_sold> materialList = repo.getMaterialsold(order_id);
        ModelAndView mv = new ModelAndView("admin/library/orderitems");
        mv.addObject("photo", photoList);
        mv.addObject("print", printList);
        mv.addObject("outdoor", outdoorList);
        mv.addObject("service", serviceList);
        mv.addObject("material", materialList);
        return mv;
	}
	
	
	
	@RequestMapping("/admin/customer/addorder/{cust_id}")
	public ModelAndView addorder(@PathVariable("cust_id") int cust_id) {
        ModelAndView mv = new ModelAndView("admin/library/additems");
        itemlist.mat.clear();
        itemlist.photo.clear();
        itemlist.outdoor.clear();
        itemlist.print.clear();
        itemlist.ser.clear();
        mv.addObject("cust_id", cust_id);
		mv.addObject("photo", itemlist.photo);
		mv.addObject("print", itemlist.print);
		mv.addObject("outdoor", itemlist.outdoor);
		mv.addObject("service", itemlist.ser);
		mv.addObject("material", itemlist.mat);
        return mv;
	}

	@PostMapping("/admin/customer/addorder/{cust_id}")
	public ModelAndView saveorder(@PathVariable("cust_id") int cust_id) {
        
        long now = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(now);
        Time sqltime = new Time(now);
        orders neword = new orders();
        neword.setCustomer_id(cust_id);
        neword.setOrderdate(sqlDate);
        neword.setOrdertime(sqltime);
        neword.setEmployee_id(1);
        int tp = 0;
        neword.setTotal_price(0);
        int order_id = repo.neworder(neword);
        for(studio_photograph photo : itemlist.photo) {
        	photo.setOrder_id(order_id);
        	repo.savephoto(photo);
        	tp = tp + photo.getPrice();
        }
        for(photo_print print : itemlist.print) {
        	print.setOrder_id(order_id);
        	repo.saveprint(print);
        	tp = tp + print.getPrice();
        }
        for(outdoororder outdoor : itemlist.outdoor) {
        	outdoor.setOrder_id(order_id);
        	repo.saveoutdoor(outdoor);
        	tp = tp + outdoor.getPrice();
        }
        for(material_sold mat: itemlist.mat) {
        	mat.setOrder_id(order_id);
        	repo.savematerial(mat);
        	repo.descmaterialqut(mat.getMaterial_id());
        	tp = tp + mat.getPrice();
        }
        for(service ser : itemlist.ser) {
        	ser.setOrder_id(order_id);
        	repo.saveservice(ser);
        	tp = tp + ser.getPrice();
        }
        repo.updatetp(order_id, tp);
        ModelAndView mv = new ModelAndView("redirect:/admin/order/"+order_id);
      List<studio_photograph> photoList = repo.getStudiophotograaph(order_id);
		List<photo_print> printList = repo.getPhotoprint(order_id);
		List<outdoororder> outdoorList = repo.getOutdoororder(order_id);
		List<service> serviceList = repo.getService(order_id);
		List<material_sold> materialList = repo.getMaterialsold(order_id);
      mv.addObject("photo", photoList);
      mv.addObject("print", printList);
      mv.addObject("outdoor", outdoorList);
      mv.addObject("service", serviceList);
      mv.addObject("material", materialList);
      return mv;
	}
	
	@RequestMapping("/admin/customer/addorderlist/{cust_id}")
	public ModelAndView addorderlist(@PathVariable("cust_id") int cust_id) {
        ModelAndView mv = new ModelAndView("admin/library/additems");
        mv.addObject("cust_id", cust_id);
		mv.addObject("photo", itemlist.photo);
		mv.addObject("print", itemlist.print);
		mv.addObject("outdoor", itemlist.outdoor);
		mv.addObject("service", itemlist.ser);
		mv.addObject("material", itemlist.mat);
        return mv;
        
	}
	
	@RequestMapping("/admin/order/addphoto/{cust_id}")
	public ModelAndView addphoto(@PathVariable("cust_id") int cust_id) {
        ModelAndView mv = new ModelAndView("app/library/addphoto");
        mv.addObject("photo", new studio_photograph());
        return mv;
	}
	@PostMapping("/admin/order/addphoto/{cust_id}")
	public ModelAndView addphoto(@PathVariable("cust_id") int cust_id, @ModelAttribute("photo") studio_photograph photo) {
        ModelAndView mv = new ModelAndView("redirect:/admin/customer/addorderlist/"+cust_id);
        itemlist.photo.add(photo);
        mv.addObject("photo", itemlist.photo);
		mv.addObject("print", itemlist.print);
		mv.addObject("outdoor", itemlist.outdoor);
		mv.addObject("service", itemlist.ser);
		mv.addObject("material", itemlist.mat);
        return mv;
	}
	
	@RequestMapping("/admin/order/addprint/{cust_id}")
	public ModelAndView addprint(@PathVariable("cust_id") int cust_id) {
        ModelAndView mv = new ModelAndView("admin/library/addprint");
        mv.addObject("print", new photo_print());
        return mv;
	}
	@PostMapping("/admin/order/addprint/{cust_id}")
	public ModelAndView addprint(@PathVariable("cust_id") int cust_id, @ModelAttribute("print") photo_print print) {
        ModelAndView mv = new ModelAndView("redirect:/admin/customer/addorderlist/"+cust_id);
        itemlist.print.add(print);
        mv.addObject("photo", itemlist.photo);
		mv.addObject("print", itemlist.print);
		mv.addObject("outdoor", itemlist.outdoor);
		mv.addObject("service", itemlist.ser);
		mv.addObject("material", itemlist.mat);
        return mv;
	}
	
	@RequestMapping("/admin/order/addoutdoor/{cust_id}")
	public ModelAndView addoutdoor(@PathVariable("cust_id") int cust_id) {
        ModelAndView mv = new ModelAndView("admin/library/addoutdoor");
        mv.addObject("outdoor", new outdoororder());
        return mv;
	}
	@PostMapping("/admin/order/addoutdoor/{cust_id}")
	public ModelAndView addoutdoor(@PathVariable("cust_id") int cust_id, @ModelAttribute("outdoor") outdoororder outdoor) {
        ModelAndView mv = new ModelAndView("redirect:/admin/customer/addorderlist/"+cust_id);
        itemlist.outdoor.add(outdoor);
        mv.addObject("photo", itemlist.photo);
		mv.addObject("print", itemlist.print);
		mv.addObject("outdoor", itemlist.outdoor);
		mv.addObject("service", itemlist.ser);
		mv.addObject("material", itemlist.mat);
        return mv;
	}
	
	@RequestMapping("/admin/order/addmaterial/{cust_id}")
	public ModelAndView addmat(@PathVariable("cust_id") int cust_id) {
        ModelAndView mv = new ModelAndView("admin/library/addsoldmaterial");
        List<material> materialList = repo.getmateriallist();
        for(material_sold mat: itemlist.mat) {
        	int i = 0;
        	for(material matlist: materialList) {
            	if(matlist.getMaterial_id() == mat.getMaterial_id()) {
            		materialList.get(i).setAvailable_quantity(materialList.get(i).getAvailable_quantity()-1);
            		if(materialList.get(i).getAvailable_quantity() == 0) {
            			materialList.remove(i);
            		}
            		break;
            	}
            	i = i+1;
            }
        }
        mv.addObject("material",materialList);
        mv.addObject("cust_id", cust_id);
        return mv;
	}
	@PostMapping("/admin/order/addmaterial/{material_id}/{unit_price}/{name}/{cust_id}")
	public ModelAndView addmat(@PathVariable("material_id") int material_id,@PathVariable("unit_price") int unit_price,@PathVariable("name") String name ,@PathVariable("cust_id") int cust_id) {
        ModelAndView mv = new ModelAndView("redirect:/admin/customer/addorderlist/"+cust_id);
        material_sold matsold = new material_sold();
        matsold.setMaterial_id(material_id);
        matsold.setPrice(unit_price);
        matsold.setName(name);
        itemlist.mat.add(matsold);
        mv.addObject("photo", itemlist.photo);
		mv.addObject("print", itemlist.print);
		mv.addObject("outdoor", itemlist.outdoor);
		mv.addObject("service", itemlist.ser);
		mv.addObject("material", itemlist.mat);
        return mv;
	}
	
	@RequestMapping("/admin/order/addservice/{cust_id}")
	public ModelAndView addser(@PathVariable("cust_id") int cust_id) {
        ModelAndView mv = new ModelAndView("admin/library/addservice");
        mv.addObject("service", new service());
        return mv;
	}
	@PostMapping("/admin/order/addservice/{cust_id}")
	public ModelAndView addser(@PathVariable("cust_id") int cust_id, @ModelAttribute("service") service service) {
        ModelAndView mv = new ModelAndView("redirect:/admin/customer/addorderlist/"+cust_id);
        itemlist.ser.add(service);
        mv.addObject("photo", itemlist.photo);
		mv.addObject("print", itemlist.print);
		mv.addObject("outdoor", itemlist.outdoor);
		mv.addObject("service", itemlist.ser);
		mv.addObject("material", itemlist.mat);
        return mv;
	}
	@PostMapping("admin/order/search")
    public ModelAndView searchcust(@ModelAttribute("serach") search srch ) {
		List<orders> ordList = repo.search(srch);
        ModelAndView mv = new ModelAndView("admin/library/allorders");
        mv.addObject("order", ordList);
        mv.addObject("search", srch);
        return mv;
    }
}
