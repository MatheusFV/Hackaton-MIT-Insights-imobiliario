package br.com.baseproject.baseproject.Models;

/**
 * Created by luciana on 15/11/17.
 */

public class Place {

    public String id;
    public String photoUrl;
    public String address;
    public Integer spots;
    public Integer price;
    public String[] tags;

    public Place(String photoUrl, String address, Integer spots, Integer price) {
        this.photoUrl = photoUrl;
        this.address = address;
        this.spots = spots;
        this.price = price;
    }
}
