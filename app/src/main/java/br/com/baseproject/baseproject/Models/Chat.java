package br.com.baseproject.baseproject.Models;

/**
 * Created by mvenosa on 15/11/17.
 */

public class Chat {
    public String message;
    public String name;
    public String photoUrl;
    public String posterId;

    public Chat() {
    }

    public Chat(String message, String name, String photoUrl, String posterId) {
        this.message = message;
        this.name = name;
        this.photoUrl = photoUrl;
        this.posterId = posterId;
    }
}
