package com.lsun.myp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class PicImage {
    Context context;
    Activity activity;
    public static final int REQ_PIC=1000;
    public PicImage(Context context) {
        this.context=context;
    }
    void PicSetting(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        activity.startActivityForResult(intent,REQ_PIC);
    }


}
