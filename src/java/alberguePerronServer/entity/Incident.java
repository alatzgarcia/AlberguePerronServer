/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
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

    public void setIdNull(){
        this.id = null;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return the incidentType
     */
    public String getIncidentType() {
        return incidentType;
    }

    /**
     * @param incidentType the incidentType to set
     */
    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    /**
     * @return the implicateds
     */
    //@XmlTransient
    public List<User> getImplicateds() {
        return implicateds;
    }

    /**
     * @param implicateds the implicateds to set
     */
    public void setImplicateds(List<User> implicateds) {
        this.implicateds = implicateds;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
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