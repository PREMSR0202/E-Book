package com.shedular;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.model.Payment;
import com.model.User;
import com.service.PaymentService;
import com.service.UserService;

@Component
public class SubscriptionExpiredShedular {

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserService userService;

	@Scheduled(cron = "0 0 0 * * ?")
	public void subscription() {

		List<Payment> payment = paymentService.findAll();
		int n = payment.size();
		for (int i = 0; i < n; i++) {

			LocalDate current = LocalDate.now();
			if (current.isAfter(payment.get(i).getEnd_date())) {

				int id = payment.get(i).getUser_id();
				User user = userService.findbyID(id);
				user.setAccount(false);
				userService.save(user);

			}
		}

	}

}
