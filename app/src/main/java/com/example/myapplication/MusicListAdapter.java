package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{
    ArrayList<AudioModel> songsList;
    public static ArrayList<AudioModel> UserPlayList;
    Context context;
    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context)
    {
        this.songsList = songsList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AudioModel songData = songsList.get(position);
        holder.titleTextView.setText(songData.getTitle());
        UserPlayList = new ArrayList<>();
        if(MyMediaPlayer.currentIndex==position)
        {
            holder.titleTextView.setTextColor(Color.parseColor("#FF0000"));
        }
        else
        {
            holder.titleTextView.setTextColor(Color.parseColor("#000000"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
                //intent.putExtra("PLAYLIST",UserPlayList);
                //intent.putParcelableArrayListExtra("LIST",songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.AddToPlayListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPlayList.add(songsList.get(position));
                if(UserPlayList==null)
                {
                    Log.i("Title", "Null list");
                }
                Log.i("Title",UserPlayList.get(0).title);
                Toast.makeText(context.getApplicationContext(), "Added to playlist", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView,AddToPlayListBtn;
        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            iconImageView = itemView.findViewById(R.id.icon);
            AddToPlayListBtn = itemView.findViewById(R.id.add_to_playlist);

        }
    }
}
