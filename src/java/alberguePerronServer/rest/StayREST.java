/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.rest;

import alberguePerronServer.ejb.StayManagerEJBLocal;
import alberguePerronServer.entity.Stay;
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
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Diego
 */
@Path("stay")
public class StayREST {
    
    /**
    * The logger that will show messages
    */
    private static final Logger LOGGER =
            Logger.getLogger("StayREST.class");
    
    /**
     * The interface of the User that links the REST and the EJB
     */
    @EJB
    private StayManagerEJBLocal ejb;

    /**
     * Creates the stay
     * @param stay Stay the data of the stay to create
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Stay stay) {
        try {
            LOGGER.log(Level.INFO,"StayREST: create stay, ",stay);
            ejb.createStay(stay);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, 
                    "StayREST: Exception creating stay, ",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    
    /**
     * Edits/updates a stay 
     * @param id Integer: The id of the stay to update
     * @param stay Stay: The data of the stay to update
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Stay stay) {
        try {
            LOGGER.log(Level.INFO,"StayREST: update stay, ",stay);
            ejb.updateStay(stay);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "StayREST: Exception updating stay",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    /**
     * Deletes the stay by the selected id
     * @param id Integer: The id of the stay to delete
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO,"StayREST: delete Stay by id, ",id);
            ejb.deleteStay(ejb.findStayById(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "StayREST: Exception deleting stay by id, ",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        } 
    }
    
    /**
     * Finds a stay by ID
     * @param id Integer: The id of the stay to find
     * @return The stay found
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Stay find(@PathParam("id") Integer id) {
        Stay stay=null;
        try {
            LOGGER.log(Level.INFO,"StayREST: find Stay by id, ",id);
            stay=ejb.findStayById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "StayREST: Exception reading stay by id, ",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return stay;
    }

    /**
     * Finds all the stays
     * @return The list of the stays found
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<Stay> findAll() {
        List<Stay> stays=null;
        try {
            LOGGER.log(Level.INFO,"StayREST: find all the stays.");
            stays=ejb.findAllStays();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "StayREST: Exception reading all stays, ",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return stays;
    } 
}
