/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.rest;

import alberguePerronServer.ejb.IncidentEJBLocal;
import alberguePerronServer.entity.Incident;
import alberguePerronServer.exception.CreateException;
import alberguePerronServer.exception.DeleteException;
import alberguePerronServer.exception.ReadException;
import alberguePerronServer.exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author 2dam
 */
@Path("incident")
public class IncidentREST {
    
    private static final Logger LOGGER = Logger.getLogger("incidentrest");
    @EJB
    private IncidentEJBLocal ejb;
    
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Incident entity) {
        try {
            ejb.createIncident(entity);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Incident entity) {
        try {
            ejb.updateIncident(entity);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            ejb.deleteIncident(ejb.findIncidentById(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Incident find(@PathParam("id") Integer id) {
        Incident incident = null;
        try {
            incident = ejb.findIncidentById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return incident;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<Incident> findAll() {
        List<Incident> incidents = null;
        try {
            incidents = ejb.findAllIncidents();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return incidents;
    }
}
