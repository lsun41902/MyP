package com.lsun.myp;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.lsun.myp.Society.DongA.DongaActivity;
import com.lsun.myp.Society.JTBC.JTBCActivity;

public class Fragment_News extends Fragment {
    Button dongaSocietyBtn,jtbcSocietyBtn,jtbcPoliticsBtn,jtbcSportsBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,container,false);
        dongaSocietyBtn=view.findViewById(R.id.donga_society_btn);
        dongaSocietyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DongaActivity.class));
            }
        });
        jtbcSocietyBtn=view.findViewById(R.id.jtbc_society_btn);
        jtbcSocietyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), JTBCActivity.class));
            }
        });
        jtbcPoliticsBtn=view.findViewById(R.id.jtbc_politics_btn);
        jtbcPoliticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), com.lsun.myp.Politics.JTBC.JTBCActivity.class));
            }
        });
        jtbcSportsBtn=view.findViewById(R.id.jtbc_sports_btn);
        jtbcSportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), com.lsun.myp.Sports.JTBC.JTBCActivity.class));
            }
        });


        return view;
    }
}
