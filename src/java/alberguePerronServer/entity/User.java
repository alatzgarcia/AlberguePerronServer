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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * User class for AlberguePerronServer application
 * @author Nerea Jimenez
 */
@Entity
@Table(name="user",schema="albergueperrondb")
@NamedQueries({
    @NamedQuery(name="findAllUsers",
            query="SELECT u FROM User u ORDER BY u.name DESC"
    ),
    @NamedQuery(name="findUserByLogin",
            query="SELECT u FROM User u WHERE u.login = :login"
    ),
    @NamedQuery(name="findUserByEmail",
            query="SELECT u FROM User u WHERE u.email = :email"
    ),
    
  
})
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String name;
    private String surname1;
    private String surname2;
    @Enumerated(EnumType.ORDINAL)
    private Privilege privilege;
    private String login;
    private String email;
    private String password;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastPasswordChange;
    @ManyToMany(mappedBy="implicateds")
    private List<Incident> incidents;
    @OneToMany(mappedBy="owner")
    private List<Pet> pets;
    @OneToMany(mappedBy="guest")
    private List<Stay> stays;
    
    /**
     * Gets id value for user.
     * @return The id value.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id value for user.
     * @param id The id value.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets name value for user.
     * @return The name value.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets name value for user.
     * @param name The name value.
     */
    public void setName(String name) {
        this.name = name;
    }
    
     /**
      * Gets surname1 value for user.
      * @return The surname1 value.
      */
    public String getSurname1() {
        return surname1;
    }

    /**
     * Sets surname1 value for user.
     * @param surname1 The surname1 Vvalue.
     */
    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    /**
     * Gets surname2 value for user.
     * @return The surname2 value.
     */
    public String getSurname2() {
        return surname2;
    }

    /**
     * Sets surname2 value for user.
     * @param surname2 The surname2 value.
     */
    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }
    
    /**
     * Gets privilege value for user.
     * @return The privilege value.
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * Sets privilege value for user.
     * @param privilege The privilege value.
     */
    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
    
    /**
     * Gets login value for user.
     * @return The login value.
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * Sets login value for user.
     * @param login The login value.
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
    /**
     * Gets email value for user.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email value for user.
     * @param email The email value.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password value for user.
     * @return The password value.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password value for user.
     * @param password The password user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Gets lastPasswordChange value for user.
     * @return The lastPasswordChange value.
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * Sets lastPasswordChange value for user.
     * @param lastPasswordChange The lastPasswordChange value.
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }
    
    /**
     * Gets incidents value for user.
     * @return The incidents value.
     */
    @XmlTransient
    public List<Incident> getIncidents() {
        return incidents;
    }

    /**
     * Sets incidents value for user.
     * @param incidents The incidents value.
     */
    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }
    
    /**
     * @return the pets
     */
    @XmlTransient
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * @param pets the pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     * @return the stays
     */
    @XmlTransient
    public List<Stay> getStays() {
        return stays;
    }

    /**
     * @param stays the stays to set
     */
    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }
    
    /**
     * HashCode method implementation for the entity.
     * @return An integer value as hashcode for the object. 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    /**
     * This method compares two user entities for equality. This implementation
     * compare login field value for equality.
     * @param object The object to compare to.
     * @return True if objects are equals, otherwise false.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * This method returns a String representation for a user entity instance.
     * @return The String representation for the user object. 
     */
    @Override
    public String toString() {
        return "alberguePerronServer.entity.User[ id=" + id + " ]";
    }
}