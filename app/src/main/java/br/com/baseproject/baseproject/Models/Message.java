package br.com.baseproject.baseproject.Models;

import android.graphics.Bitmap;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

/**
 * Created by mvenosa on 15/11/17.
 */

public class Message implements IMessage,
        MessageContentType.Image, /*this is for default image messages implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {

    private String id;
    private String text;
    private Date createdAt;
    private Author author;
    private Image image;
    private Bitmap localImage;
    private Voice voice;
    private boolean isLoading = false;

    public Message(String id, Author author, String text) {
        this(id, author, text, new Date(), false);
    }

    public Message(String id, Author author, String text, Date createdAt, boolean isLoading) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.createdAt = createdAt;
        this.isLoading = isLoading;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public Author getUser() {
        return this.author;
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }

    public Bitmap getLocalImage() {
        return this.localImage;
    }

    public Voice getVoice() {
        return voice;
    }

    public String getStatus() {
        return "Sent";
    }

    public boolean getIsLoading() {
        return isLoading;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImageUrl(String url) {
        if (image != null) {
            image.setUrl(url);
        }
    }

    public void setLocalImage(Bitmap bitmap) {
        this.localImage = bitmap;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }

        public void setUrl(String url) {this.url = url;}
    }

    public static class Voice {

        private String url;
        private int duration;

        public Voice(String url, int duration) {
            this.url = url;
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }


        public int getDuration() {
            return duration;
        }
    }

}