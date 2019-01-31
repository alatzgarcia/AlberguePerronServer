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

/**
 *
 * @author Diego
 */
public interface StayManagerEJBLocal {
    
    /**
     * Creates an stay with the data sent by REST
     * @param stay Stay: Data to create
     * @throws CreateException  throws when the create operation fails.
     */
    public void createStay(Stay stay) throws CreateException;

    /**
     * Updates an stay with the data sent by REST
     * @param stay Stay: Data to update
     * @throws UpdateException throws when the update operation fails.
     */
    public void updateStay(Stay stay) throws UpdateException;

    /**
     * Finds all the stays
     * @return The List of the stays found
     * @throws ReadException throws when the read operation fails
     */
    public List<Stay> findAllStays() throws ReadException;

    /**
     * Finds an user by ID
     * @param id Integer: The id that of the stay to search
     * @return  The stay found
     * @throws ReadException throws when the read opertion fails. 
     */
    public Stay findStayById(Integer id) throws ReadException;

    /**
     * Deletes a stay
     * @param stay Stay: The stay sent by the REST
     * @throws DeleteException throws when the delete fails
     */
    public void deleteStay(Stay findStayById) throws DeleteException;

    
}