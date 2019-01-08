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
import javax.ejb.Local;

/**
 *
 * @author 2dam
 */
@Local
public interface UserEJBLocal {
    /**
     * Finds a {@link User} by its login. 
     * @param login The login for the user to be found.
     * @return The {@link User} object containing user data. 
     * @throws ReadException If there is any Exception during processing.
     */
    public User findUserByLogin(String login) throws ReadException;
    /**
     * Finds a List of {@link User} objects containing data for all users in the
     * application data storage.
     * @return A List of {@link User} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<User> findAllUsers() throws ReadException;
    /**
     * Finds a List of {@link User} objects containing data for all users with certain
     * profile value.
     * @param user
     * @throws alberguePerronServer.exception.CreateException
     */
    public void createUser(User user) throws CreateException;
    /**
     * Updates a user's data in the underlying application storage. 
     * @param user The {@link User} object containing the user data. 
     * @throws UpdateException If there is any Exception during processing.
     */
    public void updateUser(User user) throws UpdateException;
    /**
     * Deletes a user's data in the underlying application storage. 
     * @param user The {@link User} object containing the user data. 
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteUser(User user) throws DeleteException;
}
