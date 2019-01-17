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
     * Finds a User by its login. 
     * @param login The login for the user
     * @return The User object
     * @throws ReadException If there is any Exception
     */
    public User findUserById(String id) throws ReadException;
    /**
     * Finds a List of User objects 
     * @return A List of User objects.
     * @throws ReadException If there is any Exception
     */
    public List<User> findAllUsers() throws ReadException;
    /**
     * Finds a List of User objects 
     * @param user The User
     * @throws alberguePerronServer.exception.CreateException
     */
    public void createUser(User user) throws CreateException;
    /**
     * Updates a user's data in the database
     * @param user The User object 
     * @throws UpdateException If there is any Exception during processing.
     */
    public void updateUser(User user) throws UpdateException;
    /**
     * Deletes a user's data in the underlying application storage. 
     * @param user The {@link User} object containing the user data. 
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteUser(User user) throws DeleteException;

    public User login(User user) throws ReadException ;
    
    public String desencrypt(String pass);
    public StringBuilder getDigest(String password);

    public User findUserByLogin(String login)throws ReadException ;
}
