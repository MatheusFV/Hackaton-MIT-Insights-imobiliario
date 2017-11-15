package br.com.baseproject.baseproject.Models;

/**
 * Created by giova on 12/11/2017.
 */

public class User {

    public String id;
    public String name;
    public String phone;
    public String email;

    public User(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
