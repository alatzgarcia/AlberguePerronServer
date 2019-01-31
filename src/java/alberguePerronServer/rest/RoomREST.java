/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.rest;

import alberguePerronServer.ejb.RoomEJBLocal;
import alberguePerronServer.entity.Room;
import alberguePerronServer.exception.CreateException;
import alberguePerronServer.exception.DeleteException;
import alberguePerronServer.exception.ReadException;
import alberguePerronServer.exception.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * RoomREST class for the AlberguePerronServer application
 * @author Alatz
 */
@Path("room")
public class RoomREST {
    /**
     * Logger for the class
     */
    private static final Logger LOGGER = Logger.getLogger("roomrest");
    /**
     * EJB Object
     */
    @EJB
    private RoomEJBLocal ejb;
    
    /**
     * Creates a new room
     * @param entity room object
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void create(Room entity) {
        try {
            ejb.createRoom(entity);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates a room
     * @param entity room to update
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void edit(Room entity) {
        try {
            ejb.updateRoom(entity);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Deletes a room
     * @param id id of the room to delete
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            ejb.deleteRoom(ejb.findRoomById(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Finds a room by its id
     * @param id id of the room
     * @return the room object
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Room find(@PathParam("id") Integer id) {
        Room room = null;
        try {
            room = ejb.findRoomById(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return room;
    }

    /**
     * Finds all rooms
     * @return all room
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Room> findAll() {
        List<Room> rooms = null;
        try {
            rooms = ejb.findAllRooms();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return rooms;
    }
    
    /**
     * Finds rooms with availableSpace different to 0
     * @return the rooms with available space
     */
    @GET
    @Path("availableRooms")
    @Produces(MediaType.APPLICATION_XML)
    public List<Room> findRoomsWithAvailableSpace() {
        List<Room> rooms = null;
        try {
            rooms = ejb.findRoomsWithAvailableSpace();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return rooms;
    }
}
