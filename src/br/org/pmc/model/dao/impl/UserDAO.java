package br.org.pmc.model.dao.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.org.pmc.model.dao.GenericDAO;
import br.org.pmc.model.entity.impl.User;

public class UserDAO extends GenericDAO<Long, User> {

	public UserDAO(EntityManager entityManager) {
		super(entityManager);
	}

	/**
	 * Retorna um {@link User} por cpf e senha
	 * 
	 * @param user
	 * @return {@link List}
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public List<User> getByCpfAndPassword(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		EntityManager entityManagger = super.getEntityManager();

		// Lista que será retornada no metódo
		List<User> returnListForMethod = new ArrayList<User>();

		String myQuery = "FROM User u WHERE u.cpf = :cpf AND u.password = :password";

		Query query = entityManagger.createQuery(myQuery);
		query.setParameter("cpf", user.getCpf());
		query.setParameter("password", user.getPassword());
		List<User> resultList = (List<User>) query.getResultList();

		if (!resultList.isEmpty()) {
			returnListForMethod.addAll(resultList);
		}

		return returnListForMethod;

	}

	@SuppressWarnings("unchecked")
	public List<User> getByEmail(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		EntityManager entityManagger = super.getEntityManager();

		String emailReceived = user.getEmail();

		// Lista que será retornada no metódo
		List<User> returnListForMethod = new ArrayList<User>();

		// Administrador
		Query query = entityManagger.createQuery("SELECT u FROM Administrador u WHERE u.email = :email");
		query.setParameter("email", emailReceived);
		List<User> resultListAdministrator = (List<User>) query.getResultList();

		if (!resultListAdministrator.isEmpty()) {
			returnListForMethod.addAll(resultListAdministrator);
		}

		// Recepcionista
		Query query2 = entityManagger.createQuery("SELECT u FROM Recepcionista u WHERE u.email = :email");
		query2.setParameter("email", emailReceived);
		List<User> resultListReceptionist = (List<User>) query2.getResultList();

		if (!resultListReceptionist.isEmpty()) {
			returnListForMethod.addAll(resultListReceptionist);
		}

		// Recepcionista
		Query query3 = entityManagger.createQuery("SELECT u FROM Fiscal u WHERE u.email = :email");
		query3.setParameter("email", emailReceived);
		List<User> resultListInspector = (List<User>) query3.getResultList();

		if (!resultListInspector.isEmpty()) {
			returnListForMethod.addAll(resultListInspector);
		}

		return returnListForMethod;

	}

}
