package com.lsun.myp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Fragment_Recyclerview extends Fragment {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    AdapterMember adapter;
    public static final int REQ_WIRTE = 10;
    ArrayList<MyMember> members = new ArrayList<>();
    SwipeRefreshLayout swiper;
    Uri Img1,img2,img3;
    String userid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        fab = view.findViewById(R.id.fab);
        loadDB();
        recyclerView = view.findViewById(R.id.recyclerview_item);
        adapter=new AdapterMember(getActivity(),members);
        recyclerView.setAdapter(adapter);
//        recyclerView.setAdapter(adapter);
        swiper = view.findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "게시판 새로고침", Toast.LENGTH_SHORT).show();
                //adapter.notifyItemRangeChanged(0,10);
                loadDB();
                adapter.notifyDataSetChanged();
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
                    String text=data.getStringExtra("Text");
                    String date=data.getStringExtra("Date");
                    SharedPreferences sp=getActivity().getSharedPreferences("userName",Context.MODE_PRIVATE);
                    String nickname=sp.getString("userNickname",null);
                    Uri img1=data.getParcelableExtra("Image1");
                    Uri img2=data.getParcelableExtra("Image2");
                    Uri img3=data.getParcelableExtra("Image3");
                    userid=data.getStringExtra("userID");
                    members.add(0,new MyMember(null,null,title,nickname,date,null,null,null,img1,img2,img3,text,null));
                    adapter=new AdapterMember(getActivity(),members);
                    recyclerView.setAdapter(adapter);
                }
                break;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if(members.size()!=0) {
            //adapter.notifyItemRangeChanged(0,10);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
    }
    void loadDB(){
        new Thread(){
            @Override
            public void run() {
                String serverUrl="http://lsun41902.dothome.co.kr/GotoWork/Board/boardloadDB.php";
                try {
                    URL url=new URL(serverUrl);
                    HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    //connection.setDoOutput(true);//outputStream 필요하면 사용
                    connection.setUseCaches(false);//트래픽 잠식요소 있음
                    InputStream is=connection.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader reader=new BufferedReader(isr);
                    final StringBuffer buffer=new StringBuffer();
                    String line=reader.readLine();
                    while (line!=null){
                        buffer.append(line+"\n");
                        line=reader.readLine();
                    }
                    //읽어오는게 작업이 성공했는지 확인 작업
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(getActivity()).setMessage(buffer.toString()).create().show();
                        }
                    });

                    //대량의 데이터 초기화
                    members.clear();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

                    //읽어온 데이터 문자열에서 row별로 분리하기
                    String[] rows=buffer.toString().split(";");
                    for(String row: rows){
                        //한줄 데이터 에서 한 칸씩 분리 시키기
                        String[] datas=row.split("&");
                        if(datas.length!=10) continue;
                        Object no=Integer.parseInt(datas[0]);
                        String profileimg="http://lsun41902.dothome.co.kr/html/GotoWork/Board/"+datas[1];
                        String title=datas[2];
                        String nickname=datas[3];
                        String date=datas[4];
                        String img1="http://lsun41902.dothome.co.kr/html/GotoWork/Board/"+datas[5];
                        String img2="http://lsun41902.dothome.co.kr/html/GotoWork/Board/"+datas[6];
                        String img3="http://lsun41902.dothome.co.kr/html/GotoWork/Board/"+datas[7];
                        String text=datas[8];
                        String user=datas[9];
                        members.add(new MyMember(no,null,title,nickname,date,null,null,null,null,null,null,text,user));

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
