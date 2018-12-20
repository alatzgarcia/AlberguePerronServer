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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alatz
 */
@Entity
@Table(name="incident",schema="albergueperrondb")
public class Incident implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String incidentType;
    @ManyToMany
    @JoinTable(name="INCI_USERS")
    private List<User> implicateds;
    private String description;
    @ManyToOne
    private Room room;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Gets Incident Type of a room
     * @return the incidentType
     */
    public String getIncidentType() {
        return incidentType;
    }

    /**
     * Sets the Incident Type of an incidents
     * @param incidentType the incidentType to set
     */
    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    /**
     * Gets the implicateds of an incident
     * @return the implicateds
     */
    public List<User> getImplicateds() {
        return implicateds;
    }

    /**
     * Sets the implicateds of an incident
     * @param implicateds the implicateds to set
     */
    public void setImplicateds(List<User> implicateds) {
        this.implicateds = implicateds;
    }

    /**
     * Gets the description of an incident
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of an incident
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the room of the incident
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room of the incident
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incident)) {
            return false;
        }
        Incident other = (Incident) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alberguePerronServer.entity.Incident[ id=" + getId() + " ]";
    }
}
