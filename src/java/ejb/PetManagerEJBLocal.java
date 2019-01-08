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
import sun.java2d.cmm.Profile;

/**
 *
 * @author 2dam
 */
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
     * profile value.
     * @param profile The profile value for the users to be found.
     * @return A List of {@link User} objects.
     * @throws ReadException If there is any Exception during processing.
     */
    public List<Pet> findPetsByProfile(Profile profile) throws ReadException;
    /**
     * Finds a List of {@link Department} objects containing data for all departments in the
     * application data storage.
     * @return A List of {@link Department} objects.
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

    
}
