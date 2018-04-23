package br.org.pmc.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.OrgaoMovimentacao;

public class OrgaoMovimentacaoDao extends GenericDAO<Long, OrgaoMovimentacao>{

	public OrgaoMovimentacaoDao(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public Boolean orgaoMovimentacaoExiste(String nomeOrgaoMovimentacao){
		EntityManager entityManager = super.getEntityManager();
		Query query = entityManager.createQuery("From OrgaoMovimentacao o Where o.origemEnviada = :nomeOrgaoMovimentacao");
		query.setParameter("nomeOrgaoMovimentacao", nomeOrgaoMovimentacao);
		return ( query.getResultList().size() > 0);
	}
	
	public List<OrgaoMovimentacao> listarOrgaoPorStatus (Boolean status){
		
		EntityManager entityManager = super.getEntityManager();
		TypedQuery<OrgaoMovimentacao> query = entityManager.createQuery("SELECT o From OrgaoMovimentacao o Where o.status = :estado",OrgaoMovimentacao.class);
		  query.setParameter("estado", status);
		  return query.getResultList();
	}
}
