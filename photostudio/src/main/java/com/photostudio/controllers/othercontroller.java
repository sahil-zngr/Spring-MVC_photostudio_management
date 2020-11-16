package com.photostudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photostudio.models.material_sold;
import com.photostudio.models.outdoororder;
import com.photostudio.models.photo_print;
import com.photostudio.models.service;
import com.photostudio.models.studio_photograph;
import com.photostudio.repository.otherrepo;

@Controller
public class othercontroller {
	
	@Autowired
	otherrepo repo;

	@RequestMapping("/app/allprint")
	public ModelAndView getprint() {
		List<photo_print> printList = repo.getallprint();
        ModelAndView mv = new ModelAndView("app/library/allprint");
        mv.addObject("print", printList);
        return mv;
	}
	
	@RequestMapping("/app/alloutdoororder")
	public ModelAndView getoutdoororder() {
		List<outdoororder> outdoorList = repo.getalloutdoor();
        ModelAndView mv = new ModelAndView("app/library/alloutdoororders");
        mv.addObject("outdoor", outdoorList);
        return mv;
	}
	
	@RequestMapping("/app/allphoto")
	public ModelAndView getphoto() {
		List<studio_photograph> photoList = repo.getallphoto();
        ModelAndView mv = new ModelAndView("app/library/allphoto");
        mv.addObject("photo", photoList);
        return mv;
	}
	
	@RequestMapping("/app/allmaterialsold")
	public ModelAndView getmaterialsold() {
		List<material_sold> materialList = repo.getallmaterialsold();
        ModelAndView mv = new ModelAndView("app/library/allmaterialsold");
        mv.addObject("material", materialList);
        return mv;
	}
	
	@RequestMapping("/app/allservice")
	public ModelAndView getservices() {
		List<service> serviceList = repo.getallservice();
        ModelAndView mv = new ModelAndView("app/library/allservices");
        mv.addObject("service", serviceList);
        return mv;
	}
	
}
