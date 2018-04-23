package br.org.pmc.controll.rest.service.impl;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.GsonBuilder;

import br.org.pmc.model.dao.impl.FolhaRequerimentoDao;
import br.org.pmc.model.entity.impl.EnumSituacaoStatus;
import br.org.pmc.model.entity.impl.FolhaRequerimento;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/folha_requerimento")
public class FolhaRequerimentoRestService {
	
	private JPAUtil simpleEntityMananger;
	private FolhaRequerimentoDao folhaRequerimentoDao;
	
	
	private void instanciaClasse(){
		this.simpleEntityMananger = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.folhaRequerimentoDao = new FolhaRequerimentoDao(this.simpleEntityMananger.getEntityManager());
		this.simpleEntityMananger.beginTransaction();
	}  

	@POST
	@RolesAllowed({ "Administrador", "Operador" })
	public Response create(@FormParam("requerimento") String requerimentoJson) {

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();
		try {
			FolhaRequerimento requerimento = new GsonBuilder().setDateFormat("yyyy-dd-MM HH:mm:ss").create().fromJson(requerimentoJson, FolhaRequerimento.class);
			
			if (requerimento != null) {
			if(!this.folhaRequerimentoDao.processoAbertoFolhaRequerimento(requerimento) && requerimento.validateEmptyFields()){
				requerimento.setStatus(Constants.ACTIVE_ENTITY);
				this.folhaRequerimentoDao.save(requerimento);
				this.simpleEntityMananger.commit();
				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
	 			}
			} else {
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
				System.out.println(" requisicao nula");
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

	@POST
	@Path("/{id}")
	@RolesAllowed({ "Administrador", "Operador" })
	public Response update( @PathParam("id")Long id ,@FormParam("requerimento") String requerimentoJson) {

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();
		
		try {
			FolhaRequerimento requerimentoAtual = this.folhaRequerimentoDao.getByPK(id);
			FolhaRequerimento requerimento = new GsonBuilder().setDateFormat("yyyy-dd-MM HH:mm:ss").create().fromJson(requerimentoJson, FolhaRequerimento.class);

			if(requerimento.validateEmptyFields() && requerimento != null){
				if(this.objetosIguais(requerimentoAtual, requerimento) == true){
					this.folhaRequerimentoDao.update(requerimento);
					this.simpleEntityMananger.commit();
					responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
				}else if (!this.folhaRequerimentoDao.processoAbertoFolhaRequerimento(requerimento)){
					this.folhaRequerimentoDao.update(requerimento);
					this.simpleEntityMananger.commit();
					responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
				}else{
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
@RolesAllowed({"Administrador","Operador"})
public Response listAll(){
	
	this.instanciaClasse();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		List<FolhaRequerimento> folhas = this.folhaRequerimentoDao.findAll();
		if(folhas != null){
			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(folhas));
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
		FolhaRequerimento folha = this.folhaRequerimentoDao.getByPK(Long.parseLong(id));
		if(folha != null){
			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(folha));
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
@Path("/get-folhas-by-status-processo/{status}")
@RolesAllowed({"Administrador","Operador"})
public Response buscaFichasPorStatusProcesso(@PathParam("status")String status){
	
	this.instanciaClasse();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		List<FolhaRequerimento> folhasProcesso = this.folhaRequerimentoDao.listaFichaProcessosPorStatus((EnumSituacaoStatus.values()[Integer.parseInt(status)]));
		if(folhasProcesso != null){
			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(folhasProcesso));
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
@Path("/get-folhas-by-status-requerimentos/{status}")
@RolesAllowed({"Administrador","Operador"})
public Response buscaFichasPorStatusRequerimentos(@PathParam("status")String status){
	
	this.instanciaClasse();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		List<FolhaRequerimento> folhasProcesso = this.folhaRequerimentoDao.listaFichaRequerimentosPorStatus(Boolean.parseBoolean(status));
		if(folhasProcesso != null){
			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(folhasProcesso));
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
@RolesAllowed({"Administrador","Operador"})
public Response delete(@PathParam("id")String id){
	
	this.instanciaClasse();
	ResponseBuilder responseBuilder = Response.noContent();
	try{
		FolhaRequerimento folha = this.folhaRequerimentoDao.getByPK(Long.parseLong(id));
		if(folha != null){
			folha.setStatus(folha.getStatus()==false);
			this.simpleEntityMananger.commit();
			responseBuilder =ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
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

private Boolean objetosIguais( FolhaRequerimento atual,FolhaRequerimento novo ){
	
	if(atual.getNumeroProtocolo() == novo.getNumeroProtocolo()){
		return true;
	}else{
	return false;
	}
}

}
