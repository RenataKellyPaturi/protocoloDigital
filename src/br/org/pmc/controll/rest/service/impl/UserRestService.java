package br.org.pmc.controll.rest.service.impl;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.org.pmc.model.dao.impl.UserDAO;
import br.org.pmc.model.dao.impl.UserProfileDAO;
import br.org.pmc.model.entity.impl.User;
import br.org.pmc.model.entity.impl.UserProfile;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/users")
public class UserRestService {

	private UserDAO userDAO;
	private UserProfileDAO userProfileDAO;
	private JPAUtil simpleEntityManager;

	@Context
	private HttpServletRequest servletRequest;

	@GET
	@RolesAllowed({ "Administrador" })
	public Response listAll() {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.userDAO = new UserDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();
		List<User> users;

		this.simpleEntityManager.beginTransaction();

		try {

			users = this.userDAO.findAll();

			if (users != null) {

				for (User user : users) {
					user.setUserProfiles(null);
					user.setLogsDeAtividades(null);
				}

				responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
						JSONUtil.objectToJSON(users));

			} else {
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
			}

		} catch (Exception e) {
			this.simpleEntityManager.rollBack();
			e.printStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);

		} finally {
			this.simpleEntityManager.close();
		}

		return responseBuilder.build();
	}
	
	@GET
	@Path("/{matricula}")
	@PermitAll
	public Response byMatricula(@PathParam("matricula") String matricula) {
		
		//@RolesAllowed({ "Administrador", "Operador CEPED" })

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.userDAO = new UserDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager.beginTransaction();

		try {

			User user = this.userDAO.getByMatricula(Long.parseLong(matricula));
			
			if( user == null ){
			
				user = new User();
				responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,JSONUtil.objectToJSON(user));
			}else{
				
				user.setLogsDeAtividades(null);
				for (UserProfile profile : user.getUserProfiles()) {
					profile.setUsers(null);
				}
				responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,JSONUtil.objectToJSON(user));
			}

		} catch (Exception e) {
			this.simpleEntityManager.rollBack();
			e.printStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);

		} finally {
			this.simpleEntityManager.close();
		}

		return responseBuilder.build();
	}
	
	@POST
	@RolesAllowed({ "Administrador", "Operador CEPED" })
	public Response update(@FormParam("profile") String idUserProfile, @FormParam("name") String name, @FormParam("cpf") String cpf, @FormParam("matricula") String matricula) {
		
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.userDAO = new UserDAO(this.simpleEntityManager.getEntityManager());
		this.userProfileDAO = new UserProfileDAO(this.simpleEntityManager.getEntityManager());
		
		try {
			
			this.simpleEntityManager.beginTransaction();

			//Verificar se já não existe
			User userForVerification = this.userDAO.getByMatricula(Long.parseLong(matricula));

			if( userForVerification == null ){
				
				
			
				User 
					user = new User();
					user.setCpf(cpf);
					user.setId(Long.parseLong(matricula));
					user.setEmail("");
					user.setName(name);
					user.setStatus(Constants.ACTIVE_ENTITY);
					
				this.userDAO.save(user);
				
				UserProfile 
					userProfile = this.userProfileDAO.getByPK(Long.parseLong(idUserProfile));
					userProfile.getUsers().add(user);
				
				this.userProfileDAO.update(userProfile);
				
				this.simpleEntityManager.commit();
			}
			
			responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

		} catch (Exception e) {
			this.simpleEntityManager.rollBack();
			e.printStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityManager.close();
		}

		return responseBuilder.build();
	}

}