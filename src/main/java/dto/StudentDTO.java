package dto;

import entities.Student;
import java.util.Objects;
/**
 *
 * @author Camilla
 */
public class StudentDTO {
    private String studentID;
    private String name;
    private String github;
    
    public StudentDTO(Student member) {
        this.studentID = member.getStudentID();
        this.name = member.getName();
        this.github = member.getGithub();
    }

    public StudentDTO() {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.studentID);
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.github);
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
        final StudentDTO other = (StudentDTO) obj;
        if (!Objects.equals(this.studentID, other.studentID)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.github, other.github)) {
            return false;
        }
        return true;
    }

    
}
