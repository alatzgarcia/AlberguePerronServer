/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Alatz
 */
@Entity
@Table(name="room",schema="albergueperrondb")
public class Room implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roomNum;
    private Integer totalSpace;
    private Integer availableSpace;
    private String status;
    @OneToMany(mappedBy="room")
    private List<Incident> incidents;
    @OneToMany(mappedBy="room")
    private List<Stay> stays;
    /**
     * 
     * @return 
     */
    public Integer getRoomNum() {
        return roomNum;
    }
    
    /**
     * @param roomNum the roomNumber to set
     */
    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    /**
     * @return the totalSpace
     */
    public Integer getTotalSpace() {
        return totalSpace;
    }

    /**
     * @param totalSpace the totalSpace to set
     */
    public void setTotalSpace(Integer totalSpace) {
        this.totalSpace = totalSpace;
    }

    /**
     * @return the availableSpace
     */
    public Integer getAvailableSpace() {
        return availableSpace;
    }

    /**
     * @param availableSpace the availableSpace to set
     */
    public void setAvailableSpace(Integer availableSpace) {
        this.availableSpace = availableSpace;
    }
    
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * @return the incidents
     */
    public List<Incident> getIncidents() {
        return incidents;
    }

    /**
     * @param incidents the incidents to set
     */
    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    /**
     * @return the stays
     */
    public List<Stay> getStays() {
        return stays;
    }

    /**
     * @param stays the stays to set
     */
    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getRoomNum() != null ? getRoomNum().hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "alberguePerronServer.entity.Room[ Room number=" + roomNum + " ]";
    }
}