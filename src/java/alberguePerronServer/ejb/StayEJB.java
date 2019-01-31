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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author Diego
 */
@Stateless
public class StayEJB implements StayManagerEJBLocal{
    
    
    /**
     * The logger that will show messages
     */
    private static final Logger LOGGER =
            Logger.getLogger("StayEJB.class");
    
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Creates an stay with the data sent by REST
     * @param stay Stay: Data to create
     * @throws CreateException  throws when the create operation fails.
     */
    @Override
    public void createStay(Stay stay) throws CreateException {
        LOGGER.info("StayEJB: Creating stay.");
        try{
            em.persist(stay);
            LOGGER.info("StayEJB: Stay created.");
        }catch(PersistenceException pe){
            LOGGER.severe(pe.getMessage());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "StayEJB: Exception creating stay.",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    
    /**
     * Updates an stay with the data sent by REST
     * @param stay Stay: Data to update
     * @throws UpdateException throws when the update operation fails.
     */
    @Override
    public void updateStay(Stay stay) throws UpdateException {
        LOGGER.info("UserEJB: Updating user.");
        try{
            if(!em.contains(stay))em.merge(stay);
            em.merge(stay);
            em.flush();
            LOGGER.info("StayEJB: stay updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "StayEJB: Exception updating stay.",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    
    /**
     * Finds all the stays
     * @return The List of the stays found
     * @throws ReadException throws when the read operation fails
     */
    @Override
    public List<Stay> findAllStays() throws ReadException {
        List<Stay> stays=null;
        try{
            LOGGER.info("UserEJB: Reading all users.");
            stays=em.createNamedQuery("findAllStays").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception reading all users:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return stays;
    }
    
    /**
     * Finds an user by ID
     * @param id Integer: The id that of the stay to search
     * @return  The stay found
     * @throws ReadException throws when the read opertion fails. 
     */
    @Override
    public Stay findStayById(Integer id) throws ReadException {
        Stay stay=null;
        try{
            LOGGER.info("UserEJB: Finding user by login.");
            stay=em.find(Stay.class, id);
            if(stay!=null){
                LOGGER.log(Level.INFO,"UserEJB: User found",stay.getStayRoom());
            }
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return stay;
    }
    
    /**
     * Deletes a stay
     * @param stay Stay: The stay sent by the REST
     * @throws DeleteException throws when the delete fails
     */
    @Override
    public void deleteStay(Stay stay) throws DeleteException {
        LOGGER.info("UserEJB: Deleting user.");
        try{
            stay=em.merge(stay);
            em.remove(stay);
            LOGGER.info("UserEJB: User deleted.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception deleting user.",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
    
}
