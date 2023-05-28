package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder>{
    public static ArrayList<AudioModel> UserPlayList;
    Context context;
    public PlayListAdapter(ArrayList<AudioModel> UserPlayList, Context context)
    {
        this.UserPlayList = MusicListAdapter.UserPlayList;
        this.context = context;
    }


    @Override
    public PlayListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new PlayListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int i = 0;
        //while(i < UserPlayList.size())
        //{
            AudioModel Songdata = UserPlayList.get(position);
            i++;
            holder.titleTextView.setText(Songdata.getTitle());
        //}
        //holder.titleTextView.setText(Songdata.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.putExtra("PLAYLIST",UserPlayList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(UserPlayList == null)
        {
            return 0;
        }
        else
        {
            return UserPlayList.size();
        }
        //return UserPlayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_title_text);
            iconImageView = itemView.findViewById(R.id.icon);

        }
    }

}
