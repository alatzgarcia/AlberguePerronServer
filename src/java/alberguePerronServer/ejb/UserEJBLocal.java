/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.ejb;

import alberguePerronServer.entity.Privilege;
import alberguePerronServer.entity.User;
import alberguePerronServer.exception.CreateException;
import alberguePerronServer.exception.DeleteException;
import alberguePerronServer.exception.ReadException;
import alberguePerronServer.exception.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nerea, Diego
 */
@Local
public interface UserEJBLocal {
    /**
     * Finds a User by its id. 
     * @param id the id
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
     * Creates an user
     * @param user The user object
     * @throws CreateException If there is any Exception 
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
     * @param user The User object containing the user data. 
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deleteUser(User user) throws DeleteException;
    /**
     * login of the user
     * @param user The User object
     * @return The UserObject
     * @throws ReadException 
     */
    public User login(User user) throws ReadException;

    /**
     * Finds a user by its email
     * @param email the user's email
     * @return The UserObject
     * @throws ReadException 
     */
    public User findUserByEmail(String email) throws ReadException;
    /**
     * Method for the recovery of the password
     * @param user The User object
     * @return The User object
     * @throws ReadException 
     */
    public User recoverPassword(User user) throws ReadException;
    /**
     * Method for the change of the password
     * @param user The User object
     * @return The User object 
     */
    
    public User changePassword(User user) throws UpdateException;
    
    public List<User> findAllByPrivilege(Privilege privilege) throws ReadException;
}
