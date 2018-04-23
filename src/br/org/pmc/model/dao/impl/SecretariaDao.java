package br.org.pmc.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.Secretaria;

public class SecretariaDao  extends GenericDAO<Long, Secretaria>{

	public SecretariaDao(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}
	
	public Boolean secretariaExiste( Secretaria  secretaria){
		EntityManager entityManager = super.getEntityManager();
		Query query = entityManager.createQuery("Select s From Secretaria s Where s.nome =:novoNome");
		query.setParameter("novoNome", secretaria.getNome());
		return ( query.getResultList().size() > 0);
	}
	
	public List<Secretaria> getSecretariasForStatus(Boolean status){
		
		EntityManager entityManager = super.getEntityManager();
		TypedQuery<Secretaria> query = entityManager.createQuery("SELECT s FROM Secretaria s Where s.status = :estado",Secretaria.class);
		query.setParameter("estado", status);
		return query.getResultList();
	}
}
