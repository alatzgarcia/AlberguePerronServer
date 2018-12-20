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
 * @author 2dam
 */
@Entity
@Table(name="room",schema="albergueperrondb")
public class Room implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roomNum;
    private Integer totalSpace;
    private Integer available;
    private String status;
    @OneToMany(mappedBy = "room")
    private List<Incident> incidents;
    @OneToMany(mappedBy = "room")
    private List<Stay> stays;
    
    //Getters
    public Integer getId() {
        return roomNum;
    }

    public Integer getTotalSpace() {
        return totalSpace;
    }

    public Integer getAvailable() {
        return available;
    }

    public String getStatus() {
        return status;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public List<Stay> getStays() {
        return stays;
    }
    
    
    
    
    //Setters
    public void setId(Integer id) {
        this.roomNum = id;
    }

    public void setTotalSpace(Integer totalSpace) {
        this.totalSpace = totalSpace;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }
    
    

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
        return "alberguePerronServer.entity.Room[ id=" + roomNum + " ]";
    }
    
}
