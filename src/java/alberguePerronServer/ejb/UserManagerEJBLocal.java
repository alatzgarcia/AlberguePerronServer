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

    /**
     * Creates an user with the data sent by the REST
     * @param user User: User data to create
     * @throws CreateException 
     */
    public void createUser(User user)throws CreateException;

    /**
     * Update an user with the data sent by the REST
     * @param user User: New data to update
     * @throws UpdateException throws when the update fails
     */
    public void updateUser(User user)throws UpdateException;

    /**
     * Finds an user by Id
     * @param id String: The id of the user yo search.
     * @return The User found
     * @throws ReadException throws when the read operation fails.
     */
    public User findUserById(String id)throws ReadException;

    /**
     * Deletes an user by id
     * @param user User: The user found by Id   
     * @throws DeleteException throws when the delete operation fails.
     */
    public void deleteUser(User findUserByLogin)throws DeleteException;

    /**
     * Finds all the users
     * @return a List of all the users 
     * @throws ReadException throws when the read operation fails.
     */
    public List<User> findAllUsers() throws ReadException;
    
}
