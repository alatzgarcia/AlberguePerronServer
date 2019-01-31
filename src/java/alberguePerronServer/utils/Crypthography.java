/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.utils;

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
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

/**
 * Class for the desencryption and the digest
 * @author Nerea Jimenez
 */
public class Crypthography {
    
     private static final Logger LOGGER =
            Logger.getLogger("");
    
    /**
     * Method to desencrypt the password
     * @param pass Encrypted password
     * @return password
     */
    public static byte[] desencrypt(String pass){
        FileInputStream fis;
       byte[] decodedMessage = null;
	
        try {
             String privKeyPath = ResourceBundle.getBundle("alberguePerronServer.config.parameters")
                .getString("privateKey");
            //get the private key
            fis = new FileInputStream(privKeyPath);
            byte[] privateKey = new byte[fis.available()];
            fis.read(privateKey);
		
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privKey = keyFactory.generatePrivate(privKeySpec);
		
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            decodedMessage = cipher.doFinal(DatatypeConverter.parseHexBinary(pass));
            
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
     * @param password the password
     * @return the hash
     */    
    public static byte[] getDigest(byte[] password){
      
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
