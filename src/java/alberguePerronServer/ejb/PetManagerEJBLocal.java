/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.ejb;

import alberguePerronServer.entity.Pet;
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
public interface PetManagerEJBLocal {
  
    /**
     * Finds a {@link User} by its login. 
     * @param login The login for the user to be found.
     * @return The {@link User} object containing user data. 
     * @throws ReadException If there is any Exception during processing.
     */
   
    public List<Pet> findAllPets() throws ReadException;
    /**
     * Finds a List of {@link User} objects containing data for all users with certain
     * @return A List of {@link User} objects.
     * @throws ReadException If there is any Exception during processing.
     */
   
   
    public void updatePet(Pet pet) throws UpdateException, ReadException;
    /**
     * Deletes a user's data in the underlying application storage. 
     * @param user The {@link User} object containing the user data. 
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deletePet(Pet pet) throws DeleteException, ReadException;
    
    /**
     * Creates a User and stores it in the underlying application storage. 
     * @param user The {@link User} object containing the user data. 
     * @throws CreateException If there is any Exception during processing.
     */
    public void createPet(Pet pet) throws CreateException, ReadException;

    public Pet findPetById(Integer id) throws ReadException;

    
}