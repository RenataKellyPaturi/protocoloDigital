package br.org.pmc.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.Requerente;

public class RequerenteDao extends GenericDAO<Long, Requerente> {

	public RequerenteDao(EntityManager entityManager) {
		super(entityManager);
	}

	public boolean matriculaExiste (Requerente requerente){
		EntityManager entityManager = super.getEntityManager();		
		Query query = entityManager.createQuery("SELECT r FROM Requerente r WHERE r.matricula = :matricula");
		query.setParameter("matricula", requerente.getMatricula());
		return (query.getResultList().size() > 0);
	}

	public Boolean requerenteExistente(Requerente requerente){
		EntityManager entityManager = super.getEntityManager();
		if(requerente.getCnpj() != null){
			Query query = entityManager.createQuery("From Requerente r Where r.cnpj = :cnpj");
			query.setParameter("cnpj", requerente.getCnpj());
			return ( query.getResultList().size() > 0);
		}else{
			Query query = entityManager.createQuery("From Requerente r Where r.cpf = :cpf");
			query.setParameter("cpf", requerente.getCpf());
			return ( query.getResultList().size() > 0);
		}
			
	}
	
	public List<Requerente> listRequerentePorStatus(Boolean status){
		
		EntityManager entityManager = super.getEntityManager();
		TypedQuery<Requerente> query = entityManager.createQuery("Select r From Requerente r Where r.status = :statusAtual",Requerente.class);
		query.setParameter("statusAtual", status);
		return query.getResultList();
	}
}
