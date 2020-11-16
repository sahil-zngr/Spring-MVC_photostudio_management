package com.photostudio.controllers;

import java.sql.Time;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.photostudio.mailservice;
import com.photostudio.models.customer_email;
import com.photostudio.models.invoice;
import com.photostudio.models.material_sold;
import com.photostudio.models.orders;
import com.photostudio.models.outdoororder;
import com.photostudio.models.photo_print;
import com.photostudio.models.sendemail;
import com.photostudio.models.service;
import com.photostudio.models.studio_photograph;
import com.photostudio.repository.customerrepo;
import com.photostudio.repository.orderrepo;
import com.photostudio.repository.otherrepo;

@Controller
public class mailcontroller {

	
	@Autowired
	private mailservice notificationService;
	
	@Autowired
	orderrepo ordrepo;
	
	@Autowired
	customerrepo custrepo;
	
	@Autowired
	otherrepo otherrepo;

	@RequestMapping("/app/order/sendmail/{order_id}")
	public ModelAndView send(@PathVariable("order_id") int order_id) {
		
		
		orders ord =  ordrepo.getorderbyorderid(order_id).get(0);
		customer_email custmail = custrepo.getCustomermail(ord.getCustomer_id()).get(0);
		String str = "Invoice \\n\\n Order id :"+order_id+"\n\n 	item				ID		 	Price	\n";
		List<studio_photograph> photoList = ordrepo.getStudiophotograaph(order_id);
		List<photo_print> printList = ordrepo.getPhotoprint(order_id);
		List<outdoororder> outdoorList = ordrepo.getOutdoororder(order_id);
		List<service> serviceList = ordrepo.getService(order_id);
		List<material_sold> materialList = ordrepo.getMaterialsold(order_id);
		for(studio_photograph photo : photoList) {
			str = str + "Studio photograph		"  +photo.getPhoto_id()+"			"+photo.getPrice()+" \n";
        }
        for(photo_print print : printList) {
        	str = str + "Print					"  +print.getPrint_id()+"			"+print.getPrice()+" \n";
        }
        for(outdoororder outdoor : outdoorList) {
        	str = str + "Outdoor				"  +outdoor.getOutdoororder_id()+"			"+outdoor.getPrice()+" \n";
        }
        for(material_sold mat: materialList) {
        	str = str + "Material				"  +mat.getMaterialsold_id()+"			"+mat.getPrice()+" \n";
        }
        for(service ser : serviceList) {
        	str = str + "Service				"  +ser.getService_id()+"			"+ser.getPrice()+" \n";
        }
        str = str+ "\nTotal price: "+ord.getTotal_price()+"  \n\n";
		try {
			notificationService.sendEmail(custmail.getEmail(), str);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		long now = System.currentTimeMillis();
        java.sql.Date sqlDate = new java.sql.Date(now);
        Time sqltime = new Time(now);
        invoice inc = new invoice();
        sendemail smail = new sendemail();
        inc.setOrder_id(order_id);
        inc.setCustomer_id(ord.getCustomer_id());
        inc.setPdate(sqlDate);
        inc.setPtime(sqltime);
        inc.setAmount(ord.getTotal_price());
        inc.setStatus("PAID");
        smail.setOrder_id(order_id);
        smail.setSdate(sqlDate);
        smail.setStime(sqltime);
        smail.setCustomer_email(custmail.getEmail());
        smail.setStatus("SEND");
        otherrepo.saveinvoice(inc);
        otherrepo.savesendmail(smail);
		ModelAndView mv = new ModelAndView("redirect:/app/customer");
		return mv;
	}


	@RequestMapping("send-mail-attachment")
	public String sendWithAttachment() throws MessagingException {

		try {
			notificationService.sendEmailWithAttachment("zingoorr@gmail.com");
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Congratulations! Your mail has been send to the user.";
	}
	
}
