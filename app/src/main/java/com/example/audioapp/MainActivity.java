package com.example.audioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private ImageView bPley, bBa, bBak;
    private SeekBar seekBar;
    private MediaPlayer player;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gropeBottom();
        setPlayer();

    }

    private void gropeBottom() {
        bPley = findViewById(R.id.bPlay);

        bBak = findViewById(R.id.bBak);

        bBa = findViewById(R.id.bBa);

        handler = new Handler();

        seekBar = findViewById(R.id.seekBar);
    }

    private void setPlayer() {
        player = MediaPlayer.create(this, R.raw.lesso);

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                changeSeekbar();
                player.pause();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    player.seekTo(i);
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

    private void changeSeekbar() {
        seekBar.setProgress(player.getCurrentPosition());

        if (player.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    changeSeekbar();
                }
            };
            handler.postDelayed(runnable, 1000);
        }

        bPley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bPlay:
                        if (player.isPlaying()) {
                            player.pause();
                            bPley.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        } else {
                            player.start();
                            changeSeekbar();
                            bPley.setImageResource(R.drawable.ic_stop_black_24dp);
                        }
                        break;
                }
            }
        });

        bBa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bBa:
                        player.seekTo(player.getCurrentPosition() + 500);
                               break;
                }
            }
        });

        bBak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bBak:
                           player.seekTo(player.getCurrentPosition() - 5000);
                              break;
                }
            }
        });
    }
}
