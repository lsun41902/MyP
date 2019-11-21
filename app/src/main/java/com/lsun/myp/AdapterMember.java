package com.lsun.myp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMember extends RecyclerView.Adapter {
    Context context;
    ArrayList<MyMember> members;
    int medalCnt=0;
    boolean medal=false;
    public static Object numbuer;
    public static final int REQ_POST=1011;







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
        numbuer=myMember.no;
        vh.tvTitle.setText(myMember.title);
        if(MainActivity.userImage==null){
            Glide.with(context).load(R.drawable.personmen).into(vh.circleImageView);
        }else {
            Glide.with(context).load(MainActivity.userImage).into(vh.circleImageView);
        }
        vh.text.setText(myMember.text);

        vh.nickname.setText(myMember.getNickName());


        vh.dates.setText(myMember.date);
        if(myMember.img11==null){
            vh.img1.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(myMember.getImg11()).into(vh.img1);
        }
        if(myMember.img22==null){
            vh.img2.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(myMember.getImg22()).into(vh.img2);
        }
        if(myMember.img33==null){
            vh.img3.setVisibility(View.GONE);
        }else {
            Glide.with(context).load(myMember.getImg33()).into(vh.img3);
        }
        vh.fav.setText(medalCnt+"");
        if(medal==true){
            vh.favBtn.setImageResource(R.drawable.medalyellow);
        }
        SharedPreferences sp=context.getSharedPreferences("userName",Context.MODE_PRIVATE);
        String checksetting=sp.getString("userNickname",null);
        if(checksetting.equals(myMember.getNickName())){
            vh.setting.setVisibility(View.VISIBLE);
        }else {
            vh.setting.setVisibility(View.GONE);
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
        ImageButton setting,favBtn;

        public VH(@NonNull final View itemView) {
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
            favBtn=itemView.findViewById(R.id.item_fav);
            setting=itemView.findViewById(R.id.item_setting);



            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (medal==false){
                        medal=true;
                        medalCnt+=1;
                        favBtn.setImageResource(R.drawable.medalyellow);
                    }else {
                        medal=false;
                        medalCnt-=1;
                        favBtn.setImageResource(R.drawable.medal32px);
                    }
                }
            });
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu=new android.widget.PopupMenu(context,setting);
                    popupMenu.inflate(R.menu.popup);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.rebuild:
                                    Intent intent= new Intent(context,PostActivity.class);
                                    ((Activity)context).startActivityForResult(intent,REQ_POST);
                                    Log.i("moyang",numbuer+"");
                                    break;
                                case R.id.delete:
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

    }
}
