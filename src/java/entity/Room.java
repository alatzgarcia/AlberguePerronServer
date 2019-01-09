/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dam
 */
@Entity
@Table(name="room",schema="albergueperrondb")
@XmlRootElement
public class Room implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roomNum;
    private Integer totalSpace;
    private Integer availableSpace;
    private String status;
    @OneToMany (mappedBy="room")
    private List <Incident> incidents;
    @OneToMany (mappedBy="room")
    private List <Stay> stays;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomNum != null ? roomNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomNum == null && other.roomNum != null) || (this.roomNum != null && !this.roomNum.equals(other.roomNum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Room[ id=" + roomNum + " ]";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public Integer getTotalSpace() {
        return totalSpace;
    }

    public Integer getAvailableSpace() {
        return availableSpace;
    }

    public String getStatus() {
        return status;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public void setTotalSpace(Integer totalSpace) {
        this.totalSpace = totalSpace;
    }

    public void setAvailableSpace(Integer availableSpace) {
        this.availableSpace = availableSpace;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    public List<Stay> getStays() {
        return stays;
    }

    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }
    
    
}
