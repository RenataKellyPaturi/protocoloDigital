package br.org.pmc.controll.rest.service.impl;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

import br.org.pmc.model.dao.impl.SecretariaDao;
import br.org.pmc.model.entity.impl.Secretaria;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/secretaria")
public class SecretariaRestService {

		private JPAUtil simpleEntityMananger;
		private SecretariaDao secretariaDao;
		
		private void instanciasClasses(){
			this.simpleEntityMananger = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
			this.secretariaDao = new SecretariaDao(this.simpleEntityMananger.getEntityManager());
			this.simpleEntityMananger.beginTransaction();
		}
		
		
	@POST
	@RolesAllowed({"Administrador","Operador"})
	public Response create(@FormParam("secretaria")String secretarariaJson){
		
		this.instanciasClasses();
		ResponseBuilder responseBuilder = Response.noContent();
		
		try{
			Secretaria secretaria = new Gson().fromJson(secretarariaJson, Secretaria.class);
			
			secretaria.setStatus(Constants.ACTIVE_ENTITY);
			
			if(secretaria.validateEmptyFields() && !this.secretariaDao.secretariaExiste(secretaria)){
				this.secretariaDao.save(secretaria);
				this.simpleEntityMananger.commit();
				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
			}else{
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
			}
			
		}catch(Exception e ){
			this.simpleEntityMananger.rollBack();
			e.getStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}finally {
			this.simpleEntityMananger.close();
		}
		return responseBuilder.build();
	}

	@PUT
	@RolesAllowed({"Administrador","Operador"})
	public Response update(@FormParam("id")String id,
						   @FormParam("nome")String nome){
		
		this.instanciasClasses();
		ResponseBuilder responseBuilder = Response.noContent();
		try{
			Secretaria secretaria = this.secretariaDao.getByPK(Long.parseLong(id));
			if(secretaria != null ){
				secretaria.setNome(nome);
				if(secretaria.validateEmptyFields()){
					this.simpleEntityMananger.commit();
					responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
				}else{
					responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
				}
					
			}
			
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
	@RolesAllowed({"Administrador","Operador"})
	public Response listAll(){
		
		this.instanciasClasses();
		ResponseBuilder responseBuilder = Response.noContent();
		try{
			List<Secretaria > secretarias = this.secretariaDao.findAll();
			if(secretarias != null){
				responseBuilder =ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(secretarias));
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
		
		this.instanciasClasses();
		ResponseBuilder responseBuilder = Response.noContent();
		try{
			Secretaria secretaria = this.secretariaDao.getByPK(Long.parseLong(id));
			if(secretaria != null){
				responseBuilder =ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, 
						JSONUtil.objectToJSON(secretaria));
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
	@Path("/get-secretaria-for-status/{status}")
	@RolesAllowed({"Administrador","Operador"})
	public Response listSecretariaForStatus(@PathParam("status") String status){
		
		this.instanciasClasses();
		ResponseBuilder responseBuilder = Response.noContent();
		try{
			List<Secretaria> secretarias = this.secretariaDao.getSecretariasForStatus(Boolean.parseBoolean(status));
			if(secretarias != null){
				responseBuilder =ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(secretarias));
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
		
		this.instanciasClasses();
		ResponseBuilder responseBuilder = Response.noContent();
		try{
			Secretaria secretaria = this.secretariaDao.getByPK(Long.parseLong(id));
			if(secretaria != null){
				secretaria.setStatus(secretaria.getStatus()==false);
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


}
