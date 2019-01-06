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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("albergueperronserver.entity.stay")
public class StayFacadeREST {
    private static final Logger LOGGER =
            Logger.getLogger("javafxserverside");
    
    @EJB
    private StayManagerEJBLocal ejb;

    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Stay stay) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: create {0}.",stay);
            ejb.createStay(stay);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, 
                    "UserRESTful service: Exception creating user, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Stay stay) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: update {0}.",stay);
            ejb.updateStay(stay);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception updating user, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: delete User by id={0}.",id);
            ejb.deleteStay(ejb.findStayById(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception deleting user by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        } 
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Stay find(@PathParam("id") Integer id) {
        Stay stay=null;
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.",id);
            stay=ejb.findStayById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception reading user by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return stay;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Stay> findAll() {
        List<Stay> stays=null;
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find all users.");
            stays=ejb.findAllStays();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception reading all users, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return stays;
    }    
}
