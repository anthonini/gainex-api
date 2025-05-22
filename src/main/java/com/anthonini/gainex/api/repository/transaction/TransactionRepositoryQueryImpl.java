package com.anthonini.gainex.api.repository.transaction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.anthonini.gainex.api.model.Transaction;
import com.anthonini.gainex.api.repository.filter.TransactionFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TransactionRepositoryQueryImpl implements TransactionRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Transaction> filter(TransactionFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
		Root<Transaction> root = criteria.from(Transaction.class);
		
		Predicate[] predicates = createRestriction(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Transaction> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] createRestriction(TransactionFilter lancamentoFilter, CriteriaBuilder builder, Root<Transaction> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.hasText(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root
							.get("descricao")), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (lancamentoFilter.getDueDateFrom() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(
							root.get("dueDate"), lancamentoFilter.getDueDateFrom()));
		}
		
		if (lancamentoFilter.getDueDateTo() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(
							root.get("dueDate"), lancamentoFilter.getDueDateTo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}
