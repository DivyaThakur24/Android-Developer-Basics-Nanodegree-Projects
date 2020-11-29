package com.example.android.soundtrack;

class SongsOrder {

    private int ImageId;
    private int Name;

    SongsOrder(int imageId, int name) {
        this.ImageId = imageId;
        this.Name = name;
    }

    int putTheImage() {
        return ImageId;
    }

    int putTheTitle() {
        return Name;
    }
}
