/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Malte
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "WhoDidWhat.getAll", query = "SELECT w FROM WhoDidWhat w"),
    @NamedQuery(name = "WhoDidWhat.getByName", query = "SELECT w FROM WhoDidWhat w WHERE w.name LIKE :name")})
public class WhoDidWhat implements Serializable {

    // DEFAULT EMPTY CONSTRUCTOR
    public WhoDidWhat() {
    }

    // FIELDS
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private List<String> done;

    public WhoDidWhat(String name) {
        this.name = name;
    }

    public List<String> getDone() {
        return done;
    }

    public void setDone(List<String> done) {
        this.done = done;
    }

    public List<String> addDone(String whatIDid) {
        if (this.done != null) {
            done.add(whatIDid);
        } else {
            this.done = new ArrayList<>();
            done.add(whatIDid);
        }
        return done;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.done);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WhoDidWhat other = (WhoDidWhat) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.done, other.done)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WhoDidWhat{" + "id=" + id + ", name=" + name + ", done=" + done + '}';
    }

}
