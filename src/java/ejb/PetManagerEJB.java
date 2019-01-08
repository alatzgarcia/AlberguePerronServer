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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sun.java2d.cmm.Profile;

/**
 *
 * @author 2dam
 */
public class PetManagerEJB implements PetManagerEJBLocal {
/**
     * Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("javafxserverside");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    /**
     * Finds a {@link User} by its login. 
     * @param login The login for the user to be found.
     * @return The {@link User} object containing user data. 
     * @throws ReadException If there is any Exception during processing.
     */
    
    /**
     * Creates a User and stores it in the underlying application storage. 
     * @param pet The {@link Pet} object containing the user data. 
     * @throws CreateException If there is any Exception during processing.
     */

    
    @Override
    public List<Pet> findAllPets() throws ReadException {
        List<Pet> pets=null;
        try{
            LOGGER.info("PetManager: Reading all users.");
            pets=em.createNamedQuery("findAllPets").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "PetManager: Exception reading all pets:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return pets;
    }

    @Override
    public List<Pet> findPetsByProfile(Profile profile) throws ReadException {
        List<Pet> pets=null;
        try{
            LOGGER.info("PetManager: Reading pets by profile.");
            pets=em.createNamedQuery("findUsersByProfile")
                     .setParameter("profile", profile)
                     .getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "PetManager: Exception reading pets by profile.",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return pets;
    }

    @Override
    public void updatePet(Pet pet) throws UpdateException {
       LOGGER.info("PetManager: Updating pet.");
        try{
            //if(!em.contains(user))em.merge(user);
            em.merge(pet);
            em.flush();
            LOGGER.info("PetManager: User updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "PetManager: Exception updating pet.{0}",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void deletePet(Pet pet) throws DeleteException {
        LOGGER.info("PetManager: Deleting pet.");
        try{
            pet=em.merge(pet);
            em.remove(pet);
            LOGGER.info("PetManager: Pet deleted.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "PetManager: Exception deleting pet.{0}",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

    @Override
    public void createPet(Pet pet) throws CreateException {
        LOGGER.info("UserManager: Creating user.");
        try{
            em.persist(pet);
            LOGGER.info("UserManager: User created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "UserManager: Exception creating user.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    
}
