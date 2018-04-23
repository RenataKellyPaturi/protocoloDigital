package br.org.pmc.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.LogDeAtividade;
import br.org.pmc.model.entity.impl.User;

public class LogDeAtividadeDAO extends GenericDAO<Long, LogDeAtividade> {

	public LogDeAtividadeDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<LogDeAtividade> listRecentsByUser(User user) {
		EntityManager entityManager = super.getEntityManager();
		Query query = entityManager.createQuery("SELECT obj FROM LogDeAtividade obj WHERE obj.user = :user ORDER BY obj.date DESC");
		query.setParameter("user", user);
		query.setMaxResults(5);
		return (List<LogDeAtividade>) query.getResultList();
	}
}