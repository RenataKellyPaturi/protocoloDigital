package br.org.pmc.controll.rest.service.impl;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import br.org.pmc.model.dao.impl.FichaProcessoDao;
import br.org.pmc.model.dao.impl.OrgaoMovimentacaoDao;
import br.org.pmc.model.entity.impl.OrgaoMovimentacao;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.MyDateGenerator;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/orgaoMovimentacao")
public class OrgaoMovimentacaoRestService {

	private JPAUtil simpleEntityMananger;
	private FichaProcessoDao fichaProcessoDao;
	private OrgaoMovimentacaoDao orgaoMovimentacaoDao;
	
	private void classesInstancias(){
		
		this.simpleEntityMananger = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.fichaProcessoDao = new FichaProcessoDao(this.simpleEntityMananger.getEntityManager());
		this.orgaoMovimentacaoDao = new OrgaoMovimentacaoDao(this.simpleEntityMananger.getEntityManager());
		this.simpleEntityMananger.beginTransaction();
		
	}
	
	@POST
	@PermitAll
	public Response create(){
		this.classesInstancias();
		ResponseBuilder responseBuilder = Response.noContent();
		
		try{
			OrgaoMovimentacao orgao = new OrgaoMovimentacao();
			orgao.setDataMovimentacao(MyDateGenerator.getCurrentDate());
			this.orgaoMovimentacaoDao.save(orgao);
			
			this.simpleEntityMananger.commit();
			responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
			
		}catch(Exception e ){
			this.simpleEntityMananger.rollBack();
			e.getStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}finally {
			this.simpleEntityMananger.close();
		}
		return responseBuilder.build();
	}
	
	@GET
	@PermitAll
	public Response listAll(){
		this.classesInstancias();
		ResponseBuilder responseBuilder = Response.noContent();
		
		try{
			List<OrgaoMovimentacao> orgaos = this.orgaoMovimentacaoDao.findAll();
			
			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(orgaos));
			
			
		}catch(Exception e ){
			this.simpleEntityMananger.rollBack();
			e.getStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}finally {
			this.simpleEntityMananger.close();
		}
		return responseBuilder.build();
	}
}
