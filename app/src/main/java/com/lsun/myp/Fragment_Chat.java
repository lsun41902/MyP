package com.lsun.myp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Fragment_Chat extends Fragment {

    ListView listView;
    EditText et;
    ArrayList<ItemChat> messageItems = new ArrayList<>();
    AdapterChat adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatRef;
    Button chatBtn;
    String username;
    Date time=new Date(System.currentTimeMillis());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        listView = view.findViewById(R.id.listview);
        et = view.findViewById(R.id.et);
        chatBtn=view.findViewById(R.id.chatbtn);
        adapter = new AdapterChat(messageItems, getLayoutInflater());
        listView.setAdapter(adapter);
        SharedPreferences sp=getActivity().getSharedPreferences("userName", Context.MODE_PRIVATE);
        username=sp.getString("userNickname",null);
        //FirebaseDB 관리객체와 chat 노드 참조객체
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatRef = firebaseDatabase.getReference("chat");

        //firebaseDB 에서 채팅 메세지들 실시간 읽어오기.
        //닉네임, 메세지, 프로필 이미지 저장 URL, 작성시간

        //'chat' 노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef 에 데이터가 변경되는 것을 듣는 리스너 추가
        //새로 추가된 child 만 얻어오기 value 하면 전체를 다 읽어옴
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // 새로 추가된 데이터(값: MessageItem객체)
                ItemChat messageItem = dataSnapshot.getValue(ItemChat.class);
                //새로운 메세지를 리스트뷰에 추가하기 ArrayList에 추가하기
                messageItems.add(messageItem);
                //리스트뷰 갱신
                adapter.notifyDataSetChanged();
                listView.setSelection(messageItems.size() - 1);//화면 포커스 이동
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = username;
                String message = et.getText().toString();
                String profileUrl = ItemChat.Url;
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA);
                // nowDate 변수에 값을 저장한다.
                String formatDate = sdfNow.format(time);
                //메세지 작성 시간 문자열로

                //firebase DB에 저장할 값(MessageItem객체) 설정
                ItemChat messageItem = new ItemChat(nickName, message, formatDate, profileUrl);

                //firebase profiles
                //chat 노드에 messageItem객체를 통째로 추가(push)하겠다
                chatRef.push().setValue(messageItem);//이친구의 좋은점 다 때려박으면 됨
                //EditText에 있는 글씨 지우기
                et.setText("");
                //소프트 키패드를 안보이도록.
//        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

                //처음 시작할 때 EditText가 다른 뷰들보다 우선시 되어 포커스를 받아버림
                //즉 시작부터 소프트 키패드가 올라와 있습니다.
                //그게 싫으면 다른뷰가 포커스를 가지도록
                //즉 edittext를 감싼 layout에게 포커스를 가지도록 속성을 추가
            }
        });

        return view;

    }

}





