package br.org.pmc.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.Servico;

public class ServicoDao extends GenericDAO<Long, Servico> {

	public ServicoDao(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public Boolean servicoExistente(String nome){
		
		EntityManager entityManager = super.getEntityManager();
		Query query = entityManager.createQuery(" FROM Servico s WHERE s.nome = :nome", Servico.class);
		query.setParameter("nome",nome);
		return ( query.getResultList().size() > 0);
	}
	
	public List<Servico> listaServicoSUrb(Boolean status){
		EntityManager entityManager = super.getEntityManager();
		TypedQuery<Servico> query = entityManager.createQuery("Select s FROM Servico s Where s.servicoUrb = :status AND s.status = 1", Servico.class);
		query.setParameter("status", status);
		return query.getResultList();
	}
	
	public List<Servico> listaServicosPorStatus(Boolean status){
		EntityManager entityManager = super.getEntityManager();
		TypedQuery<Servico> query = entityManager.createQuery("Select s FROM Servico s Where s.status = :statusServico", Servico.class);
		query.setParameter("statusServico", status);
		return query.getResultList();
	}
		
}
