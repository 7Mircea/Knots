package com.example.myfundamentalsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KnotAdapter extends RecyclerView.Adapter<KnotAdapter.KnotViewHolder> {
    private ArrayList<Knot> list = new ArrayList<>();
    private Context context;
    private OnClickInterface onClickInterface;

    public static class KnotViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private View item;

        public KnotViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_recycler_item);
            imageView = itemView.findViewById(R.id.image_recycler_item);
            item = itemView;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public View getItem() {
            return item;
        }
    }

    public KnotAdapter(Context context, OnClickInterface onClickInterface) {
        this.context = context;
        this.onClickInterface = onClickInterface;
    }

    public void setList(List<Knot> list) {
        this.list = (ArrayList<Knot>) list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KnotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_knot,
                parent,false);
        return new KnotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KnotViewHolder holder, final int position) {
        holder.getTextView().setText(list.get(position).getName());
        holder.getImageView().setImageDrawable(ContextCompat.getDrawable(context,list.get(position).getImage()));
        holder.getItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
