/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Pet;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
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
     * @return The {@link User} object containing user data. 
     * @throws ReadException If there is any Exception during processing.
     */
   
    public List<Pet> findAllPets() throws ReadException;
    /**
     * Finds a List of {@link User} objects containing data for all users with certain
     * @param pet
     * @throws ReadException If there is any Exception during processing.
     */
   
   
    public void updatePet(Pet pet) throws UpdateException, ReadException;
    /**
     * Deletes a user's data in the underlying application storage.
     * @param pet 
     * @throws DeleteException If there is any Exception during processing.
     */
    public void deletePet(Pet pet) throws DeleteException, ReadException;
    
    /**
     * Creates a User and stores it in the underlying application storage. 
     * @param pet 
     * @throws CreateException If there is any Exception during processing.
     */
    public void createPet(Pet pet) throws CreateException, ReadException;

    public Pet findPetById(Integer id) throws ReadException;

    
}
