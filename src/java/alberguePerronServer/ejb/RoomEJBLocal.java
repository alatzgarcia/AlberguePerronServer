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
 *
 * @author Alatz
 */
@Local
public interface RoomEJBLocal {
    public Room findRoomById(Integer roomNum) throws ReadException;
    public List<Room> findAllRooms() throws ReadException;
    public List<Room> findRoomsWithAvailableSpace() throws ReadException;
    public void createRoom(Room room) throws CreateException;
    public void updateRoom(Room room) throws UpdateException;
    public void deleteRoom(Room room) throws DeleteException;
}
