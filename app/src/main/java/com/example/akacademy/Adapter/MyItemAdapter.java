package com.example.akacademy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akacademy.CorurselistActivity;
import com.example.akacademy.Interface.IItemClickListener;
import com.example.akacademy.Model.ItemData;
import com.example.akacademy.PdfReader;
import com.example.akacademy.QuizTest;
import com.example.akacademy.R;
import com.example.akacademy.testExplore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.MyViewHolder> {

    private Context context;
    private List<ItemData> itemDataList;


    public MyItemAdapter(Context context, List<ItemData> itemDataList) {
        this.context = context;
        this.itemDataList = itemDataList;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_item_title.setText(itemDataList.get(position).getName());
        Picasso.get().load(itemDataList.get(position).getImage()).into(holder.img_item);

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if(itemDataList.get(position).getType().equals("pdf")){
                    context.startActivity(new Intent(new Intent(context, PdfReader.class)).putExtra("pdfid",itemDataList.get(position).getId()));
                }else if(itemDataList.get(position).getType().equals("quiz_test")){
                    context.startActivity(new Intent(new Intent(context, QuizTest.class)).putExtra("testid",itemDataList.get(position).getId()));
                    //context.startActivity(new Intent(new Intent(context, testExplore.class)));

                }
                else {
                    context.startActivity(new Intent(new Intent(context, CorurselistActivity.class)).putExtra("courseid", itemDataList.get(position).getId()));
                }
                Toast.makeText(context, itemDataList.get(position).getType(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemDataList!=null?itemDataList.size():0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_item_title;
        ImageView img_item;

        IItemClickListener iItemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_item_title = (TextView) itemView.findViewById(R.id.imgTitle);
            img_item = (ImageView) itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(this);
        }



        public void setiItemClickListener(IItemClickListener iItemClickListener){
            this.iItemClickListener = iItemClickListener;
        }

        @Override
        public void onClick(View view) {
            iItemClickListener.onItemClickListener(view,getAdapterPosition());
        }
    }
}