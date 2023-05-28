package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class PlayerList extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView PlayListBtn;
    TextView noMusicTextView;
    ImageView AddToPlayListBtn;
    public static ArrayList<AudioModel> songsList = new ArrayList<>();
    public static ArrayList<AudioModel> UserPlayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);
        recyclerView = findViewById(R.id.recycler_view);
        noMusicTextView = findViewById(R.id.no_songs);
        PlayListBtn = findViewById(R.id.playlistbtn);
        AddToPlayListBtn = findViewById(R.id.add_to_playlist);
        /*PlayListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerList.this, PlayList.class));
            }
        });*/
        if(checkPermission()==false)
        {
            requestPermission();
            return;
        }
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext())
        {
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0), cursor.getString(2));
            if(new File(songData.getPath()).exists())
            {
                songsList.add(songData);
            }
            if(songsList.size() == 0)
            {
                noMusicTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new MusicListAdapter(songsList,getApplicationContext()));
            }
        }
        PlayListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlayerList.this, PlayList.class));
            }
        });

    }
    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(PlayerList.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(PlayerList.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            Toast.makeText(PlayerList.this, "Please give permission to access!", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(PlayerList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);

    }

}