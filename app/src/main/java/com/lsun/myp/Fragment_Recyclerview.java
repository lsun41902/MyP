package com.lsun.myp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;


public class Fragment_Recyclerview extends Fragment {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    AdapterMember adapter;
    public static final int REQ_WIRTE = 10;
    ArrayList<MyMember> members = new ArrayList<>();
    SwipeRefreshLayout swiper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerview_item);
//        recyclerView.setAdapter(adapter);
        swiper = view.findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "게시판 새로고침", Toast.LENGTH_SHORT).show();
                adapter.notifyItemRangeChanged(0,10);
                recyclerView.setAdapter(adapter);
                swiper.setRefreshing(false);

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                startActivityForResult(intent, REQ_WIRTE);
            }
        });
        return view;

    }//onCreateView

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_WIRTE:
                if(resultCode== MainActivity.RESULT_OK){
                    String title=data.getStringExtra("Title");
                    members.add(0,new MyMember(title,null,null));
                    adapter=new AdapterMember(getActivity(),members);
                    recyclerView.setAdapter(adapter);
                }
                break;
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //members.add(0, new MyMember(null, null, null));
    }






}
