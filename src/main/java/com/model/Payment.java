package com.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private LocalDate start_date;
	private LocalDate end_date;
	private int user_id;

	private String trans_id;

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Payment(int id, LocalDate start_date, LocalDate end_date, int user_id, String trans_id) {
		super();
		this.id = id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.user_id = user_id;
		this.trans_id = trans_id;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", start_date=" + start_date + ", end_date=" + end_date + ", user_id=" + user_id
				+ ", trans_id=" + trans_id + "]";
	}

	public Payment() {
		
	}

}
