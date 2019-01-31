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
import javax.ejb.Local;

/**
 * IncidentEJBLocal class for the AlberguePerronServer application
 * @author Alatz
 */
@Local
public interface IncidentEJBLocal {
    /**
     * Finds an incident by its id
     * @param id
     * @return the incident
     * @return
     * @throws ReadException 
     */
    public Incident findIncidentById(Integer id) throws ReadException;
    /**
     * Finds all incidents
     * @return the incidents
     * @throws ReadException 
     */
    public List<Incident> findAllIncidents() throws ReadException;
    /**
     * Creates a new incident
     * @param incident the incident object
     * @throws CreateException 
     */
    public void createIncident(Incident incident) throws CreateException;
    /**
     * Updates an incident
     * @param incident the incident object
     * @throws UpdateException 
     */
    public void updateIncident(Incident incident) throws UpdateException;  
    /**
     * Deletes an incident
     * @param incident the incident object
     * @throws DeleteException 
     */
    public void deleteIncident(Incident incident) throws DeleteException;
}
