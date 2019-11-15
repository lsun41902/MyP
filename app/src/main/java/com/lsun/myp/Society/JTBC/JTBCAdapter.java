package com.lsun.myp.Society.JTBC;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.lsun.myp.R;
import java.util.ArrayList;

public class JTBCAdapter extends RecyclerView.Adapter {
    ArrayList<RssItemMember> items;
    Context context;

    public JTBCAdapter(ArrayList<RssItemMember> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(context).inflate(R.layout.donga_item,parent,false);
        VH vh=new VH(itemview);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh= (VH)holder;

        //현재번째(position) 아이템 얻어오기
        RssItemMember item= items.get(position);
        vh.tvTitle.setText(item.getTitle());
        vh.tvDesc.setText(item.getDesc());
        vh.tvDate.setText(item.getDate());

        //이미지는 없을 수도 있음.
        if( item.getImgUrl()==null){
            vh.iv.setVisibility(View.GONE);
        }else{
            vh.iv.setVisibility(View.VISIBLE);
            //네트워크에 있는 이미지를 보여주려면
            //별도의 Thread가 필요한데 이를 편하게
            //해주는 Library사용( Glide library )
            Glide.with(context).load(item.getImgUrl()).into(vh.iv);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    class VH extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView iv;
        TextView tvDesc;
        TextView tvDate;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.donga_tv_title);
            iv=itemView.findViewById(R.id.donga_iv);
            tvDesc=itemView.findViewById(R.id.donga_tv_desc);
            tvDate=itemView.findViewById(R.id.donga_tv_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String link= items.get(getLayoutPosition()).getLink();

                    //웹뷰를 가진 새로운 액티비티 실행
                    Intent intent= new Intent(context, JTBCWebViewActivity.class);
                    intent.putExtra("Link", link);

                    context.startActivity(intent);

                }
            });
        }
    }

}

