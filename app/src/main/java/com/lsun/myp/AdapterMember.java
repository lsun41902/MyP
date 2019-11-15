package com.lsun.myp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMember extends RecyclerView.Adapter {
    Context context;
    ArrayList<MyMember> members;







    public AdapterMember(Context context, ArrayList<MyMember> members) {
        this.context = context;
        this.members = members;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemview=inflater.inflate(R.layout.fragment_recyclerview_item,parent,false);
        VH vh=new VH(itemview);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH) holder;
        MyMember myMember=members.get(position);
        vh.tvTitle.setText(myMember.title);
        if(MainActivity.userImage==null){
            Glide.with(context).load(R.drawable.personmen).into(vh.circleImageView);
        }else {
            Glide.with(context).load(MainActivity.userImage).into(vh.circleImageView);
        }
        vh.text.setText(myMember.text);
        SharedPreferences sp=context.getSharedPreferences("userName", Context.MODE_PRIVATE);
        vh.nickname.setText(sp.getString("userNickname",null));
        vh.dates.setText(myMember.date);
        if(myMember.img1==null){
            vh.img1.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(myMember.getImg1()).into(vh.img1);
        }
        if(myMember.img2==null){
            vh.img2.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(myMember.getImg2()).into(vh.img2);
        }
        if(myMember.img3==null){
            vh.img3.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(myMember.getImg3()).into(vh.img3);
        }

    }


    @Override
    public int getItemCount() {
        return members.size();
    }
    class VH extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView dates,fav,text,tvTitle,nickname;
        ImageView img1,img2,img3;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.item_tv_title);
            dates=itemView.findViewById(R.id.item_tv_date);
            fav=itemView.findViewById(R.id.item_tv_fav);
            circleImageView=itemView.findViewById(R.id.item_iv);
            text=itemView.findViewById(R.id.item_tv_text);
            nickname=itemView.findViewById(R.id.item_tv_nickname);
            img1=itemView.findViewById(R.id.item_layout_img1);
            img2=itemView.findViewById(R.id.item_layout_img2);
            img3=itemView.findViewById(R.id.item_layout_img3);



        }
    }
}
