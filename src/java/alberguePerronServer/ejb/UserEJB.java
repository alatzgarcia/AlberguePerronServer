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

import alberguePerronServer.utils.Crypthography;
import alberguePerronServer.utils.Email;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.DatatypeConverter;

/**
 * EJB class for managing User entity CRUD operations.
 *
 * @author Nerea Jimenez
 */
@Stateless
public class UserEJB implements UserEJBLocal {

    /**
     * Logger for the class.
     */
    private static final Logger LOGGER
            = Logger.getLogger("");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Method that finds and user by its id
     *
     * @param id The id of the user
     * @return The user
     * @throws ReadException
     */
    @Override
    public User findUserById(String id) throws ReadException {
        User user = null;
        try {
            LOGGER.info("User: Finding user by id.");
            user = em.find(User.class, id);
            if (user != null) {
                LOGGER.log(Level.INFO, "User: User found {0}", user.getId());
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "User: Exception Finding user by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;
    }

    /**
     * Method that get all the users
     *
     * @return all th users
     * @throws ReadException
     */
    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> users = null;
        try {
            LOGGER.info("User: Reading all users.");
            users = em.createNamedQuery("findAllUsers").getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "User: Exception reading all users:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    /**
     * Method that creates a new user
     *
     * @param user The user
     * @throws CreateException
     */
    @Override
    public void createUser(User user) throws CreateException {
        LOGGER.info("User: Creating user.");
        try {
            //byte[] passDes = Crypthography.desencrypt(user.getPassword());
            //user.setPassword(DatatypeConverter.printHexBinary(Crypthography.getDigest(passDes)));
             //New random password
            String[] symbols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
            int length = 10;
            Random random = SecureRandom.getInstance("SHA1PRNG");
            StringBuilder stringBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int indexRandom = random.nextInt(symbols.length);
                stringBuilder.append(symbols[indexRandom]);
            }
            String pass = stringBuilder.toString();

            user.setPassword(DatatypeConverter.printHexBinary(
                    Crypthography.getDigest(pass.getBytes())));

            em.persist(user);
            Email.sendEmailRecovery(user, pass);
            em.persist(user);
            LOGGER.info("User: User created.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "User Exception creating user.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Methos to update an user
     *
     * @param user The user
     * @throws UpdateException
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        LOGGER.info("User: Updating user.");
        try {
            //if(!em.contains(user))em.merge(user);
            em.merge(user);
            em.flush();
            LOGGER.info("User: User updated.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "User: Exception updating user.{0}",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Methos to delete an user
     *
     * @param user
     * @throws DeleteException
     */
    @Override
    public void deleteUser(User user) throws DeleteException {
        LOGGER.info("UserEJB: Deleting user.");
        try {
            user = em.merge(user);
            em.remove(user);
            LOGGER.info("UserEJB: User deleted.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB: Exception deleting user.",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    /**
     * Method to find an user by its email
     *
     * @param email The email of the user
     * @return the user
     * @throws ReadException
     */
    @Override
    public User findUserByEmail(String email) throws ReadException {
        User user = null;
        try {
            user = (User) em.createNamedQuery("findUserByEmail")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;
    }

    /**
     * Method for the login of the user
     *
     * @param user The user
     * @return The user
     * @throws ReadException
     */
    @Override
    public User login(User user) throws ReadException {
        User userDB = null;

        //desencrypt the password tha has been encrypted in the client
        byte[] pass = Crypthography.desencrypt(user.getPassword());

        //get the digest
        byte[] digestCliente = Crypthography.getDigest(pass);

        try {
            //find the user by the login

            userDB = (User) em.createNamedQuery("findUserByLogin")
                    .setParameter("login", user.getLogin())
                    .getSingleResult();

            //get the password that is kept in the DB
            byte[] digestDB = DatatypeConverter.parseHexBinary(userDB.getPassword());

            //Compare the two hashes
            if (userDB != null) {
                if (MessageDigest.isEqual(digestDB, digestCliente)) {
                    LOGGER.info("Correct login");
                } else {
                    userDB = null;
                    LOGGER.info("Incorect login");
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }

        return userDB;
    }

    /**
     * Method for the recovery of the password
     *
     * @param user The user
     * @return
     * @throws ReadException
     */
    @Override
    public User recoverPassword(User user) throws ReadException {

        try {
            
            //New random password
            String[] symbols = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
            int length = 10;
            Random random = SecureRandom.getInstance("SHA1PRNG");
            StringBuilder stringBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int indexRandom = random.nextInt(symbols.length);
                stringBuilder.append(symbols[indexRandom]);
            }
            String pass = stringBuilder.toString();

            user.setPassword(DatatypeConverter.printHexBinary(
                    Crypthography.getDigest(pass.getBytes())));
            //user.setLastPasswordChange(Date.);
            updateUser(user);
            Email.sendEmailRecovery(user, pass);

        } catch (UpdateException | NoSuchAlgorithmException e) {
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }

        return user;
    }
    
    /**
     * Method for the change of the password
     *
     * @param user the user
     * @return
     * @throws alberguePerronServer.exception.UpdateException
     */
    @Override
    public User changePassword(User user) throws UpdateException {
        
        //desencrypt the password tha has been encrypted in the client
        byte[] pass = Crypthography.desencrypt(user.getPassword());

        user.setPassword(DatatypeConverter.printHexBinary(Crypthography.getDigest(pass)));

        try {
            updateUser(user);
            Email.sendEmailChange(user);
        } catch (UpdateException e) {
            LOGGER.severe(e.getMessage());
            throw new UpdateException(e.getMessage());
        }

        return user;
    }

}
