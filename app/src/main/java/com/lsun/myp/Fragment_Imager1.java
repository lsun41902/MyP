package com.lsun.myp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.lsun.myp.DongA.DongaActivity;
import com.lsun.myp.JTBC.JTBCActivity;

public class Fragment_Imager1 extends Fragment {
    ImageView iv;
    ImageUri imageUri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recyclerview_pager_image1,container,false);
        iv=view.findViewById(R.id.pager_iv);
        Glide.with(this).load(imageUri.getImage1()).into(iv);
        return view;
    }
}
