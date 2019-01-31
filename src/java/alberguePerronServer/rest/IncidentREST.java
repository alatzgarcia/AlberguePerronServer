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
 * IncidentREST class for the AlberguePerronServer application
 * @author Alatz
 */
@Path("incident")
public class IncidentREST {
    
    /**
     * Logger for the class
     */
    private static final Logger LOGGER = Logger.getLogger("incidentrest");
    /**
     * EJB object
     */
    @EJB
    private IncidentEJBLocal ejb;
    
    /**
     * Creates a new incident
     * @param entity incident object
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Incident entity) {
        try {
            entity.setIdNull();
            ejb.createIncident(entity);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates an incident
     * @param entity incident object
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(Incident entity) {
        try {
            ejb.updateIncident(entity);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes an incident by its id
     * @param id id of the incident to delete
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            ejb.deleteIncident(ejb.findIncidentById(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Finds an incident by its id
     * @param id id of the incident
     * @return the incident object
     */
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

    /**
     * Finds all incidents
     * @return the list of incidents
     */
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
