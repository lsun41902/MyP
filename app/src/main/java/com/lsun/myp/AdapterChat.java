package com.lsun.myp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChat extends BaseAdapter {
    Context context;
    ArrayList<ItemChat> messageItems;
    LayoutInflater layoutInflater;
    public AdapterChat(ArrayList<ItemChat> messageItems, LayoutInflater layoutInflater) {
        this.messageItems = messageItems;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemChat item=messageItems.get(position);//채팅글 하나


        //메세지가 내 메세지인지?? 확인 작업
        View itemView=null;//view 를 재활용 하면 안되니깐 새로운 뷰를 생성. 재활용하면 꼬임
        if(item.getName().equals(StartProfileActivity.startusernickname)) {
            itemView=layoutInflater.inflate(R.layout.box_msg_my,parent,false);
        }else {
            itemView=layoutInflater.inflate(R.layout.box_msg_other,parent,false);
        }

        //만들어진 itemView에 값들 설정
        CircleImageView iv=itemView.findViewById(R.id.iv);
        TextView tvName=itemView.findViewById(R.id.tv_name);
        TextView tvMsg=itemView.findViewById(R.id.tv_msg);
        TextView tvTime=itemView.findViewById(R.id.time);
        tvName.setText(item.getName());
        tvMsg.setText(item.getMessage());
        tvTime.setText(item.getTime());
        if(item.getProfileUrl()!=null){
            Glide.with(itemView).load(item.getProfileUrl()).into(iv);
        }else {
            Glide.with(itemView).load(R.drawable.personmen).into(iv);
        }


        return itemView;
    }

}
