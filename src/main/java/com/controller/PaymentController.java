package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.Books;
import com.model.Order;
import com.model.Payment;
import com.model.User;
import com.service.PaymentService;
import com.service.UserService;

@Controller
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public String payment(Books books, HttpSession session, Model model, Order order) {
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		return "Payment";
	}

	@RequestMapping(value = "/makepayment", method = RequestMethod.GET)
	public String makePayment(Payment payment, HttpSession session) {

		payment.setUser_id((int) session.getAttribute("user"));

		paymentService.createPayment(payment, (int) session.getAttribute("user"));

		session.setAttribute("account", true);

		User u = userService.findbyID((int) session.getAttribute("user"));
		
		session.setAttribute("download", u.getInvoice());

		return "redirect:/successpayment";
	}

}
