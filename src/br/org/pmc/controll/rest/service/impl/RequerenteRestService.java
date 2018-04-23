package br.org.pmc.controll.rest.service.impl;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.faces.render.ResponseStateManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.gson.Gson;

import br.org.pmc.model.dao.impl.RequerenteDao;
import br.org.pmc.model.entity.impl.Requerente;
import br.org.pmc.model.jpa.impl.JPAUtil;
import br.org.pmc.util.Constants;
import br.org.pmc.util.JSONUtil;
import br.org.pmc.util.ResponseBuilderGenerator;

@Path("/requerente")
public class RequerenteRestService {

	private JPAUtil simpleEntityMananger;
	private RequerenteDao requerenteDao;

	private void instanciaClasse() {
		this.simpleEntityMananger = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.requerenteDao = new RequerenteDao(this.simpleEntityMananger.getEntityManager());
		this.simpleEntityMananger.beginTransaction();
	}

	@POST
	@RolesAllowed({ "Administrador", "Operador" })
	public Response create( @FormParam("requerente") String requerenteJson){

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();

		try {
			Requerente requerente = new Gson().fromJson(requerenteJson, Requerente.class);
			
				if(requerente.validateEmptyFields() && !this.requerenteDao.requerenteExistente(requerente) ){
					requerente.setStatus(Constants.ACTIVE_ENTITY);
					this.requerenteDao.save(requerente);
					this.simpleEntityMananger.commit();
					responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
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

	@GET
	@RolesAllowed({ "Administrador", "Operador" })
	public Response listAll() {

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();
		try {
			List<Requerente> requerentes = this.requerenteDao.findAll();
			if (requerentes != null) {
				responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
						JSONUtil.objectToJSON(requerentes));
			} else {
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
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
	@Path("/{id}")
	@RolesAllowed({ "Administrador", "Operador" })
	public Response listForId(@PathParam("id") String id) {

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();
		try {
			Requerente requerente = this.requerenteDao.getByPK(Long.parseLong(id));
			if (requerente != null) {
				responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
						JSONUtil.objectToJSON(requerente));
			} else {
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
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
	public Response update(@PathParam("id") String id, @FormParam("requerente") String requerentejson) {

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();
		 
		try {
			Requerente requerenteAtual = this.requerenteDao.getByPK(Long.parseLong(id));
				
			requerenteAtual= new Gson().fromJson(requerentejson, Requerente.class);
				if(requerenteAtual != null){
					if(requerenteAtual.validateEmptyFields()){
						if(this.requerenteDao.requerenteExistente(requerenteAtual)){
							this.requerenteDao.update(requerenteAtual);
							this.simpleEntityMananger.commit();
							responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
						}else{
							responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
						}
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

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "Administrador", "Operador" })
	public Response delete(@PathParam("id") String id) {

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();

		try {
			Requerente requerente = this.requerenteDao.getByPK(Long.parseLong(id));
			if (requerente != null) {
				requerente.setStatus(requerente.getStatus() == false);
				this.simpleEntityMananger.commit();
				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
			} else {
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
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
	@Path("/get-requerentes-for-status/{status}")
	@RolesAllowed({ "Administrador", "Operador" })
	public Response listRequerenteForStatus(@PathParam("status") String status) {

		this.instanciaClasse();
		ResponseBuilder responseBuilder = Response.noContent();
		try {
			List<Requerente> requerentes = this.requerenteDao.listRequerentePorStatus(Boolean.parseBoolean(status));
			if (requerentes != null) {
				responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
						JSONUtil.objectToJSON(requerentes));
			} else {
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
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
}
