package br.org.pmc.controll.rest.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.org.pmc.controll.rest.service.MyHttpSessionManager;
import br.org.pmc.model.dao.impl.LogDeAtividadeDAO;
import br.org.pmc.model.entity.impl.LogDeAtividade;
import br.org.pmc.model.entity.impl.User;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/log-de-atividade")
public class LogDeAtividadeRestService {

	private LogDeAtividadeDAO logDeAtividadeDAO;
	private JPAUtil simpleEntityManager;

	@Context
	private HttpServletRequest httpServletRequest;

	@POST
	@PermitAll
	public Response create(@FormParam("descricao") String descricao) {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.logDeAtividadeDAO = new LogDeAtividadeDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager.beginTransaction();

		try {
			
			User user = MyHttpSessionManager.getInstance().getSessionUserLogged(httpServletRequest.getSession());
			
			LogDeAtividade 
				log = new LogDeAtividade();
				log.setDescricao(descricao);
				log.setUser(user);
				log.setDate(new Date());

			this.logDeAtividadeDAO.save(log);
			this.simpleEntityManager.commit();
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

	@GET
	@PermitAll
	public Response listByUser() {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.logDeAtividadeDAO = new LogDeAtividadeDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager.beginTransaction();

		try {
			
			User user = MyHttpSessionManager.getInstance().getSessionUserLogged(httpServletRequest.getSession());
			
			List<LogDeAtividade> logs = this.logDeAtividadeDAO.listRecentsByUser(user);

			if (logs != null) {

				for (LogDeAtividade log : logs) {
					log.setUser(null);
				}

				responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
						JSONUtil.objectToJsonWithTime(logs));

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