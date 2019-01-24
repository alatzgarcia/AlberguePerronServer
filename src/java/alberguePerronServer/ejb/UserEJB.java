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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
 * @author Diego
 */
@Stateless
public class UserEJB implements UserManagerEJBLocal{
    
    /**
     * The logger that will show messages
     */
    private static final Logger LOGGER =
            Logger.getLogger("UserEJB.class");
    
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Creates an user with the data sent by the REST
     * @param user User: User data to create
     * @throws CreateException 
     */
    @Override
    public void createUser(User user) throws CreateException {
        LOGGER.info("UserEJB: Creating user.");
        try{
            user.setPassword(DatatypeConverter.printHexBinary(decrypt(DatatypeConverter.parseHexBinary(user.getPassword()))));
            user.setPassword(DatatypeConverter.printHexBinary(getDigest(user.getPassword())));
            em.persist(user);
            LOGGER.info("UserEJB: User created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception creating user.",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }    }
    
    /**
     * Update an user with the data sent by the REST
     * @param user User: New data to update
     * @throws UpdateException throws when the update fails
     */
    @Override
    public void updateUser(User user) throws UpdateException {
        LOGGER.info("UserEJB: Updating user.");
        try{
            //if(!em.contains(user))em.merge(user);
            em.merge(user);
            em.flush();
            LOGGER.info("UserEJB: User updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception updating user: ",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }    }

    /**
     * Finds an user by Id
     * @param id String: The id of the user yo search.
     * @return The User found
     * @throws ReadException throws when the read operation fails.
     */
    @Override
    public User findUserById(String id) throws ReadException {
        User user=null;
        try{
            LOGGER.info("UserEJB: Finding user by login.");
            user=em.find(User.class, id);
            if(user!=null){
                LOGGER.log(Level.INFO,"UserEJB: User found ",user.getLogin());
            }
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception Finding user by login:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return user;    }
    
    /**
     * Deletes an user by id
     * @param user User: The user found by Id   
     * @throws DeleteException throws when the delete operation fails.
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
        }    }

    /**
     * Finds all the users
     * @return a List of all the users 
     * @throws ReadException throws when the read operation fails.
     */
    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> users=null;
        try{
            LOGGER.info("UserEJB: Reading all users.");
            users=em.createNamedQuery("findAllUsers").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception reading all users:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;    
   }    

    @Override
    public List<User> findAllByPrivilege(String privilege) throws ReadException {
        List<User> users=null;
        try{
            LOGGER.info("UserEJB: Reading users by privilege.");
            if(privilege.equals("0")){
                users=em.createNamedQuery("findUserByPrivilege").setParameter("privilege", Privilege.USER).getResultList();
            }else if(privilege.equals("1")){
                users=em.createNamedQuery("findUserByPrivilege").setParameter("privilege", Privilege.EMPLOYEE).getResultList();
            }else if(privilege.equals("2")){
                users=em.createNamedQuery("findUserByPrivilege").setParameter("privilege", Privilege.ADMIN).getResultList();
            }
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserEJB: Exception reading by privilege:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }
     
    public byte[] decrypt(byte[] pass){
        FileInputStream fis;
       byte[] decodedMessage = null;
	
        try {
		                 		
            fis = new FileInputStream("private.key");
            byte[] privateKey = new byte[fis.available()];
            fis.read(privateKey);
		
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privKey = keyFactory.generatePrivate(privKeySpec);
		
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            decodedMessage = cipher.doFinal(pass);
                    //password = new String(decodedMessage);
            LOGGER.info("Contraseña desencriptada");
			
	} catch (FileNotFoundException e) {
			e.printStackTrace();
	} catch (IOException e) {
			e.printStackTrace();
	}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
	} catch (InvalidKeySpecException e) {
			e.printStackTrace();
	} catch (NoSuchPaddingException e) {
			e.printStackTrace();
	} catch (InvalidKeyException e) {
			e.printStackTrace();
	} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
	} catch (BadPaddingException e) {
			e.printStackTrace();
        }
        return decodedMessage;
    }
    
    public byte[] getDigest(String password) {
        String algorithm = "SHA-512";
        //StringBuilder stringBuilder = null;
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            //byte[] myTextInBytes = password;
            md.update(password.getBytes());
            result = md.digest();

            /**
             * stringBuilder = new StringBuilder(); for (byte theByte : result)
             * { stringBuilder.append(String.format("%02x", theByte & 0xff)); }
			*
             */
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}
