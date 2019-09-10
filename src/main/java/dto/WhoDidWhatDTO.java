/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.WhoDidWhat;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Malte
 */
public class WhoDidWhatDTO {

    public WhoDidWhatDTO(WhoDidWhat entity) {
        this.name = entity.getName();
        this.done = entity.getDone();
    }

    private String name;
    private List<String> done;

    public List<String> getDone() {
        return done;
    }

    public void setDone(List<String> done) {
        this.done = done;
    }

    public List<String> addDone(String whatIDid) {
        done.add(whatIDid);
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.done);
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
        final WhoDidWhatDTO other = (WhoDidWhatDTO) obj;
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
        return "WhoDidWhatDTO{" + "name=" + name + ", done=" + done + '}';
    }

}
