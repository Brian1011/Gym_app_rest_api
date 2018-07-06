package com.brian.gymapp;

//model to store the fetched data into objects
public class InstructorsUtils {
    private String instructor_name;
    private String instructor_contacts;
    private String email;
    private String gender;
    private String gym_name;
    private String instructor_image;

    public String getInstructor_image() {
        return instructor_image;
    }

    public void setInstructor_image(String instructor_image) {
        this.instructor_image = instructor_image;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getInstructor_contacts() {
        return instructor_contacts;
    }

    public void setInstructor_contacts(String instructor_contacts) {
        this.instructor_contacts = instructor_contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGym_name() {
        return gym_name;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
    }
}

