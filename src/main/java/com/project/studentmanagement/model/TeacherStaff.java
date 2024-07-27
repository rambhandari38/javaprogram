package com.project.studentmanagement.model;

public class TeacherStaff {
    private final String teacherID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String subjectTaught;
    private int experience;

    public TeacherStaff(String teacherID, String firstName, String lastName, String email, String phone,
                        String department, String subjectTaught, int experience) {
        this.teacherID = teacherID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.subjectTaught = subjectTaught;
        this.experience = experience;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubjectTaught() {
        return subjectTaught;
    }

    public void setSubjectTaught(String subjectTaught) {
        this.subjectTaught = subjectTaught;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}