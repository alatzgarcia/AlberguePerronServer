/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Room class for the AlberguePerronServer application
 * @author Alatz
 */
@Entity
@Table(name="room",schema="albergueperrondb")

@NamedQueries({
@NamedQuery(name="findAllRooms",
    query="SELECT r FROM Room r ORDER BY r.roomNum DESC"
),
@NamedQuery(name="findRoomsWithAvailableSpace",
        query="SELECT r FROM Room r WHERE r.availableSpace <> 0")})
@XmlRootElement
public class Room implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    private Integer roomNum;
    private Integer totalSpace;
    private Integer availableSpace;
    private Status status;
    @OneToMany(mappedBy="room")
    private List<Incident> incidents;
    @OneToMany(mappedBy="stayRoom")
    private List<Stay> stays;
    
    /**
     * Getter for the roomNum attribute
     * @return 
     */
    public Integer getRoomNum() {
        return roomNum;
    }
    
    /**
     * Setter for the roomNum attribute
     * @param roomNum the roomNumber to set
     */
    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    /**
     * Getter for the totalSpace attribute
     * @return the totalSpace
     */
    public Integer getTotalSpace() {
        return totalSpace;
    }

    /**
     * Setter for the totalSpace attribute
     * @param totalSpace the totalSpace to set
     */
    public void setTotalSpace(Integer totalSpace) {
        this.totalSpace = totalSpace;
    }

    /**
     * Getter for the availableSpace attribute
     * @return the availableSpace
     */
    public Integer getAvailableSpace() {
        return availableSpace;
    }

    /**
     * Getter for the availableSpace attribute
     * @param availableSpace the availableSpace to set
     */
    public void setAvailableSpace(Integer availableSpace) {
        this.availableSpace = availableSpace;
    }
    
    /**
     * Getter for the status attribute
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter for the status attribute
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    
    /**
     * Getter for the incidents attribute
     * @return the incidents
     */
    @XmlTransient
    public List<Incident> getIncidents() {
        return incidents;
    }

    /**
     * Setter for the incidents attribute
     * @param incidents the incidents to set
     */
    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    /**
     * Getter for the stays attribute
     * @return the stays
     */
    @XmlTransient
    public List<Stay> getStays() {
        return stays;
    }

    /**
     * Setter for the stays attribute
     * @param stays the stays to set
     */
    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }
    
    /**
     * Hash code method
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getRoomNum() != null ? getRoomNum().hashCode() : 0);
        return hash;
    }

    /**
     * Compares two objects to check if they are of type Room and if they
     * are the same object
     * @param object
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.getRoomNum() == null && other.getRoomNum() != null) || 
                (this.getRoomNum() != null && !this.roomNum.equals(other.roomNum))) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representing a room object
     * @return the room as a string
     */
    @Override
    public String toString() {
        //return "alberguePerronServer.entity.Room[ Room number=" + roomNum + " ]";
        return getRoomNum().toString();
    }
}