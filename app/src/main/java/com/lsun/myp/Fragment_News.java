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
    Button dongaBtn,jtbcBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,container,false);
        dongaBtn=view.findViewById(R.id.donga_btn);
        dongaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DongaActivity.class));
            }
        });
        jtbcBtn=view.findViewById(R.id.jtbc_btn);
        jtbcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), JTBCActivity.class));
            }
        });


        return view;
    }
}
