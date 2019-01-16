/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alatz
 */
@Entity
@Table(name="stay",schema="albergueperrondb")
@NamedQuery(name="findAllStays", query="SELECT s FROM Stay s ORDER BY s.id ASC")
@XmlRootElement
public class Stay implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private User guest;
    @ManyToOne
    private Room stayRoom;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets guest value for stay.
     * @return The guest value.
     */
    public User getGuest() {
        return guest;
    }

    /**
     * Sets guest value for stay.
     * @param guest The guest value.
     */
    public void setGuest(User guest) {
        this.guest = guest;
    }

    /**
     * Gets room value for stay.
     * @return The room value.
     */
    public Room getStayRoom() {
        return stayRoom;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(Room stayRoom) {
        this.stayRoom = stayRoom;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof Stay)) {
            return false;
        }
        Stay other = (Stay) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alberguePerronServer.entity.Stay[ id=" + getId() + " ]";
    }
}
