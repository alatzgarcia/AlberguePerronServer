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
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author 2dam
 */
@Entity
@Table(name="user",schema="albergueperrondb")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String surname1;
    private String surname2;
    @Enumerated
    private Privilege privilege;
    private Date lastPasswordChange;
    @ManyToMany(mappedBy="implicateds")
    private List<Incident> incidents;
    private String login;
    private String email;
    private String password;
    @OneToMany (mappedBy = "owner")
    private List<User> pets;
    @OneToMany (mappedBy = "guest")
    private List<Stay> stays;
    
    //Getters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname1() {
        return surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public List<User> getPets() {
        return pets;
    }

    public List<Stay> getStays() {
        return stays;
    }
    
    
    
    //Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    public void setPets(List<User> pets) {
        this.pets = pets;
    }

    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "alberguePerronServer.entity.User[ id=" + id + " ]";
    }
    
}
