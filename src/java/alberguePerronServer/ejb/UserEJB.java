/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.ejb;

import alberguePerronServer.entity.User;
import alberguePerronServer.exception.CreateException;
import alberguePerronServer.exception.DeleteException;
import alberguePerronServer.exception.ReadException;
import alberguePerronServer.exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Diego
 */
@Stateless
public class UserEJB implements UserManagerEJBLocal{
    
    /**
     * The logger that will show messages
     */
    private static final Logger LOGGER =
            Logger.getLogger("UserEJB.class");
    
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Creates an user with the data sent by the REST
     * @param user User: User data to create
     * @throws CreateException 
     */
    @Override
    public void createUser(User user) throws CreateException {
        LOGGER.info("UserEJB: Creating user.");
        try{
            em.persist(user);
            LOGGER.info("UserEJB: User created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception creating user.",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }    }
    
    /**
     * Update an user with the data sent by the REST
     * @param user User: New data to update
     * @throws UpdateException throws when the update fails
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        LOGGER.info("UserEJB: Updating user.");
        try{
            //if(!em.contains(user))em.merge(user);
            em.merge(user);
            em.flush();
            LOGGER.info("UserEJB: User updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception updating user: ",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }    }

    /**
     * Finds an user by Id
     * @param id String: The id of the user yo search.
     * @return The User found
     * @throws ReadException throws when the read operation fails.
     */
    @Override
    public User findUserById(String id) throws ReadException {
        User user=null;
        try{
            LOGGER.info("UserEJB: Finding user by login.");
            user=em.find(User.class, id);
            if(user!=null){
                LOGGER.log(Level.INFO,"UserEJB: User found ",user.getLogin());
            }
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;    }
    
    /**
     * Deletes an user by id
     * @param user User: The user found by Id   
     * @throws DeleteException throws when the delete operation fails.
     */
    @Override
    public void deleteUser(User user) throws DeleteException {
        LOGGER.info("UserEJB: Deleting user.");
        try{
            user=em.merge(user);
            em.remove(user);
            LOGGER.info("UserEJB: User deleted.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception deleting user.",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }    }

    /**
     * Finds all the users
     * @return a List of all the users 
     * @throws ReadException throws when the read operation fails.
     */
    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> users=null;
        try{
            LOGGER.info("UserEJB: Reading all users.");
            users=em.createNamedQuery("findAllUsers").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception reading all users:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;    
   }    
}
