package br.com.baseproject.baseproject.Models;

/**
 * Created by teruya on 14/11/2017.
 */

public class TagRate {
    public String tag;
    public Integer counter;

    public TagRate(String tag, Integer counter) {
        this.tag = tag;
        if(counter == null){
            this.counter = 0;
        } else{
            this.counter = counter;
        }
    }
}
