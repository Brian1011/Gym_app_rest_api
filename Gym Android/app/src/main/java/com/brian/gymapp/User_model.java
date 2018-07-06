package com.brian.gymapp;

public class User_model {
    private int id; //user_id
    private String firstname,lastname,email,age,gender,preffered_location;
    private double target_weight, weight;

    public User_model(int id, String firstname, String lastname, String email, String age, String gender, String preffered_location, double target_weight, double weight) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.preffered_location = preffered_location;
        this.target_weight = target_weight;
        this.weight = weight;
    }


    //testing controller
    public User_model(int id, String firstname, String lastname, String email, String age, String gender, String preffered_location) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.preffered_location = preffered_location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPreffered_location() {
        return preffered_location;
    }

    public void setPreffered_location(String preffered_location) {
        this.preffered_location = preffered_location;
    }

    public double getTarget_weight() {
        return target_weight;
    }

    public void setTarget_weight(double target_weight) {
        this.target_weight = target_weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
