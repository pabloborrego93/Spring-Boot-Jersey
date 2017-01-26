package com.pl.controller;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pl.dao.GroupMemberDAO;
import com.pl.model.GroupMember;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jersey.repackaged.com.google.common.collect.Lists;

@Path("/member")
@Component
@Api(value = "GroupMember")
public class GroupMemberController {
	
	@Autowired
	private GroupMemberDAO gmDAO;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Devuelve todos los miembros existentes", response = GroupMember.class)
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Lista de miembros"),
	    @ApiResponse(code = 204, message = "No existen miembros")
	})
	public Response getAll() {
		ArrayList<GroupMember> gm = new ArrayList<GroupMember>(Lists.newArrayList(gmDAO.findAll()));
		if(gm.isEmpty())
			return Response.status(204).build();
		return Response.status(200).entity(gm).build();
	}

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Devuelve un miembro pasandole su id", response = GroupMember.class)
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Devuelve el miembro con el id introducido"),
	    @ApiResponse(code = 204, message = "No existe miembro con ese id")
	})
	@Path("/{id}")
	public GroupMember getOne(
			@ApiParam(value = "ID para buscar miembro", required = true) @PathParam(value="id") Integer id) {
		return gmDAO.findOne(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "Actualiza los parametros de un miembro")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ha actualizado correctamente"),
	    @ApiResponse(code = 204, message = "No existe miembro con ese id")
	})
	@Path("/{id}")
	public Response updateOne(
			@ApiParam(value = "ID para actualizar el miembro", required = true) @PathParam(value = "id") Integer id, 
			@ApiParam(value = "Nombre", required = false) @FormParam("name") String name,
			@ApiParam(value = "Apellido", required = false) @FormParam("surname") String surname,
			@ApiParam(value = "Nombre cientifico", required = false) @FormParam("scientistName") String scientistName,
			@ApiParam(value = "Departamento", required = false) @FormParam("department") String department,
			@ApiParam(value = "Numero de telefono", required = false) @FormParam("phone") String phone) {
		if(gmDAO.exists(id)){
			GroupMember gm = gmDAO.findOne(id);
			if(name != null) gm.setName(name);
			if(surname != null) gm.setSurname(surname);
			if(scientistName != null) gm.setScientistName(scientistName);
			if(department != null) gm.setDepartment(department);
			if(phone != null) gm.setPhone(phone);
			gmDAO.save(gm);
			return Response.status(200).entity(gm).build();
		}
		return Response.status(204).build();
	}
	
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Borra un miembro pasandole su id")
	  @ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se borra el miembro correctamente"),
	    @ApiResponse(code = 204, message = "No existe miembro con ese id")
	})
	@Path("/{id}")
	public Response deleteOne(
			@ApiParam(value = "ID para borrar miembro", required = true) @PathParam(value = "id") Integer id) {
		if(gmDAO.exists(id)){
			gmDAO.delete(gmDAO.findOne(id));
			return Response.status(200).build();
		}
		return Response.status(204).build();
	}
	
}
