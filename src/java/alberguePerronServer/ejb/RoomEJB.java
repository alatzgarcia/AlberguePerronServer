/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.ejb;

import alberguePerronServer.entity.Room;
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
public class RoomEJB implements RoomEJBLocal{
    /**
     * Logger for the class.
     */
    private static final Logger LOGGER =
            Logger.getLogger("roomejb");
    /**
     * Entity manager object.
     */
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Room findRoomById(Integer roomNum) throws ReadException {
        Room room=null;
        try{
            LOGGER.info("RoomManager: Finding room by id.");
            room=em.find(Room.class, roomNum);
            LOGGER.log(Level.INFO,"RoomManager: Room found {0}",room.getRoomNum());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "RoomManager: Exception Finding room by room number:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return room;
    }
    
    @Override
    public List<Room> findAllRooms() throws ReadException {
        List<Room> rooms=null;
        try{
            LOGGER.info("RoomManager: Reading all rooms.");
            rooms=em.createNamedQuery("findAllRooms").getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "RoomManager: Exception reading all rooms:",
                    e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return rooms;
    }
   
    @Override
    public List<Room> findRoomsWithAvailableSpace() throws ReadException{
        List<Room> rooms = null;
        try{
            LOGGER.info("RoomManager: Reading rooms with available space.");
            rooms=em.createNamedQuery("findRoomsWithAvailableSpace")
                    .getResultList();
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "RoomManager: Exception reading"
                    + "rooms with available space.");
            throw new ReadException(e.getMessage());
        }
        return rooms;
    }
    
    @Override
    public void createRoom(Room room) throws CreateException {
        LOGGER.info("RoomManager: Creating room.");
        try{
            em.persist(room);
            LOGGER.info("RoomManager: Room created.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "RoomManager: Exception creating room.{0}",
                    e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }
    
    @Override
    public void updateRoom(Room room) throws UpdateException {
        LOGGER.info("RoomManager: Updating room.");
        try{
            //if(!em.contains(user))em.merge(user);
            em.merge(room);
            em.flush();
            LOGGER.info("RoomManager: Room updated.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "RoomManager: Exception updating room.{0}",
                    e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }
    
    @Override
    public void deleteRoom(Room room) throws DeleteException {
        LOGGER.info("RoomManager: Deleting room.");
        try{
            if(!em.contains(room))room=em.merge(room);
            em.remove(room);
            LOGGER.info("RoomManager: Room deleted.");
        }catch(Exception e){
            LOGGER.log(Level.SEVERE, "RoomManager: Exception deleting room.{0}",
                    e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }
}
