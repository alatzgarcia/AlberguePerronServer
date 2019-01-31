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
import javax.ejb.Local;

/**
 * RoomEJB interface for the AlberguePerronServer application
 * @author Alatz
 */
@Local
public interface RoomEJBLocal {
    /**
     * Finds a room by its id
     * @param roomNum the room id
     * @return the room object
     * @throws ReadException 
     */
    public Room findRoomById(Integer roomNum) throws ReadException;
    /**
     * Finds all rooms
     * @return the rooms
     * @throws ReadException 
     */
    public List<Room> findAllRooms() throws ReadException;
    /**
     * Finds rooms with availableSpace different to 0
     * @return the rooms with availableSpace
     * @throws ReadException 
     */
    public List<Room> findRoomsWithAvailableSpace() throws ReadException;
    /**
     * Creates a new room
     * @param room the new room object
     * @throws CreateException 
     */
    public void createRoom(Room room) throws CreateException;
    /**
     * Updates a room
     * @param room the room object
     * @throws UpdateException 
     */
    public void updateRoom(Room room) throws UpdateException;
    /**
     * Deletes a room
     * @param room the room to delete
     * @throws DeleteException 
     */
    public void deleteRoom(Room room) throws DeleteException;
}
