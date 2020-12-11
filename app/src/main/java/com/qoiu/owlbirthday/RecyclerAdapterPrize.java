package com.qoiu.owlbirthday;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qoiu.owlbirthday.model.Box;
import com.qoiu.owlbirthday.presenter.GamePresenter;

import java.util.ArrayList;

public class RecyclerAdapterPrize extends RecyclerView.Adapter<RecyclerAdapterPrize.ViewHolder> {

    ArrayList<Integer> data=new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new RecyclerAdapterPrize.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setBackgroundResource(0);
        holder.imageView.setImageResource(
                ImageResources.getImageById(
                        data.get(position)
                )
        );
    }


    public void addPrize(int prizeId) {
        data.add(prizeId);
        //notifyDataSetChanged();
        //notifyAll();
        notifyItemInserted(data.size());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.action_image);
        }
    }
}
