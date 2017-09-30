package com.vivo.yzz.music.music;

/**
 * Created by Administrator on 2017/9/25.
 */

public class Music {
    private String title;
    private String album;
    private String author;
    private String imageUri;
    private String data;
    private int  duration;
    private long id;
    private long size;
    private String albumKey;
    private String lrc;

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public Music(){
        super();
    }

    public String getAlbumKey() {
        return albumKey;
    }

    public void setAlbumKey(String albumKey) {
        this.albumKey = albumKey;
    }

    public int getDuration() {
        return duration;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getSize() {
        return size;
    }

    public String getAlbum() {
        return album;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
