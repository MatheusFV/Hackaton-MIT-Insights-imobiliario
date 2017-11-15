package br.com.baseproject.baseproject.Models;

/**
 * Created by luciana on 15/11/17.
 */

public class Place {

    public String id;
    public String imageUrl;
    public String address;
    public String slots;
    public String price;
    public String status;

    public Place() {
    }

    public Place(String photoUrl, String address, String spots, String price, String[] tags) {
        this.imageUrl = photoUrl;
        this.address = address;
        this.slots = spots;
        this.price = price;
//        this.tags = tags;
    }


    public Place(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public Place(String address, String slots, String price, String status) {
        this.address = address;
        this.slots = slots;
        this.price = price;
        this.status = status;
    }

    public Place(String url, String address, String slots, String price, String status, String[] tags) {
        this.address = address;
        this.slots = slots;
        this.price = price;
        this.status = status;
        this.imageUrl = url;
    }




}
