package com.lsun.myp;

import android.net.Uri;

public class ImageUri {
    Object image1;
    Object image2;
    Object image3;

    public ImageUri() {
    }

    public ImageUri(Object image1, Object image2, Object image3) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Object getImage1() {
        return image1;
    }

    public void setImage1(Object image1) {
        this.image1 = image1;
    }

    public Object getImage2() {
        return image2;
    }

    public void setImage2(Object image2) {
        this.image2 = image2;
    }

    public Object getImage3() {
        return image3;
    }

    public void setImage3(Object image3) {
        this.image3 = image3;
    }
}
