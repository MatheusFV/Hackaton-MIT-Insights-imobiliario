package br.com.baseproject.baseproject.Models;

/**
 * Created by mvenosa on 15/11/17.
 */

public class UserTags {
    public String age;
    public boolean animal;
    public String ocupation;
    public boolean smoke;
    public String gender;

    public UserTags(String age, boolean animal, String ocupation, boolean smoke, String gender) {
        this.age = age;
        this.animal = animal;
        this.ocupation = ocupation;
        this.smoke = smoke;
        this.gender = gender;
    }
}
