/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import ejb.PetManagerEJBLocal;
import entity.Pet;
import entity.User;
import exception.CreateException;
import exception.DeleteException;
import exception.ReadException;
import exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author 2dam
 */
@Path("pet")
public class PetREST {
      /**
     * Logger for class methods.
     */
    private static final Logger LOGGER =
            Logger.getLogger("PetREST.class");
    /**
     * EJB reference for business logic object.
     */
    @EJB
    private PetManagerEJBLocal ejb;
    /**
     * RESTful POST method for creating {@link User} objects from XML representation.
     * @param pet The object containing user data.
     */
    @POST
    @Consumes({"application/xml"})
    public void create(Pet pet) throws ReadException {
        try {
            LOGGER.log(Level.INFO,"PetRESTful service: create {0}.",pet);
            ejb.createPet(pet);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, 
                    "PetRESTful service: Exception creating user, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * RESTful PUT method for updating {@link User} objects from XML representation.
     * @param pet The object containing user data.
     */
    @PUT
    @Consumes({"application/xml"})
    public void update(Pet pet) throws ReadException {
        try {
            LOGGER.log(Level.INFO,"PetRESTful service: update {0}.",pet);
            ejb.updatePet(pet);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE,
                    "PetRESTful service: Exception updating pet, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
    }
    /**
     * RESTful DELETE method for deleting {@link User} objects from id.
     * @param id The id for the object to be deleted.
     */
    @DELETE
    @Path("{id}")
    //@Consumes({"application/xml", "application/json"})
    public void delete(@PathParam("id") Integer id) {
        try {
            LOGGER.log(Level.INFO,"PetRESTful service: delete Pet by id={0}.",id);
            Pet pet = find(id) ;
            ejb.deletePet(pet);
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE,
                    "PetRESTful service: Exception deleting pet by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
           
        } 
    }
    /**
     * RESTful GET method for reading {@link User} objects through an XML representation.
     * @param id The id for the object to be read.
     * @return The User object containing data. 
     */
    @GET
    @Path("{id}")
    @Produces({"application/xml"})
    public Pet find(@PathParam("id") Integer id) {
        Pet pet=null;
        try {
            LOGGER.log(Level.INFO,"PetRESTful service: find pet by id={0}.",id);
            pet= ejb.findPetById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "PetRESTful service: Exception reading pet by id, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return pet;
    }
    /**
     * RESTful GET method for reading all {@link Pet} objects through an XML representation.
     * @return A List of User objects containing data.
     */
    @GET
    @Produces({"application/xml"})
    public List<Pet> findAllPets() {
        List<Pet> pets=null;
        try {
            LOGGER.log(Level.INFO,"PetRESTful service: find all pets.");
            pets=ejb.findAllPets();        
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE,
                    "PetRESTful service: Exception reading all pets, {0}",
                    ex.getMessage());
            throw new InternalServerErrorException(ex);
        }
        return pets;
    }
    
    
}
