package br.org.pmc.controll.rest.service.impl;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/generate-database")
public class GenerateDataBaseRestService {
	
	private JPAUtil simpleEntityMananger;

	@Context
	private HttpServletRequest serveletRequest;

	@GET
	@PermitAll
	public Response listAll() {

		this.simpleEntityMananger = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityMananger.beginTransaction();

		try {
			
			responseBuilder.status(Response.Status.OK);
			responseBuilder.header(Constants.ACCESS_CONTROL_ALLOW_ORIGIN, Constants.ACCESS_CONTROL_ALLOW_ORIGIN_EXTRA);
			responseBuilder.entity("Ok");

		} catch (Exception e) {
			this.simpleEntityMananger.rollBack();
			e.getStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityMananger.close();
		}
		return responseBuilder.build();
	}

}
