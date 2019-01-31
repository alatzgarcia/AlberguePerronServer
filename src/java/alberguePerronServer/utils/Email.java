/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.utils;

import alberguePerronServer.entity.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.mail.Session;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Class for the methods related to the password recover email
 * @author Nerea Jimenez
 */
public class Email{
        
     private static final Logger LOGGER =
            Logger.getLogger("");
    
    /**
     * Method that sends an email in the recovery
     * @param user the user
     * @param pass the password
     */
     public static void sendEmailRecovery(User user,String pass){
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
                        " puede modificarla la próxima vez que inicie sesión</html>");
                
                // send the email
                emailToSend.send();
                
                } catch (EmailException ex) {
                   LOGGER.severe(ex.getMessage());
                }
    }
     
     public static void sendEmailChange(User user){
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
                emailToSend.setSubject("Cambio de contraseña");
                emailToSend.setDebug(true);
                emailToSend.setSSLCheckServerIdentity(true);
                emailToSend.setStartTLSRequired(true);

                // set the html message
                emailToSend.setHtmlMsg("<html>Su contraseña a sido cambiada</html>");
                
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
    public static ArrayList<String> getEmailCredentials(){
	ArrayList<String> creedentials = new ArrayList<>();
         String privKeyPath = ResourceBundle.getBundle("alberguePerronServer.config.parameters")
                .getString("privateKeyEmail");
         String emailPath = ResourceBundle.getBundle("alberguePerronServer.config.parameters")
                .getString("emailEncrip");
         String passPath = ResourceBundle.getBundle("alberguePerronServer.config.parameters")
                .getString("passEncrip");
	ObjectInputStream ois = null;
	try {
            ois = new ObjectInputStream(new FileInputStream(privKeyPath));
            SecretKey privateKey =(SecretKey) ois.readObject();
	   
	    //email									
            ois = new ObjectInputStream(new FileInputStream(emailPath));
            byte[] iv1 =(byte[]) ois.readObject();
            byte[] encodedEmail =(byte[]) ois.readObject();
					
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv1Param = new IvParameterSpec(iv1);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, iv1Param);
            byte[] decodedEmail = cipher.doFinal(encodedEmail);
            String email = new String(decodedEmail);
            
            //password
            ois = new ObjectInputStream(new FileInputStream(passPath));
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
