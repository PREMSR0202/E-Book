package com.service;

import java.util.List;

import com.model.Payment;

public interface PaymentService {

	public void createPayment(Payment payment, int userid);

	public List<Payment> findAll();


}
