package br.com.baseproject.baseproject.Models;

/**
 * Created by giova on 12/11/2017.
 */

public class User {

    public String id;
    public String name;
    public String phone;
    public String email;
    public String gender;
    public String photoUrl;
    public String age;
    public boolean animal;
    public String ocupation;
    public boolean smoke;

    public User(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public User(String name, String phone, String email, String gender, String age, boolean animal, String ocupation, boolean smoke) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.animal = animal;
        this.ocupation = ocupation;
        this.smoke = smoke;
    }
}
