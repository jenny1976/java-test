package com.upday.newsapi.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author jschulz
 */
public class RsAuthor implements Serializable {
    
    private static final long serialVersionUID = 1642920232195642145L;

    private Long id;
    private String firstname;
    private String lastname;

    public RsAuthor() {
        // default constructor 
    }

    public RsAuthor(Long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "RsAuthor{" + "id=" + id + ", firstname=" + firstname + 
                ", lastname=" + lastname + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.firstname);
        hash = 29 * hash + Objects.hashCode(this.lastname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RsAuthor other = (RsAuthor) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }
        return true;
    }
}
