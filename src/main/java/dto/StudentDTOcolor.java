package dto;

import entities.Student;

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
}
