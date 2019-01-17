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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



/**
 *
 * @author 2dam
 */
@Stateless
public class PetManagerEJB implements PetManagerEJBLocal {
/**
     * Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("PetREST.class");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
    
    /**
     * Creates a Pet and stores it in the underlying application storage. 
     * @param pet The {@link Pet} object containing the pet data. 
     * @throws exception.ReadException 
     * @throws CreateException If there is any Exception during processing.
     */

    @Override
    public List<Pet> findAllPets() throws ReadException {
        List<Pet> pets=null;
        try{
            LOGGER.info("PetManager: Reading all pets.");
            pets=em.createNamedQuery("findAllPets").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "PetManager: Exception reading all pets:",
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
            LOGGER.info("PetManager: Pet updated.");
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
        LOGGER.info("PetManager: Creating Pet.");
        try{
            em.persist(pet);
            LOGGER.info("PetManager: Pet created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "PetManager: Exception creating pet.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public Pet findPetById(Integer id) throws ReadException {
        Pet pet=null;
        try{
            LOGGER.info("PetManager: Finding pet by login.");
            pet=em.find(Pet.class, id);
            LOGGER.log(Level.INFO,"PetManager: Pet found {0}",pet.getName());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "PetManager: Exception Finding pet by id:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return pet;
    }

  
    
}
