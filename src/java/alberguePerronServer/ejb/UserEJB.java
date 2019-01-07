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
    
    private static final Logger LOGGER =
            Logger.getLogger("UserEJB.class");
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void createUser(User user) throws CreateException {
        LOGGER.info("UserManager: Creating user.");
        try{
            em.persist(user);
            LOGGER.info("UserManager: User created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception creating user.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }    }

    @Override
    public void updateUser(User user) throws UpdateException {
        LOGGER.info("UserManager: Updating user.");
        try{
            //if(!em.contains(user))em.merge(user);
            em.merge(user);
            em.flush();
            LOGGER.info("UserManager: User updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception updating user.{0}",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }    }

    @Override
    public User findUserById(String id) throws ReadException {
        User user=null;
        try{
            LOGGER.info("UserManager: Finding user by login.");
            user=em.find(User.class, id);
            LOGGER.log(Level.INFO,"UserManager: User found {0}",user.getLogin());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;    }

    @Override
    public void deleteUser(User user) throws DeleteException {
        LOGGER.info("UserManager: Deleting user.");
        try{
            user=em.merge(user);
            em.remove(user);
            LOGGER.info("UserManager: User deleted.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception deleting user.{0}",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }    }

    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> users=null;
        try{
            LOGGER.info("UserManager: Reading all users.");
            users=em.createNamedQuery("findAllUsers").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all users:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;    
   }    
}
