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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author Nerea Jimenez
 */
@Stateless
public class UserEJB implements UserEJBLocal{
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Method that finds and user by its id
     * @param id The id od the user
     * @return The user
     * @throws ReadException 
     */
    @Override
    public User findUserById(String id) throws ReadException {
          User user=null;
        try{
            LOGGER.info("User: Finding user by id.");
            user=em.find(User.class, id);
            if (user!= null){
                LOGGER.log(Level.INFO,"User: User found {0}",user.getId());
            }
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "User: Exception Finding user by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;
    }
    /**
     * Method that get all the users
     * @return all th users
     * @throws ReadException 
     */

    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> users=null;
        try{
            LOGGER.info("User: Reading all users.");
            users=em.createNamedQuery("findAllUsers").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "User: Exception reading all users:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    
    /**
     * Method that creates a new user
     * @param user The user
     * @throws CreateException 
     */
    @Override
    public void createUser(User user) throws CreateException {
        LOGGER.info("User: Creating user.");
        try{
            
            if(user.getPassword()!=null){
                 
                byte[] pass=desencrypt(DatatypeConverter.parseHexBinary(user.getPassword()));
                user.setPassword(DatatypeConverter.printHexBinary(getDigest(pass)));
            }
            em.persist(user);
            LOGGER.info("User: User created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "User Exception creating user.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    /**
     * Methos to update an user
     * @param user The user
     * @throws UpdateException 
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        LOGGER.info("User: Updating user.");
        try{
            //if(!em.contains(user))em.merge(user);
            em.merge(user);
            em.flush();
            LOGGER.info("User: User updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "User: Exception updating user.{0}",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    /**
     * Methos to delete an user
     * @param user
     * @throws DeleteException 
     */
    @Override
    public void deleteUser(User user) throws DeleteException {
        LOGGER.info("UserEJB: Deleting user.");
        try{
            user=em.merge(user);
            em.remove(user);
            LOGGER.info("UserEJB: User deleted.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception deleting user.",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        } 
    }
    
    /**
     * Method to find an user by its login
     * @param login The login of the user
     * @return The user
     * @throws ReadException 
     */
    @Override
    public User findUserByLogin(String login) throws ReadException {
        User user=null;
        try{
            user=(User) em.createNamedQuery("findUserByLogin")
                     .setParameter("login", login)
                     .getSingleResult();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;
    }
    
    /**
     * Method to find an user by its email
     * @param email The email of the user
     * @return the user
     * @throws ReadException 
     */
    @Override
    public User findUserByEmail(String email) throws ReadException {
        User user=null;
        try{
            user=(User) em.createNamedQuery("findUserByEmail")
                     .setParameter("email", email)
                     .getSingleResult();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;
    }
    
    
    /**
     * Method for the login of the user
     * @param user The user
     * @return The user
     * @throws ReadException
     */
    @Override
    public User login(User user) throws ReadException {
          User userDB=null;
          
          //desencrypt the password tha has been encrypted in the client
          byte[] pass=desencrypt(DatatypeConverter.parseHexBinary(user.getPassword()));
          //get the digest
          byte[] digestCliente = getDigest(pass);
          
        try{
            //find the user by the login
            userDB=findUserByLogin(user.getLogin());
            //get the password thats kept in the DB
            String digestDB = userDB.getPassword();
            byte[] digest = DatatypeConverter.parseHexBinary(digestDB);
            
            //Compare the two digest
            if (userDB!= null){
               if(MessageDigest.isEqual(digestCliente, digest)){
                   LOGGER.info("correcto");
               }else{
                   userDB=null;
               }
            }
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "User: Exception Finding user by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        
        return userDB;
    }
    
    /**
     * Method for the update of the password
     * @param user
     * @return
     * @throws ReadException 
     */
    @Override
    public User updatePassword(User user) throws ReadException {
               
        try{
            //desencrypt the password and set it to the user to update
            byte[] pass=desencrypt(DatatypeConverter.parseHexBinary(user.getPassword()));
            user.setPassword(DatatypeConverter.printHexBinary(getDigest(pass)));
            updateUser(user);
            
        }catch(Exception e){
            
            e.getStackTrace();
            e.getCause();
        }
        
        return user;
    }
   
    /**
     * Method to desencrypt the password
     * @param pass Encrypted password
     * @return password
     */
    public byte[] desencrypt(byte[] pass){
        FileInputStream fis;
       byte[] decodedMessage = null;
	
        try {
            //get the private key
            fis = new FileInputStream("private.key");
            byte[] privateKey = new byte[fis.available()];
            fis.read(privateKey);
		
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privKey = keyFactory.generatePrivate(privKeySpec);
		
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            decodedMessage = cipher.doFinal(pass);
            
            LOGGER.info("Contraseña desencriptada");
			
	} catch (FileNotFoundException e) {
            LOGGER.severe(e.getMessage());
	} catch (IOException e) {
            LOGGER.severe(e.getMessage());
	}catch (NoSuchAlgorithmException e) {
            LOGGER.severe(e.getMessage());
	} catch (InvalidKeySpecException e) {
            LOGGER.severe(e.getMessage());
	} catch (NoSuchPaddingException e) {
            LOGGER.severe(e.getMessage());
	} catch (InvalidKeyException e) {
            LOGGER.severe(e.getMessage());
	} catch (IllegalBlockSizeException e) {
            LOGGER.severe(e.getMessage());
	} catch (BadPaddingException e) {
            LOGGER.severe(e.getMessage());
        }
        return decodedMessage;
    }
    
    /**
     * Method to digest the password
     * @param password
     * @return 
     */    
    public byte[] getDigest(byte[] password){
      
	String algorithm = "SHA-512";
        
        byte[] result = null; 
	try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(password);
            result = md.digest();

        } catch (NoSuchAlgorithmException e) {
		LOGGER.severe(e.getMessage());
        }
                
        return result;
    }
    
    
}
