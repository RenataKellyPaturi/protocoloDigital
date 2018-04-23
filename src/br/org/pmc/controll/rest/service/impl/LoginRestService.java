package br.org.pmc.controll.rest.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

import br.org.pmc.controll.rest.service.MyHttpSessionManager;
import br.org.pmc.model.dao.impl.LogDeAtividadeDAO;
import br.org.pmc.model.dao.impl.UserDAO;
import br.org.pmc.model.entity.impl.LogDeAtividade;
import br.org.pmc.model.entity.impl.User;
import br.org.pmc.model.entity.impl.UserProfile;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.StringUtil;

@Path("/login")
public class LoginRestService {

	private UserDAO userDao;
	private JPAUtil simpleEntityManager;
	private LogDeAtividadeDAO logDeAtividadeDAO;

	@Context
	private HttpServletRequest servletRequest;

	@GET
	@Path("/get-user-loged")
	@PermitAll
	public Response getUserLoged() {

		// Captura a session do servletRequest do contexto de request atual
		HttpSession session = servletRequest.getSession();

		// Captura o usuario presente na session em contexto
		User userLoged = MyHttpSessionManager.getInstance().getSessionUserLogged(session);

		ResponseBuilder rb = Response.noContent();

		/*
		 * Se não existir nenhum usuário na session, abortar todas as requests
		 */
		if (userLoged == null) {

			rb.status(Response.Status.UNAUTHORIZED);
			rb.header(Constants.ACCESS_CONTROL_ALLOW_ORIGIN, Constants.ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA);

		} else if (userLoged.getStatus() == false) {

			rb.status(Response.Status.UNAUTHORIZED);
			rb.header(Constants.ACCESS_CONTROL_ALLOW_ORIGIN, Constants.ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA);

		} else {

			User userToJson = new User();
				userToJson.setId(userLoged.getId());
				userToJson.setName(userLoged.getName());
				userToJson.setEmail(userLoged.getEmail());
				userToJson.setStatus(userLoged.getStatus());
			
				List<UserProfile> userProfiles = userLoged.getUserProfiles();
				for (UserProfile userProfile : userProfiles) {
					System.out.println(userProfile.getName());
					userProfile.setUsers(null);
				}
				
				userToJson.setUserProfiles(userLoged.getUserProfiles());

			String json = new Gson().toJson(userToJson);

			rb.status(Response.Status.ACCEPTED).entity(json);
			rb.type(MediaType.APPLICATION_JSON);
			rb.header(Constants.ACCESS_CONTROL_ALLOW_ORIGIN, Constants.ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA);

		}

		return rb.build();

	}

	@GET
	@Path("/logout")
	@PermitAll
	public Response logOut() {

		// Captura a session do servletRequest do contexto de request atual
		HttpSession httpSession = servletRequest.getSession();

		// Destroi a session
		MyHttpSessionManager.getInstance().destoySessionUserLogged(httpSession);

		// Response
		ResponseBuilder rb = Response.noContent();
		rb.status(Response.Status.OK);
		rb.header(Constants.ACCESS_CONTROL_ALLOW_ORIGIN, Constants.ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA);

		return rb.build();

	}

	@POST
	@PermitAll
	public Response onPostRequest(@FormParam("password") String password, @FormParam("cpf") String cpf) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		User user = new User();
		user.setCpf(cpf);
		user.setPassword( StringUtil.SHA1(password));

		// Verificar existencia
		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.userDao = new UserDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder rb = Response.noContent();

		HttpSession session = servletRequest.getSession();

		try {

			this.simpleEntityManager.beginTransaction();

			List<User> resultList = this.userDao.getByCpfAndPassword(user);

			if (resultList.isEmpty()) {

				MyHttpSessionManager.getInstance().destoySessionUserLogged(session);
				rb = rb.header(Constants.ACCESS_CONTROL_ALLOW_ORIGIN, Constants.ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA);
				rb = rb.status(Response.Status.UNAUTHORIZED);

			} else {
				
				User userLoged = resultList.get(0);
				MyHttpSessionManager.getInstance().setSessionUserLogged(session, userLoged);
				rb = rb.header(Constants.ACCESS_CONTROL_ALLOW_ORIGIN, Constants.ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA);
				rb = rb.status(Response.Status.OK);
				
				//Registrar Atividade ...
				LogDeAtividade 
					log = new LogDeAtividade();
					log.setDescricao("Realizou Login no Sistema");
					log.setUser(userLoged);
					log.setDate(new Date());
				this.logDeAtividadeDAO = new LogDeAtividadeDAO(this.simpleEntityManager.getEntityManager());
				this.logDeAtividadeDAO.save(log);
				this.simpleEntityManager.commit();
				// ../ Registrar Atividade ...
				
			}

		} catch (Exception e) {

			this.simpleEntityManager.rollBack();

			MyHttpSessionManager.getInstance().destoySessionUserLogged(session);

			rb = rb.header(Constants.ACCESS_CONTROL_ALLOW_ORIGIN, Constants.ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA);
			rb = rb.type(MediaType.TEXT_PLAIN);
			rb = rb.status(Response.Status.INTERNAL_SERVER_ERROR);

		} finally {
			this.simpleEntityManager.close();
		}

		return rb.build();

	}

}
