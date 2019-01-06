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

    public void createStay(Stay stay) throws CreateException;

    public void updateStay(Stay stay) throws UpdateException;

    public List<Stay> findAllStays() throws ReadException;

    public Stay findStayById(Integer id) throws ReadException;

    public void deleteStay(Stay findStayById) throws DeleteException;

    
}
