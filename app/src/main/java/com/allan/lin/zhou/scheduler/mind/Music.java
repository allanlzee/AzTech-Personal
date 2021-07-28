package com.allan.lin.zhou.scheduler.mind;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.MusicActivityBinding;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class Music extends AppCompatActivity {

    private MusicActivityBinding binding;
    private Toolbar toolbar;

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity);

        binding = MusicActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, Music.this);
            }
        });

        mediaPlayer = new MediaPlayer();

        binding.playerSeekbar.setMax(100);

        binding.playImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(mover);
                    mediaPlayer.pause();
                    binding.playImage.setImageResource(R.drawable.play_music);
                } else {
                    mediaPlayer.start();
                    binding.playImage.setImageResource(R.drawable.pause_music);
                    moveSeekbar();
                }
            }
        });

        preparePlayer();

        binding.playerSeekbar.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                SeekBar seekBar = (SeekBar) view;
                int position = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(position);
                binding.currentTime.setText(timer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private Runnable mover = new Runnable() {

        @Override
        public void run() {
            moveSeekbar();
            long current = mediaPlayer.getCurrentPosition();
            binding.currentTime.setText(timer(current));
        }
    };

    private void preparePlayer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build());
        } else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        try {
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            // TODO: find a MP3 link that works
            mediaPlayer.setDataSource("https://www.youtube.com/watch?v=5qap5aO4i9A&ab_channel=LofiGirl");
            mediaPlayer.prepareAsync();
            binding.songLength.setText(timer(mediaPlayer.getDuration()));

        } catch (Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            exception.printStackTrace();
        }
    }

    private void moveSeekbar() {
        if (mediaPlayer.isPlaying()) {
            binding.playerSeekbar.setProgress((int)
                    (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration() * 100)));
            handler.postDelayed(mover, 1000);
        }
    }

    private String timer(long milliseconds) {
        String timerString = "";
        String secondsString = "";

        int hours = (int) (milliseconds / (1000 * 3600));
        int minutes = (int) (milliseconds % (1000 * 3600) / (60000));
        int seconds = (int) ((milliseconds % (1000 * 3600)) % (60000));

        if (hours > 0) {
            timerString = hours + ":";
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondsString;
        return timerString;
    }
}