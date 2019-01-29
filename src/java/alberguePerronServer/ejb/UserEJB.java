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
import alberguePerronServer.passwordGen.PasswordGenerator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.ejb.Stateless;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.mail.EmailException;
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
            
            PasswordGenerator randomPass = new PasswordGenerator.
                        PasswordGeneratorBuilder().useDigits(true).useUpper(true).
                        useLower(true).build();
                String password = randomPass.generate(8);
                LOGGER.info("contraseña generada: "+password);
                byte[] pass=DatatypeConverter.parseHexBinary(password);
                //byte[] pass=password.getBytes();
                user.setPassword(DatatypeConverter.printHexBinary(getDigest(pass)));
                //user.setPassword(getDigest(pass).toString());
                
                
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
          
          LOGGER.info("Contraseña encriptada en string en servidor");
          //desencrypt the password tha has been encrypted in the client
           byte[] pass=desencrypt(DatatypeConverter.parseHexBinary(user.getPassword()));
           //byte[] pass=desencrypt(user.getPassword().getBytes());
           LOGGER.info("Contraseña servidor desencriptada en Bytes:"+pass);
           LOGGER.info("Contraseña servidor desencriptada en String:"+DatatypeConverter.printHexBinary(pass));
          //get the digest
          byte[] digestCliente = getDigest(pass);
          LOGGER.info("Digest cliente: "+DatatypeConverter.printHexBinary(digestCliente));
          
        try{
            //find the user by the login
            userDB=findUserByLogin(user.getLogin());
            //get the password thats kept in the DB
            String digestDB = userDB.getPassword();
            //byte[] digest = DatatypeConverter.parseHexBinary(digestDB);
            byte[] digest = digestDB.getBytes();
            LOGGER.info("Digest DB: "+DatatypeConverter.printHexBinary(digest));
            
            //Compare the two digest
            if (userDB!= null){
               if(MessageDigest.isEqual(digestCliente, digest)){
                   LOGGER.info("correcto");
               }else{
                   userDB=null;
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
    
    /**
     * Method for the update of the password
     * @param user
     * @return
     * @throws ReadException 
     */
    @Override
    public User updatePassword(User user) throws ReadException {
               
        try{
            PasswordGenerator randomPass = new PasswordGenerator.
                        PasswordGeneratorBuilder().useDigits(true).useUpper(true).
                        useLower(true).build();
                String password = randomPass.generate(8);
                //user.setPassword(password);
                
                LOGGER.info("contraseña generada: "+password);
                byte[] pass=DatatypeConverter.parseHexBinary(password);
                //byte[] pass=password.getBytes();
                user.setPassword(DatatypeConverter.printHexBinary(getDigest(pass)));
                //user.setPassword(getDigest(pass).toString());
            //desencrypt the password and set it to the user to update
            
           // byte[] pass=DatatypeConverter.parseHexBinary(user.getPassword());
            //user.setPassword(DatatypeConverter.printHexBinary(getDigest(pass)));
            updateUser(user);
            sendEmail(user, password);
            
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
            e.printStackTrace();
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
    
    public void sendEmail(User user,String pass){
        //get the creedential of the email account
        ArrayList<String> creedentials=getEmailCredentials();
        String email =creedentials.get(0);
        String password =creedentials.get(1);
         
                             
                Properties props = System.getProperties();
                props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
                props.put("mail.smtp.user", email);
                props.put("mail.smtp.clave", password);    //La clave de la cuenta
                props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
                props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
                props.put("mail.smtp.port", "465"); //El puerto SMTP seguro de Google
         try {   
                Session session = Session.getDefaultInstance(props);
                // Create the email message
                HtmlEmail emailToSend = new HtmlEmail();
                emailToSend.setAuthentication(email, password);
                emailToSend.setHostName("smtp.gmail.com");
        
                emailToSend.addTo(user.getEmail(), user.getName());
        
                emailToSend.setFrom(email, "Albergue Perron");
                emailToSend.setSubject("Nueva contraseña");
                emailToSend.setDebug(true);
                emailToSend.setSSLCheckServerIdentity(true);
                emailToSend.setStartTLSRequired(true);

                // set the html message
                emailToSend.setHtmlMsg("<html>Esta es su nueva contraseña: "+pass+
                        "/n puede modificarla la próxima vez que inicie sesión</html>");
                
                // send the email
                emailToSend.send();
                
                } catch (EmailException ex) {
                   LOGGER.severe(ex.getMessage());
                }
    }
    
    /**
     * Method to get the creedentials of the email account that have been
     * encrypted with a private key
     * @return 
     */
    public ArrayList<String> getEmailCredentials(){
	ArrayList<String> creedentials = new ArrayList<>();
	ObjectInputStream ois = null;
	try {
            ois = new ObjectInputStream(new FileInputStream("privKey"));
            SecretKey privateKey =(SecretKey) ois.readObject();
	   
	    //email									
            ois = new ObjectInputStream(new FileInputStream("email"));
            byte[] iv1 =(byte[]) ois.readObject();
            byte[] encodedEmail =(byte[]) ois.readObject();
					
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv1Param = new IvParameterSpec(iv1);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, iv1Param);
            byte[] decodedEmail = cipher.doFinal(encodedEmail);
            String email = new String(decodedEmail);
            
            //password
            ois = new ObjectInputStream(new FileInputStream("pass"));
            byte[] iv2 =(byte[]) ois.readObject();
            byte[] encodedPass =(byte[]) ois.readObject();
	
            IvParameterSpec iv2Param = new IvParameterSpec(iv2);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, iv2Param);
            byte[] decodedPass = cipher.doFinal(encodedPass);
            String pass = new String(decodedPass);
            
            
            creedentials.add(0, email);
            creedentials.add(1, pass);
					
            } catch (NoSuchAlgorithmException e) {
                LOGGER.severe(e.getMessage());
            } catch (NoSuchPaddingException e) {
                LOGGER.severe(e.getMessage());
            } catch (InvalidKeyException e) {
		LOGGER.severe(e.getMessage());
            } catch (IllegalBlockSizeException e) {
		LOGGER.severe(e.getMessage());
            } catch (BadPaddingException e) { //Clave introducida no es correcta
		LOGGER.severe(e.getMessage());
            } catch (FileNotFoundException e) {
		LOGGER.severe(e.getMessage());
            } catch (IOException e) {
		LOGGER.severe(e.getMessage());
            } catch (ClassNotFoundException e) {
		LOGGER.severe(e.getMessage());
            } catch (InvalidAlgorithmParameterException e) {
		LOGGER.severe(e.getMessage());
            }finally {
                if(ois!=null) {
                    try {
			ois.close();
                    } catch (IOException e) {
			LOGGER.severe(e.getMessage());
                    }
                }
					
            }
       
        return creedentials;
    }
    
    
}
