package br.org.pmc.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.FichaProcesso;


public class FichaProcessoDao  extends GenericDAO<Long, FichaProcesso>{

	public FichaProcessoDao(EntityManager entityManager) {
		super(entityManager);
	}
	public Boolean fichaProcessoExistente(FichaProcesso ficha){
		
		EntityManager entityManager = super.getEntityManager();
		Query query =  entityManager.createQuery("SELECT f FROM FichaProcesso f  WHERE f.numeroProcesso = :numeroProcessoFicha");
		query.setParameter("numeroProcessoFicha", ficha.getNumeroProcesso());
		return (query.getResultList().size() > 0);
	}

	
	public List<FichaProcesso> getProcessoForStatus(Boolean status){
		
		EntityManager entityManager = super.getEntityManager();
		TypedQuery<FichaProcesso> query = entityManager.createQuery("SELECT p FROM FichaProcesso p Where p.status = :estado",FichaProcesso.class);
		query.setParameter("estado", status);
		return query.getResultList();
	}
}
