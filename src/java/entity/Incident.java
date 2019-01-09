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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dam
 */
@Entity
@Table(name="incident",schema="albergueperrondb")

public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String incidentType;
    @ManyToMany
    @JoinTable(name="INCI_USERS",schema="albergueperrondb")
    private List <User> implicateds;
    private String description;
    @ManyToOne
    private Room room;
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incident)) {
            return false;
        }
        Incident other = (Incident) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Incident[ id=" + id + " ]";
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public List<User> getImplicateds() {
        return implicateds;
    }

    public String getDescription() {
        return description;
    }

    

    public Room getRoom() {
        return room;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public void setImplicateds(List<User> implicateds) {
        this.implicateds = implicateds;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   

    public void setRoom(Room room) {
        this.room = room;
    }
    
    
}
