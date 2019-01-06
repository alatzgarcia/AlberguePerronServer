/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.ejb;

import alberguePerronServer.entity.Stay;
import alberguePerronServer.exception.CreateException;
import alberguePerronServer.exception.DeleteException;
import alberguePerronServer.exception.ReadException;
import alberguePerronServer.exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author Diego
 */
public class StayEJB implements StayManagerEJBLocal{
    
    private static final Logger LOGGER =
            Logger.getLogger("javafxserverside");
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void createStay(Stay stay) throws CreateException {
        LOGGER.info("UserManager: Creating user.");
        try{
            em.persist(stay);
            LOGGER.info("UserManager: User created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception creating user.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateStay(Stay stay) throws UpdateException {
        LOGGER.info("UserManager: Updating user.");
        try{
            //if(!em.contains(user))em.merge(user);
            em.merge(stay);
            em.flush();
            LOGGER.info("UserManager: User updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception updating user.{0}",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public List<Stay> findAllStays() throws ReadException {
        List<Stay> stays=null;
        try{
            LOGGER.info("UserManager: Reading all users.");
            stays=em.createNamedQuery("findAllUsers").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception reading all users:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return stays;
    }

    @Override
    public Stay findStayById(Integer id) throws ReadException {
        Stay stay=null;
        try{
            LOGGER.info("UserManager: Finding user by login.");
            stay=em.find(Stay.class, id);
            LOGGER.log(Level.INFO,"UserManager: User found {0}",stay.getRoom());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return stay;
    }

    @Override
    public void deleteStay(Stay stay) throws DeleteException {
        LOGGER.info("UserManager: Deleting user.");
        try{
            stay=em.merge(stay);
            em.remove(stay);
            LOGGER.info("UserManager: User deleted.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception deleting user.{0}",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    
}
