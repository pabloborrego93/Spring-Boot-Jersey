package com.pl.controller;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pl.dao.ResearchGroupDAO;
import com.pl.dto.Data;
import com.pl.model.GroupMember;
import com.pl.model.ResearchGroup;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jersey.repackaged.com.google.common.collect.Lists;

@Path("/group")
@Component
@Api(value = "ResearchGroup")
public class ResearchGroupController {
	@Autowired
	private ResearchGroupDAO rgDAO;

	@GET
	@ApiOperation(value = "Devuelve una lista con todos los grupos")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Grupos devueltos correctamente"),
	    @ApiResponse(code = 204, message = "No existen grupos")
	})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		ArrayList<ResearchGroup> rg = new ArrayList<ResearchGroup>(Lists.newArrayList(rgDAO.findAll()));
		if(rg.isEmpty())
			return Response.status(204).build();
		return Response.status(200).entity(rg).build();
	}
	
	@GET
	@ApiOperation(value = "Devuelve una lista con los nombres de los grupos")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Grupos devueltos correctamente"),
	    @ApiResponse(code = 204, message = "No existen grupos")
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/names")
	public Response getAllNames() {
		ArrayList<ResearchGroup> rg = new ArrayList<ResearchGroup>(Lists.newArrayList(rgDAO.findAll()));
		ArrayList<String> nombres = new ArrayList<String>();
		rg.forEach(g->{
			nombres.add(g.getSpanishName());
		});
		if(rg.isEmpty())
			return Response.status(204).build();
		return Response.status(200).entity(nombres).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Devuelve una lista con todos los miembros de un grupo")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Miembros devueltos correctamente"),
	    @ApiResponse(code = 204, message = "No existe el grupo")
	})
	@Path("/{id}/member")
	public Response getMembers(
			@ApiParam(value = "TIC o nombre, en ingles o español", required = true) @PathParam(value = "id") String id) {
		if(rgDAO.searchByString(id) == null)
			return Response.status(204).build(); 
		return Response.status(200).entity(Lists.newArrayList(rgDAO.searchByString(id).getMembers())).build();
	} 
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Inserta un nuevo miembro en un grupo")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Insertado correctamente"),
	    @ApiResponse(code = 204, message = "No existe el grupo")
	})
	@Path("/{id}/member/json")
	public Response addMemberJson(
			@ApiParam(value = "TIC o nombre, en ingles o español", required = true) @PathParam(value = "id") String id, 
			@ApiParam(value = "Miembro nuevo a insertar", required = true) GroupMember gm) {
		ResearchGroup rg = rgDAO.searchByString(id);
		if(rg == null)
			return Response.status(204).build();
		rg.getMembers().add(gm);
		gm.setGrupo(rg);
		rgDAO.save(rg);
		return Response.status(200).entity(gm).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "Inserta un nuevo miembro en un grupo a través de formulario")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ha insertado correctamente"),
	    @ApiResponse(code = 204, message = "No existe el grupo"),
	    @ApiResponse(code = 400, message = "Faltan campos por rellenar")
	})
	@Path("/{id}/member")
	public Response addMember(
			@ApiParam(value = "TIC o nombre, en ingles o español", required = true) @PathParam(value = "id") String id,
			@ApiParam(value = "Nombre", required = true) @FormParam("name") String name,
			@ApiParam(value = "Apellido", required = true) @FormParam("surname") String surname,
			@ApiParam(value = "Nombre cientifico", required = true) @FormParam("scientistName") String scientistName,
			@ApiParam(value = "Departamento", required = true) @FormParam("department") String department,
			@ApiParam(value = "Numero de telefono", required = true) @FormParam("phone") String phone) {
		if(name == null || surname == null || scientistName == null || department == null || phone == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		ResearchGroup rg = rgDAO.searchByString(id);
		if(rg == null)
			return Response.status(204).build();
		GroupMember gm = new GroupMember(0, name, surname, scientistName, department, phone);
		rg.getMembers().add(gm);
		gm.setGrupo(rg);
		rgDAO.save(rg);
		return Response.status(200).entity(gm).build();
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene un grupo pasandole un id")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Grupo devuelto correctamente"),
	    @ApiResponse(code = 204, message = "No existe grupo con ese id")
	})
	@Path("/{id}")
	public Response getOne(
			@ApiParam(value = "TIC o nombre, en ingles o español", required = true) @PathParam(value = "id") String id) {
		ResearchGroup rg = rgDAO.searchByString(id);
		if(rg != null){
			return Response.status(200).entity(rg).build();
		}
		return Response.status(204).build();
	}

	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Borra un grupo pasandole su id")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ha borrado correctamente"),
	    @ApiResponse(code = 204, message = "No existe grupo con ese id")
	})
	@Path("/{id}")
	public Response deleteGroup(
			@ApiParam(value = "TIC o nombre, en ingles o español", required = true) @PathParam(value = "id") String id) {
		ResearchGroup rg = rgDAO.searchByString(id);
		if(rg != null){
			rgDAO.delete(rg);
			return Response.status(200).build();
		}
		return Response.status(204).build();
	} 

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "Inserta un nuevo grupo a través de formulario")
	  @ApiResponses(value = {
		@ApiResponse(code = 200, message = "Se ha creado grupo correctamente"),
		@ApiResponse(code = 204, message = "Ya existe grupo"),
		@ApiResponse(code = 400, message = "Algun parametro no fue introducido")
	})
	public Response addGroupForm(
			@ApiParam(value = "TIC", required = true) @FormParam(value = "tic") String tic, 
			@ApiParam(value = "Nombre en ingles", required = true) @FormParam("name") String name,
			@ApiParam(value = "Nombre en español", required = true) @FormParam("nombre") String nombre) {
		if(tic == null || name == null || nombre == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		if(grupoExistente(tic, name, nombre))
			return Response.status(204).build();
		else{
			ResearchGroup rg = rgDAO.save(new ResearchGroup(tic, name, nombre));
			return Response.status(200).entity(rg).build();
		}
	}
	
	@POST
	@ApiOperation(value = "Inserta un nuevo grupo en formato JSON")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ha creado grupo correctamente"),
	    @ApiResponse(code = 204, message = "Ya existe grupo")
	})
	@Path("/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addGroupJson(
			@ApiParam(value = "Grupo nuevo a insertar", required = true) ResearchGroup rg) {
		if(!grupoExistente(rg.getTic(), rg.getEnglishName(), rg.getSpanishName())){
			rgDAO.save(rg);
			return Response.status(200).entity(rg).build();
		}
		return Response.status(204).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "Actualiza los parametros de un grupo")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ha actualizado correctamente"),
	    @ApiResponse(code = 204, message = "No existe grupo con ese id")
	})
	@Path("/{id}")
	public Response updateGroup(
			@ApiParam(value = "TIC o nombre, en ingles o español", required = true) @PathParam(value = "id") String id, 
			@ApiParam(value = "Nuevo nombre en ingles", required = false) @FormParam("name") String name,
			@ApiParam(value = "Nuevo nombre en español", required = false) @FormParam("nombre") String nombre) {
		ResearchGroup rg = rgDAO.searchByString(id);
		if(rg != null){
			if (name != null)
				rg.setEnglishName(name);
			if (nombre != null)
				rg.setSpanishName(nombre);
			rgDAO.save(rg);
			return Response.status(200).entity(rg).build();
		}
		return Response.status(204).build();
	} 
	
	
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Obtiene los enlaces al dblp de todos los miembros de un grupo a partir de su nombre", response = Data.class)
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Peticion correcta")
	})
	@Path("/url/{name}/{key}")
	public Response getUrl(
			@ApiParam(value = "Nombre del grupo", required = true) @PathParam(value="name") String name,
			@ApiParam(value = "API key", required = false) @PathParam(value="key") String key) {
		ResearchGroup rg = rgDAO.searchByString(name);
		Data data = new Data();
		if(rg != null){
			RestTemplate restTemplate = new RestTemplate();
			ArrayList<GroupMember> mg = Lists.newArrayList(rg.getMembers());
			mg.forEach(m->{
				Data data_aux = restTemplate.getForObject("http://sistedes.services.rcis.governify.io/api/v1/users?q="
						+m.getScientistName()+"&apikey="
						+key, Data.class);
				if(!data_aux.getData().isEmpty())
					data.getData().add(data_aux.getData().get(0));
			});
		}
		if(data.getData().isEmpty())
			return Response.status(204).build();
	    return Response.status(200).entity(data).build();
	}
	
	
	private boolean grupoExistente(String tic, String englishName, String spanishName){
		boolean existe = false;
		if(rgDAO.searchByString(tic) != null) existe = true;
		if(rgDAO.searchByString(englishName) != null) existe = true;
		if(rgDAO.searchByString(spanishName) != null) existe = true;
		return existe;
	}
}
