package com.example.karthik.mydashboard;


public class AvatarList {

    private int uImage;
    private String uTitle;

    public AvatarList(int image, String tiltle) {
        uImage = image;
        uTitle = tiltle;
    }

    public void setImage(int image) {
        uImage = image;
    }

    public int getImage() {
        return uImage;
    }


    public void setTitle(String title) {
        uTitle = title;
    }

    public String getTitle() {
        return uTitle;
    }

}