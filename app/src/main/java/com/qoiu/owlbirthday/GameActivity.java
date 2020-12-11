package com.qoiu.owlbirthday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qoiu.owlbirthday.presenter.GamePresenter;

public class GameActivity extends AppCompatActivity implements GameActivityInterface {

    private ImageView prize;
    private RecyclerAdapterPrize recyclerAdapterPrize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GamePresenter.getInstance().bindView(this);

        prize = findViewById(R.id.image_prize);
        prize.setVisibility(View.GONE);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerAdapterGameField recyclerAdapterGameField = new RecyclerAdapterGameField();
        GridLayoutManager layoutManager =
                new GridLayoutManager(this, GamePresenter.getInstance().getGridSize());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapterGameField);

        recyclerView = findViewById(R.id.recycler_prizes);
        recyclerAdapterPrize = new RecyclerAdapterPrize();
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapterPrize);

    }

    private Runnable searchTask = new Runnable() {
        @Override
        public void run() {
            for (int i = 255; i > 1; i--) {
                final int r = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prize.setImageAlpha(r);
                        prize.setScaleX(r / 255f);
                        prize.setScaleY(r / 255f);
                    }
                });
                if (Thread.currentThread().isInterrupted()) return;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        GamePresenter.getInstance().unbindView();
    }

    private Thread prizeThread = new Thread(searchTask);

    @Override
    public void addPrize(int prizeID) {
        recyclerAdapterPrize.addPrize(prizeID);
        prize.setVisibility(View.VISIBLE);
        prize.setImageResource(ImageResources.getImageById(prizeID));
        try {
            prizeThread.interrupt();
            prizeThread.join();
            prizeThread = new Thread(searchTask);
            prizeThread.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
