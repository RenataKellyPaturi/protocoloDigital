	package br.org.pmc.controll.rest.service.impl;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.GsonBuilder;

import br.org.pmc.model.dao.impl.FichaProcessoDao;
import br.org.pmc.model.dao.impl.OrgaoMovimentacaoDao;
import br.org.pmc.model.entity.impl.FichaProcesso;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.ResponseBuilderGenerator;


@Path("/processo")
public class FichaProcessoRestService {

	@Context
	private HttpServletRequest servletRequest;
	
	private JPAUtil simpleEntityMananger;
	private FichaProcessoDao fichaProcessoDao;

	private void instanciaClasse() {
		this.simpleEntityMananger = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.fichaProcessoDao = new FichaProcessoDao(this.simpleEntityMananger.getEntityManager());
		this.simpleEntityMananger.beginTransaction();
	}
	
	@POST
//	@RolesAllowed({ "Administrador", "Operador" })
	@PermitAll
	public Response create(@FormParam("processo") String processoJson){

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();

		try {
			FichaProcesso processo = new GsonBuilder().setDateFormat("yyyy-dd-MM HH:mm:ss")
					.create().fromJson(processoJson,  FichaProcesso.class);
			
					if(processo != null){
						if(	!this.fichaProcessoDao.fichaProcessoExistente(processo) && processo.validateEmptyFields()){
							processo.setStatus(Constants.ACTIVE_ENTITY);
							this.fichaProcessoDao.save(processo);
							this.simpleEntityMananger.commit();
							responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
						}
					}else{
						responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
					}
			
			}catch (Exception e) {
			this.simpleEntityMananger.rollBack();
			e.getStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
			} finally {
			this.simpleEntityMananger.close();
		}
		return responseBuilder.build();
		}

	@POST
	@Path("/{id}")
//	@RolesAllowed({ "Administrador", "Operador" })
	@PermitAll
	public Response update(@PathParam("id")String id, @FormParam("processo") String processoJson) {

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();
		try {
			FichaProcesso processoAtual = this.fichaProcessoDao.getByPK(Long.parseLong(id));	
			FichaProcesso atualizacaoProcesso = new GsonBuilder().setDateFormat("yyyy-dd-MM HH:mm:ss")
					.create().fromJson(processoJson,  FichaProcesso.class);
			

			 if(atualizacaoProcesso != null && atualizacaoProcesso.validateEmptyFields()){
				 if(this.objetosIguais(processoAtual, atualizacaoProcesso)){
					 this.fichaProcessoDao.update(atualizacaoProcesso);
					 this.simpleEntityMananger.commit();
					 responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
				 }else if(this.fichaProcessoDao.fichaProcessoExistente(atualizacaoProcesso)){
					 this.fichaProcessoDao.update(atualizacaoProcesso);
					 this.simpleEntityMananger.commit();
					 responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
				 } else{
					 responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
				 }
			 }
		} catch (Exception e) {
			this.simpleEntityMananger.rollBack();
			e.getStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityMananger.close();
		}
		return responseBuilder.build();
	}

@GET
//@RolesAllowed({"Administrador","Operador"})
@PermitAll
public Response listAll(){
	
	this.instanciaClasse();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		List<FichaProcesso> fichasProcessos = this.fichaProcessoDao.findAll();
		if(fichasProcessos != null ){
			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, 
					JSONUtil.objectToJSON(fichasProcessos));
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
	}catch(Exception e){
		this.simpleEntityMananger.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityMananger.close();
	}
	return responseBuilder.build();
}


//@GET
//@Path("/get-fichas-for-status-processo/{status}")
//@RolesAllowed({"Administrador","Operador"})
//public Response listFichasPorStatusProcesso(@PathParam("status")String status){
//	this.instanciaClasse();
//	ResponseBuilder responseBuilder = Response.noContent();
//	
//	try{
//		
//		List<FichaProcesso> fichasProcessos = this.fichaProcessoDao.listFichasPorStatusProcesso((EnumSituacaoStatus.values()[Integer.parseInt(status)]));
//		if(fichasProcessos != null ){
//			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
//					JSONUtil.objectToJSON(fichasProcessos));
//		}else{
//			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
//		}
//	}catch(Exception e){
//		this.simpleEntityMananger.rollBack();
//		e.getStackTrace();
//		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
//	}finally {
//		this.simpleEntityMananger.close();
//	}
//	return responseBuilder.build();
//}

@GET
@Path("/get-fichas-for-status/{status}")
@RolesAllowed({"Administrador","Operador"})
public Response listFichasPorStatus(@PathParam("status")String status){
	this.instanciaClasse();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		List<FichaProcesso> fichasProcessos = this.fichaProcessoDao.getProcessoForStatus(Boolean.parseBoolean(status));
		
		if(fichasProcessos != null ){
			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(fichasProcessos));
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
	}catch(Exception e){
		this.simpleEntityMananger.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityMananger.close();
	}
	return responseBuilder.build();
}
@GET
@Path("/{id}")
@RolesAllowed({"Administrador","Operador"})
public Response listForId(@PathParam("id")String id){
	
	this.instanciaClasse();
	ResponseBuilder responseBuilder = Response.noContent();
	try{
		FichaProcesso processo = this.fichaProcessoDao.getByPK(Long.parseLong(id));
		if(processo != null){
			responseBuilder =ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, 
					JSONUtil.objectToJSON(processo));
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
		
	}catch(Exception e){
		this.simpleEntityMananger.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityMananger.close();
	}
	return responseBuilder.build();
}


@DELETE
@Path("/{id}")
@RolesAllowed({ "Administrador", "Operador" })
public Response delete(@PathParam("id")String id){
	

	this.instanciaClasse();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		FichaProcesso fichasProcesso = this.fichaProcessoDao.getByPK(Long.parseLong(id));
		if(fichasProcesso != null ){
			fichasProcesso.setStatus(fichasProcesso.getStatus()==false);
			this.simpleEntityMananger.commit();
			responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
	}catch(Exception e){
		this.simpleEntityMananger.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityMananger.close();
	}
	return responseBuilder.build();
}

private Boolean objetosIguais( FichaProcesso atual,FichaProcesso novo ){
	
	if(atual.getNumeroProcesso() == novo.getNumeroProcesso()){
		return true;
	}else{
	return false;
	}
}
}
