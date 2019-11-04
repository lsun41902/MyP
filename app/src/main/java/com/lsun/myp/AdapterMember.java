package com.lsun.myp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

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

    }

    @Override
    public int getItemCount() {
        return members.size();
    }
    class VH extends RecyclerView.ViewHolder{
        TextView tvTitle;
        CircleImageView circleImageView;
        TextView date;
        TextView fav;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.item_tv_title);
            date=itemView.findViewById(R.id.item_tv_date);
            fav=itemView.findViewById(R.id.item_tv_fav);
            circleImageView=itemView.findViewById(R.id.item_iv);


        }
    }
}
