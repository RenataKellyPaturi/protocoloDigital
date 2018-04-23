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

import br.org.pmc.model.dao.impl.ServicoDao;
import br.org.pmc.model.entity.impl.Servico;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/servico")
public class ServicoRestService {
	
	private JPAUtil simpleEntityManager;
	private ServicoDao servicoDao;
	
	private void instanciaClasses(){
		
	 this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
	 this.servicoDao = new ServicoDao(this.simpleEntityManager.getEntityManager());
	 this.simpleEntityManager.beginTransaction();
		
	}
	
@POST
@RolesAllowed({"Administrador", "Operador"})
public Response create(@FormParam("nome") String nome,
					   @FormParam("isServicoUrb")String servicoUrb,
					   @FormParam("valorServico")String valorServico){
	
	this.instanciaClasses();
	ResponseBuilder responseBuilder = Response.noContent();

	try{
		Servico servico = new Servico();
		servico.setNome(nome);
		servico.setValor(Float.parseFloat(valorServico));
		servico.setServicoUrb(Boolean.parseBoolean(servicoUrb));
		servico.setStatus(Constants.ACTIVE_ENTITY);
		if(servico.validateEmptyFields() && !this.servicoDao.servicoExistente(nome)){
			this.servicoDao.save(servico);
			this.simpleEntityManager.commit();
			responseBuilder= ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
	}catch(Exception e){
		this.simpleEntityManager.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityManager.close();
	}
	return responseBuilder.build();
}

@PUT
@RolesAllowed({"Administrador", "Operador"})
public Response update(@FormParam("id") String id,
					   @FormParam("nome")String nome,
					   @FormParam("isServicoUrb")String servicoUrb,
					   @FormParam("valorServico")String valorServico){
	this.instanciaClasses();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		Servico  servico = this.servicoDao.getByPK(Long.parseLong(id));
		String oldName = servico.getNome();
		if(servico != null ){
			
			servico.setServicoUrb(Boolean.parseBoolean(servicoUrb));
			servico.setValor(Float.parseFloat(valorServico));
			
			if(servico.validateEmptyFields() && !this.servicoDao.servicoExistente(nome)){
				servico.setNome(nome);
				this.simpleEntityManager.commit();
				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
			}else if (servico.validateEmptyFields() && nome.equals(oldName) ){
				this.simpleEntityManager.commit();
				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
			}else{
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
			}
		}
		
	}catch(Exception e){
		this.simpleEntityManager.rollBack();
		e.getStackTrace();
		responseBuilder =ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityManager.close();
	}
	return responseBuilder.build();
}

@GET
@RolesAllowed({"Administrador", "Operador", "Atendente"})
public Response listAll(){
	
	this.instanciaClasses();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		List<Servico> servicos = this.servicoDao.findAll();
		if(servicos != null){
		 responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(servicos));
			
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
		
	}catch(Exception e){
		this.simpleEntityManager.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityManager.close();
	}
	return responseBuilder.build();
	}


@GET
@Path("/get-servicoUbr-for-status/{status}")
@RolesAllowed({"Administrador","Operador", "Atendente"})
public Response servicoUrbPorStatus(@PathParam("status")String status){
	
	this.instanciaClasses();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		List<Servico> servicos = this.servicoDao.listaServicoSUrb(Boolean.parseBoolean(status));
		if(servicos != null){
		 responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(servicos));
			
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
		
	}catch(Exception e){
		this.simpleEntityManager.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityManager.close();
	}
	return responseBuilder.build();
	}

@GET
@Path("/get-servicos-for-status/{status}")
@RolesAllowed({"Administrador","Operador", "Atendente"})
public Response servicoPorStatus(@PathParam("status")String status){
	
	this.instanciaClasses();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		List<Servico> servicos = this.servicoDao.listaServicosPorStatus(Boolean.parseBoolean(status));
		if(servicos != null){
		 responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(servicos));
			
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
		
	}catch(Exception e){
		this.simpleEntityManager.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityManager.close();
	}
	return responseBuilder.build();
	}

@GET
@Path("/{id}")
@RolesAllowed({"Administrador","Operador", "Atendente"})
public Response listForid(@PathParam("id") String id){
	
	this.instanciaClasses();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		Servico servico = this.servicoDao.getByPK(Long.parseLong(id));
		if(servico != null){
		 responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder, JSONUtil.objectToJSON(servico));
			
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
		
	}catch(Exception e){
		this.simpleEntityManager.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityManager.close();
	}
	return responseBuilder.build();
	}




@DELETE
@Path("/{id}")
@RolesAllowed({"Administrador","Operador"})
public Response delete(@PathParam("id") String id){
	
	this.instanciaClasses();
	ResponseBuilder responseBuilder = Response.noContent();
	
	try{
		Servico servico = this.servicoDao.getByPK(Long.parseLong(id));
		if(servico !=  null){
			servico.setStatus(servico.getStatus() == false);
			this.simpleEntityManager.commit();
			responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
		}else{
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		}
		
	}catch(Exception e ){
		this.simpleEntityManager.rollBack();
		e.getStackTrace();
		responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
	}finally {
		this.simpleEntityManager.close();
	}
	return responseBuilder.build();
}
}


