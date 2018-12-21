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
 *
 * @author Alatz
 */
@Local
public interface IncidentEJBLocal {
    public Incident findIncidentById(Integer id) throws ReadException;
    public List<Incident> findAllIncidents() throws ReadException;
    public void createIncident(Incident incident) throws CreateException;
    public void updateIncident(Incident incident) throws UpdateException;
    public void deleteIncident(Incident incident) throws DeleteException;
}
