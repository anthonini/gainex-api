package com.anthonini.gainex.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.anthonini.gainex.model.TransactionType;

public class TransactionResume {

	private Long id;
	private String description;
	private LocalDate dueVencimento;
	private LocalDate paymentDate;
	private BigDecimal amount;
	private TransactionType type;
	private String category;
	private String person;
	
	public TransactionResume(Long id, String description, LocalDate dueVencimento, LocalDate paymentDate,
			BigDecimal amount, TransactionType type, String category, String person) {
		this.id = id;
		this.description = description;
		this.dueVencimento = dueVencimento;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.type = type;
		this.category = category;
		this.person = person;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueVencimento() {
		return dueVencimento;
	}

	public void setDueVencimento(LocalDate dueVencimento) {
		this.dueVencimento = dueVencimento;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
}
