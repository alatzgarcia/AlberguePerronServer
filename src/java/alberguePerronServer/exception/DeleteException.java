/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.exception;

/**
 *
 * @author Alatz
 */
public class DeleteException extends Exception {

    public DeleteException(){
        
    }
    
    public DeleteException(String message) {
        super(message);
    }
    
}
