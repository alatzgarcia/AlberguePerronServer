/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.ejb;

import alberguePerronServer.entity.Incident;
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
 * IncidentEJB class for the AlberguePerronServer application
 * @author Alatz
 */
@Stateless
public class IncidentEJB implements IncidentEJBLocal {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("incidentejb");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Finds an incident by its id
     * @param id the incident id
     * @return the incident
     * @throws ReadException 
     */
    @Override
    public Incident findIncidentById(Integer id) throws ReadException {
        Incident incident=null;
        try{
            LOGGER.info("IncidentManager: Finding incident by id.");
            incident=em.find(Incident.class, id);
            LOGGER.log(Level.INFO,"IncidentManager: Incident found {0}",incident.getId());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "IncidentManager: Exception Finding incident by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return incident;
    }
    
    /**
     * Finds all incidents
     * @return the incidents
     * @throws ReadException 
     */
    @Override
    public List<Incident> findAllIncidents() throws ReadException {
        List<Incident> incidents=null;
        try{
            LOGGER.info("IncidentManager: Reading all incidents.");
            //TODO -- Create named query on entity
            incidents=em.createNamedQuery("findAllIncidents").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "IncidentManager: Exception reading all incidents:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return incidents;
    }
   
    /**
     * Creates a new incident
     * @param incident the incident object
     * @throws CreateException 
     */
    @Override
    public void createIncident(Incident incident) throws CreateException {
        LOGGER.info("IncidentManager: Creating incident.");
        try{
            em.persist(incident);
            LOGGER.info("IncidentManager: Incident created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "IncidentManager: Exception creating incident.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    
    /**
     * Updates an incident
     * @param incident the incident object
     * @throws UpdateException 
     */
    @Override
    public void updateIncident(Incident incident) throws UpdateException {
        LOGGER.info("IncidentManager: Updating incident.");
        try{
            //if(!em.contains(user))em.merge(user);
            em.merge(incident);
            em.flush();
            LOGGER.info("IncidentManager: Incident updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "IncidentManager: Exception updating incident.{0}",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    
    /**
     * Deletes an incident
     * @param incident the incident object
     * @throws DeleteException 
     */
    @Override
    public void deleteIncident(Incident incident) throws DeleteException {
        LOGGER.info("IncidentManager: Deleting incident.");
        try{
            incident=em.merge(incident);
            em.remove(incident);
            LOGGER.info("IncidentManager: Incident deleted.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "IncidentManager: Exception deleting incident.{0}",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
}
