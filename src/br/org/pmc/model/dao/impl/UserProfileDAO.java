package br.org.pmc.model.dao.impl;

import javax.persistence.EntityManager;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.UserProfile;

public class UserProfileDAO extends GenericDAO<Long, UserProfile> {

	public UserProfileDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
}
