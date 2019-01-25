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
import albergueperronclient.passwordGen.PasswordGenerator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

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

    @Override
    public List<User> findAllUsers() throws ReadException {
        List<User> users=null;
        try{
            LOGGER.info("User: Reading all users.");
            users=em.createNamedQuery("findAllUsers").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserM: Exception reading all users:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return users;
    }

    @Override
    public void createUser(User user) throws CreateException {
        LOGGER.info("User: Creating user.");
        try{
            //desencriptar contraseña
            //digest
            if(user.getPassword()!=null){
                //generateKey();
                
                byte[] pass=desencrypt(DatatypeConverter.parseHexBinary(user.getPassword()));
                user.setPassword(DatatypeConverter.printHexBinary(getDigest(pass)));
            }
            em.persist(user);
            LOGGER.info("User: User created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception creating user.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

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
    
    @Override
    public User findUserByLogin(String login) throws ReadException {
        User user=null;
        try{
            LOGGER.info("");
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
    
    @Override
    public User findUserByEmail(String email) throws ReadException {
        User user=null;
        try{
            LOGGER.info("");
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
     *
     * @param id
     * @return
     * @throws ReadException
     */
    @Override
    public User login(User user) throws ReadException {
          User userDB=null;
          
          //desencriptar contraseña que viene de cliente
          
          byte[] pass=desencrypt(DatatypeConverter.parseHexBinary(user.getPassword()));
          
          //digest
          byte[] digestCliente = getDigest(pass);
          
        try{
            userDB=findUserByLogin(user.getLogin());
            String digestDB = userDB.getPassword();
            byte[] digest = DatatypeConverter.parseHexBinary(digestDB);
            if (userDB!= null){
               if(MessageDigest.isEqual(digestCliente, digest)){
                   LOGGER.info("correcto");
               }else{
                   LOGGER.info("incorrecto");  
               }
            }
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "User: Exception Finding user by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        
        return userDB;
    }
    
     @Override
    public User recoverEmail(User user) throws ReadException {
         
          
          
        try{
                byte[] pass=desencrypt(DatatypeConverter.parseHexBinary(user.getPassword()));
                user.setPassword(DatatypeConverter.printHexBinary(getDigest(pass)));
                updateUser(user);
                
                
                // Create the email message
                HtmlEmail email = new HtmlEmail();
                
                email.setHostName("gmail.com");
                email.addTo("nerea.jim87@gmail.com", "John Doe");
                email.setFrom("albergueperronbinary@gmail.com", "Me");
                email.setSubject("Test email with inline image");

                // set the html message
                email.setHtmlMsg("<html>Prueba</html>");

                // set the alternative message
                email.setTextMsg("Your email client does not support HTML messages");

                // send the email
                email.send();
            
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "User: Exception Finding user by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        
        return user;
    }
   
    
    public byte[] desencrypt(byte[] pass){
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
    
    public byte[] getDigest(byte[] password){
      
		String algorithm = "SHA-512";
                //StringBuilder stringBuilder = null;
                byte[] result = null; 
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			//byte[] myTextInBytes = password;
			md.update(password);
			result = md.digest();

			/**stringBuilder = new StringBuilder();
			for (byte theByte : result) {
				stringBuilder.append(String.format("%02x", theByte & 0xff));
			}
			**/
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
                
             return result;
	}
    
    
}
