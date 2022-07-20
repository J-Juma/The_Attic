package com.hfad.theattic;

public class Attic {
    private String title, desc, postImage, displayName, profilePhoto, time, date;
    // creating a constructor
    public Attic(String title, String desc, String postImage, String displayName,
                 String profilePhoto, String time, String date) {
        this.title = title;
        this.desc = desc;
        this.postImage=postImage;
        this.displayName = displayName;
        this.profilePhoto=profilePhoto;
        this.time=time;
        this.date=date;
    }
    //requires an empty constructor
    public Attic() {
    }
    // setters
    public void setPostImage(String postImage){
        this.postImage=postImage;
    }
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setDate(String date){
        this.date=date;
    }
    //getters
    public String getDisplayName() {
        return displayName;
    }
    public String getPostImage() {
        return postImage;
    }
    public String getTitle() {
        return title;
    }
    public String getDesc() {
        return desc;
    }
    public String getProfilePhoto()
    {
        return profilePhoto;
    }
    public String getTime(){
        return time;
    }
    public String getDate(){
        return date;
    }
}
