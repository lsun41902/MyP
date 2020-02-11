package com.lsun.myp;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Fragment_Recyclerview extends Fragment {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    AdapterMember adapter;
    public static final int REQ_WIRTE = 1010;
    ArrayList<MyMember> members = new ArrayList<>();
    SwipeRefreshLayout swiper;
    String userid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference board;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        fab = view.findViewById(R.id.fab);
        loadfirebase();
        recyclerView = view.findViewById(R.id.recyclerview_item);
        adapter=new AdapterMember(getActivity(),members);
        recyclerView.setAdapter(adapter);
        swiper = view.findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "게시판 새로고침", Toast.LENGTH_SHORT).show();
                loadfirebase();
                swiper.setRefreshing(false);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }//onCreateView

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    
    @Override
    public void onResume() {
        super.onResume();
        if(members.size()!=0) {
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
    }

    void loadfirebase(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        board= firebaseDatabase.getReference("board");
        board.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("imagedown","123123");
                members.clear();
                adapter.notifyDataSetChanged();
                for(DataSnapshot t: dataSnapshot.getChildren()){
                    MyMember myMembers=t.getValue(MyMember.class);
                    members.add(myMembers);
                }
                Collections.reverse(members);
//                adapter.notifyItemChanged(members.size()-1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
