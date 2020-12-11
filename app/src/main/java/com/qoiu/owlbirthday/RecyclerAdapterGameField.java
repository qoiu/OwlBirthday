package com.qoiu.owlbirthday;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qoiu.owlbirthday.model.Box;
import com.qoiu.owlbirthday.presenter.GamePresenter;

import java.util.List;


public class RecyclerAdapterGameField extends RecyclerView.Adapter<RecyclerAdapterGameField.ViewHolder> {

    private List<Box> data = GamePresenter.getInstance().getBoxList();


    @NonNull
    @Override
    public RecyclerAdapterGameField.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Box box = data.get(position);
        holder.imageView.setBackgroundResource(ImageResources.getBackImg(box));
        holder.imageView.setImageResource(
                ImageResources.getImageById(
                        box.isOpened() ? box.getGiftID() : 0
                )
        );
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int winItem = GamePresenter.getInstance().clickBox(position);
                update();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void update() {
        data = GamePresenter.getInstance().getBoxList();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.action_image);
        }

    }
}
