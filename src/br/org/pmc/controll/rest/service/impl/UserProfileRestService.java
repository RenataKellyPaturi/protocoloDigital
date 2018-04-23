package br.org.pmc.controll.rest.service.impl;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.org.pmc.model.dao.impl.UserProfileDAO;
import br.org.pmc.model.entity.impl.UserProfile;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/user-profile")
public class UserProfileRestService {

	private UserProfileDAO userProfileDAO;
	private JPAUtil simpleEntityManager;

	@Context
	private HttpServletRequest httpServletRequest;

	@GET
	@PermitAll
	public Response listByUser() {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.userProfileDAO = new UserProfileDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager.beginTransaction();

		try {

			List<UserProfile> profiles = this.userProfileDAO.findAll();

			if (profiles != null) {

				for (UserProfile profile : profiles) {
					profile.setUsers(null);
				}
				responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
						JSONUtil.objectToJsonWithTime(profiles));

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

}