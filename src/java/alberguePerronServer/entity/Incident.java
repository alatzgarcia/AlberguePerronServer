/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Incident class for the AlberguePerronServer application
 * @author Alatz
 */
@Entity
@Table(name="incident",schema="albergueperrondb")
@NamedQuery(name="findAllIncidents",
    query="SELECT i FROM Incident i ORDER BY i.id DESC"
)
@XmlRootElement
public class Incident implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String incidentType;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="INCI_USERS", schema="albergueperrondb")
    private List<User> implicateds;
    private String description;
    @ManyToOne
    private Room room;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    /**
     * Method to set the id to null when receiving an incident from the client
     */
    public void setIdNull(){
        this.id = null;
    }
    
    /**
     * Getter for the id attribute
     * @return 
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter for the id attribute
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Getter for the incidentType attribute
     * @return the incidentType
     */
    public String getIncidentType() {
        return incidentType;
    }

    /**
     * Setter for the incidentType attribute
     * @param incidentType the incidentType to set
     */
    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    /**
     * Getter for the implicateds attribute
     * @return the implicateds
     */
    //@XmlTransient
    public List<User> getImplicateds() {
        return implicateds;
    }

    /**
     * Setter for the implicateds attribute
     * @param implicateds the implicateds to set
     */
    public void setImplicateds(List<User> implicateds) {
        this.implicateds = implicateds;
    }

    /**
     * Getter for the description attribute
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description attribute
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Getter for the room attribute
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Setter for the room attribute
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }
    
    /**
     * Getter for the date attribute
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for the date attribute
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * Hash code method
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    /**
     * Compares two objects to check if they are of type Incident and if they
     * are the same object
     * @param object
     * @return 
     */
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

    /**
     * Returns a string representing an incident object
     * @return the incident as a string
     */
    @Override
    public String toString() {
        return ("Incident id: " + this.getId().toString());
    }
}