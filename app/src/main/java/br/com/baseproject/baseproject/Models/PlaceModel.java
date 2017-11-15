package br.com.baseproject.baseproject.Models;

/**
 * Created by teruya on 14/11/2017.
 */

public class PlaceModel {
    public String id;
    public String photoUrl;
    public String address;
    public Integer spots;
    public Integer price;
    public String[] tags;
    public TagRate[] historic;

    public PlaceModel(String photoUrl, String address, Integer spots, Integer price) {
        this.photoUrl = photoUrl;
        this.address = address;
        this.spots = spots;
        this.price = price;
    }
}
