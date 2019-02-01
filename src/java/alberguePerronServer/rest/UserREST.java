/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.rest;

import alberguePerronServer.ejb.UserManagerEJBLocal;
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
@Path("users")
public class UserREST{
    /**
     * The logger that will show messages
     */
    private static final Logger LOGGER =
            Logger.getLogger("UserREST.class");
    
    /**
     * The interface of the User that links the REST and the EJB
     */
    @EJB
    private UserManagerEJBLocal ejb;
    
    /**
     * Creates a new user with the inserted data
     * @param user User: The user data that will be created
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User user) {
        try {
            LOGGER.log(Level.INFO,"UserREST: creating user, ",user.getId());
            ejb.createUser(user);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, 
                    "UserRESTful: user creation exception",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    
    /**
     * Updates an user by ID
     * @param id String: The id by which user is searched
     * @param user User: The data of the update of the user.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(User user) {
        try {
            LOGGER.log(Level.INFO,"UserREST: Updating user, ",user);
            ejb.updateUser(user);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserREST: user updating exception",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    
    /**
     * Deletes an user by ID
     * @param id String: The id by which user is searched.
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        try {
            LOGGER.log(Level.INFO,"UserREST: Deleting user, ",id);
            ejb.deleteUser(ejb.findUserById(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserREST: user deleting exception",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }    }

    /**
     * Find a single user by an id
     * @param id String: The id by which user is searched
     * @return The data of the user that is found.
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") String id) {
        User user=null;
        try {
            LOGGER.log(Level.INFO,"UserREST: Finding user, ",id);
            user=ejb.findUserById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserREST: finding user by id exception ",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return user;
    }
    
    /**
     * Finds all the user
     * @return List of users found
     */
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAll() {
        List<User> users=null;
        try {
            LOGGER.log(Level.INFO,"UserREST: Find all users.");
            users=ejb.findAllUsers();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserREST: finding al users exception",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return users;
    }
    
    @GET
    @Path("privilege/{privilege}")
    @Produces({"application/xml"})
    public List<User> findByPrivilege(@PathParam("privilege") Privilege privilege) {
        List<User> users=null;
        try{
            LOGGER.log(Level.INFO,"UserREST: Find all users by privilege.");
            users=ejb.findAllByPrivilege(privilege);
        }catch(ReadException ex){
            LOGGER.log(Level.SEVERE,
                    "UserREST: finding all users exception",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return users;
    }
}
