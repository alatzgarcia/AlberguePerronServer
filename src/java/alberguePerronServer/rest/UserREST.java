/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.rest;

import alberguePerronServer.ejb.UserEJBLocal;
import alberguePerronServer.entity.Privilege;
import alberguePerronServer.entity.User;
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
 * @author Nerea JImenez
 */

@Path("user")
public class UserREST {

/**
     * Logger for class methods.
     */
    private static final Logger LOGGER =
            Logger.getLogger("alberguPerronServer");
    @PersistenceContext(unitName = "AlberguePerronServerPU")
    /**
     * EJB reference for business logic object.
     */
    @EJB
    private UserEJBLocal ejb;
    /**
     * RESTful POST method for creating {@link User} objects from XML representation.
     * @param user The object containing user data.
     */
    @POST
    @Consumes({"application/xml"})
    public void create(User user) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: create {0}.",user);
            ejb.createUser(user);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, 
                    "UserRESTful service: Exception creating user, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * RESTful PUT method for updating {@link User} objects from XML representation.
     * @param user The object containing user data.
     */
    @PUT
    @Consumes({"application/xml"})
    public void update(User user) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: update {0}.",user);
            ejb.updateUser(user);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception updating user, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * RESTful DELETE method for deleting {@link User} objects from id.
     * @param id The id for the object to be deleted.
     */
    @DELETE
    @Path("{id}")
    //@Consumes({"application/xml", "application/json"})
    public void delete(@PathParam("id") String id) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: delete User by id={0}.",id);
            ejb.deleteUser(ejb.findUserByLogin(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception deleting user by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        } 
    }
    /**
     * RESTful GET method for reading {@link User} objects through an XML representation.
     * @param id The id for the object to be read.
     * @return The User object containing data. 
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml"})
    public User find(@PathParam("id") String id) {
        User user=null;
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.",id);
            user=ejb.findUserByLogin(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception reading user by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return user;
    }
    /**
     * RESTful GET method for reading all {@link User} objects through an XML representation.
     * @return A List of User objects containing data.
     */
    @GET
    @Produces({"application/xml"})
    public List<User> findAll() {
        List<User> users=null;
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find all users.");
            users=ejb.findAllUsers();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception reading all users, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return users;
    }
    
    
    
}
