/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alberguePerronServer.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alatz
 */
@Entity
@Table(name="user",schema="dindb")
@NamedQueries({
    @NamedQuery(name="findAllUsers",
            query="SELECT u FROM User u ORDER BY u.name DESC"
    ),
    @NamedQuery(name="findUsersByProfile",
            query="SELECT u FROM User u WHERE u.profile = :profile"
    )
})
@XmlRootElement
public class User implements Serializable {
    private static long serialVersionUID = 1L;
    
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
    
     /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    
    /**
     * @param id the id to set
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
     * @return the surname1
     */
    public String getSurname1() {
        return surname1;
    }

    /**
     * @param surname1 the surname1 to set
     */
    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    /**
     * @return the surname2
     */
    public String getSurname2() {
        return surname2;
    }

    /**
     * @param surname2 the surname2 to set
     */
    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }
    
    /**
     * @return the privilege
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * @param privilege the privilege to set
     */
    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }
    
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * HashCode method implementation for the entity.
     * @return An integer value as hashcode for the object. 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getLogin().hashCode() : 0);
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
        if ((this.getLogin() == null && other.getLogin() != null) || 
            (this.getLogin() != null && !this.login.equals(other.login))) {
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
        return "javafxserverside.entity.User[ login=" + getLogin() + " ]";
    }
}
