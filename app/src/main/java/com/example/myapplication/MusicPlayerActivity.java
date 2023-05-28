package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView titleTc, currentTimeTv, totalTimeTv;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, previousBtn, MusicIcon, PlayListBtn;
    ArrayList<AudioModel> songsList;
    public static ArrayList<AudioModel> UserPlayList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTc = findViewById(R.id.title_text);
        currentTimeTv = findViewById(R.id.start_time);
        totalTimeTv = findViewById(R.id.end_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.pause_btn);
        nextBtn = findViewById(R.id.next_btn);
        previousBtn = findViewById(R.id.previous_btn);
        MusicIcon = findViewById(R.id.img);
        titleTc.setSelected(true);
        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");
        UserPlayList = new ArrayList<AudioModel>();
        UserPlayList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("PLAYLIST");
        setResourcesWithMusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null)
                {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));
                    if(mediaPlayer.isPlaying())
                    {
                        pausePlay.setImageResource(R.drawable.ic_baseline_pause_24);;
                        MusicIcon.setRotation(x++);
                    }
                    else
                    {
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        MusicIcon.setRotation(0);
                    }
                }
                new Handler().postDelayed(this, 10);

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer!=null && b)
                {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    void setResourcesWithMusic()
    {
        currentSong = songsList.get(MyMediaPlayer.currentIndex);
        titleTc.setText(currentSong.getTitle());
        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));
        pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausePlay();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNextSong();
            }
        });
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPreviousSong();
            }
        });
        playMusic();
    }
    private void playMusic()
    {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    private void playNextSong()
    {
        if(MyMediaPlayer.currentIndex == songsList.size() - 1)
        {
            return;
        }
        MyMediaPlayer.currentIndex += 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }
    private void playPreviousSong()
    {
        if(MyMediaPlayer.currentIndex == 0)
        {
            return;
        }
        MyMediaPlayer.currentIndex -= 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }
    private void pausePlay()
    {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
        }
        else
        {
            mediaPlayer.start();
        }
    }
    public static String convertToMMSS(String duration)
    {
        Long mills = Long.parseLong(duration);
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(mills) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(mills) % TimeUnit.MINUTES.toSeconds(1));

    }


}