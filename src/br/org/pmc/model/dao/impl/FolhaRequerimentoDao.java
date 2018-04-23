package br.org.pmc.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.EnumSituacaoStatus;
import br.org.pmc.model.entity.impl.FolhaRequerimento;

public class FolhaRequerimentoDao extends GenericDAO<Long, FolhaRequerimento> {

	public FolhaRequerimentoDao(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public Boolean processoAbertoFolhaRequerimento(FolhaRequerimento folha){
	EntityManager entityManager = super.getEntityManager();
	Query query = entityManager.createQuery("SELECT f FROM FolhaRequerimento f WHERE f.numeroProtocolo = :protocolo ");
	query.setParameter("protocolo",folha.getNumeroProtocolo());
	return (query.getResultList().size() > 0);
	}
	
	public List<FolhaRequerimento> listaFichaProcessosPorStatus(EnumSituacaoStatus status){
		EntityManager entityManager = super.getEntityManager();
		TypedQuery<FolhaRequerimento> query = entityManager.createQuery("Select f FROM FolhaRequerimento f WHERE f.statusFolhaRequerimento = :status",FolhaRequerimento.class);
		query.setParameter("status", status);
		return query.getResultList();
	}
	
	
	public List<FolhaRequerimento> listaFichaRequerimentosPorStatus(Boolean status){
		EntityManager entityManager = super.getEntityManager();
		TypedQuery<FolhaRequerimento> query = entityManager.createQuery("Select f FROM FolhaRequerimento f WHERE f.status= :statusAtual",FolhaRequerimento.class);
		query.setParameter("statusAtual", status);
		return query.getResultList();
	}
	
}