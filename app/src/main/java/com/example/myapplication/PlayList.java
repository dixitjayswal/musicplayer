package com.example.myapplication;

import static com.example.myapplication.MusicPlayerActivity.UserPlayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;


public class PlayList extends AppCompatActivity {
    RecyclerView playList;
    ImageView HomeBtn, PlayListBtn;
    ArrayList<AudioModel> myList = UserPlayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        playList = findViewById(R.id.play_list);
        HomeBtn = findViewById(R.id.homebtn);
        PlayListBtn = findViewById(R.id.playlistbtn);
        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayList.this, PlayerList.class));
            }
        });
        playList.setLayoutManager(new LinearLayoutManager(this));
        playList.setAdapter(new PlayListAdapter(myList,getApplicationContext()));
    }
}