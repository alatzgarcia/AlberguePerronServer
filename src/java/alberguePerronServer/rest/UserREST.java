/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.rest;

import alberguePerronServer.ejb.UserEJBLocal;
import alberguePerronServer.entity.Pet;
import alberguePerronServer.entity.User;
import alberguePerronServer.exception.CreateException;
import alberguePerronServer.exception.DeleteException;
import alberguePerronServer.exception.ReadException;
import alberguePerronServer.exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

/**
 *
 * @author Nerea JImenez
 */

@Path("users")
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
     * RESTful POST method for creating User objects
     * @param user The user
     */
    @POST
    @Consumes({"application/xml"})
    public void create(User user) {
        LOGGER.log(Level.INFO,"UserRESTful service: create {0}.",user);
        try {
            ejb.createUser(user);
        } catch (CreateException ex) {
            Logger.getLogger(UserREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * RESTful PUT method for updating User objects
     * @param user The user
     */
    @PUT
    @Consumes({"application/xml"})
    public void update(User user) {
        LOGGER.log(Level.INFO,"UserRESTful service: update {0}.",user);
        try {
            if(user.getId()==null){
               ejb.login(user); 
            }else{
               ejb.updateUser(user);
            }
            
        } catch (ReadException ex) {
            Logger.getLogger(UserREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UpdateException ex) {
            Logger.getLogger(UserREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * RESTful DELETE method for deleting users by id
     * @param id The id for the user
     */
    @DELETE
    @Path("{id}")
    //@Consumes({"application/xml"})
    public void delete(@PathParam("id") String id) {
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: delete User by id={0}.",id);
            ejb.deleteUser(ejb.findUserById(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception deleting user by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        } 
    }
    /**
     * RESTful GET method for reading Users by id
     * @param id The id for the user
     * @return The User object 
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml"})
    public User find(@PathParam("id") String id) {
        User user=null;
        try {
            LOGGER.log(Level.INFO,"UserRESTful service: find User by id={0}.",id);
            user=ejb.findUserById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception reading user by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return user;
    }
    /**
     * RESTful GET method for reading all User objects
     * @return A List of User objects
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
    
    @GET
    @Path("login/{login}")
    @Produces({"application/xml"})
    public User findUserByLogin(@PathParam("login") String login) {
        User user=null;
        try {
            LOGGER.log(Level.INFO,"");
            user=ejb.findUserByLogin(login);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserRESTful service: Exception reading users by login, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return user;
    }
  

  
    
    
}
