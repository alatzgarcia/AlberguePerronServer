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

/**
 *
 * @author Diego
 */
public interface UserManagerEJBLocal {

    public void createUser(User user)throws CreateException;

    public void updateUser(User user)throws UpdateException;

    public User findUserById(String id)throws ReadException;

    public void deleteUser(User findUserByLogin)throws DeleteException;

    public List<User> findAllUsers() throws ReadException;
    
}
