package br.com.baseproject.baseproject.Models;

/**
 * Created by giova on 12/11/2017.
 */

public class User {

    public String id;
    public String name;
    public String phone;
    public String email;
    public UserTags tags;
    public String photoUrl;
    public String status;


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
        this.tags = new UserTags(age, animal, ocupation, smoke, gender);
    }

    public User(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.status = "pending";
    }
}
