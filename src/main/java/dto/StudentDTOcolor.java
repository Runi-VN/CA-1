package dto;

import entities.Student;
import java.util.Objects;

/**
 *
 * @author Camilla
 */
public class StudentDTOcolor {
    private String studentID;
    private String name;
    private String github;
    private String color;
    
    public StudentDTOcolor(Student member) {
        this.studentID = member.getStudentID();
        this.name = member.getName();
        this.github = member.getGithub();
        this.color = member.getColor();
    }

    public StudentDTOcolor() {
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.studentID);
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.github);
        hash = 19 * hash + Objects.hashCode(this.color);
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
        final StudentDTOcolor other = (StudentDTOcolor) obj;
        if (!Objects.equals(this.studentID, other.studentID)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.github, other.github)) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return true;
    }
    
}
