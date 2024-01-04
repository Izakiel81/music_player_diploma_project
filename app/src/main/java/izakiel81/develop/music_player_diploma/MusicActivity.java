package izakiel81.develop.music_player_diploma;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    private Button buttonPreviousSong, buttonNextSong, buttonPauseSong;
    private TextView textViewFileNameMusic, textViewProgressStart, textViewProgressEnd;
    private SeekBar seekBarVolume, seekBarMusic;
    private String title, path;
    private int position;
    private ArrayList<String> list;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private Handler handler;
    private int totalTime;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);




        buttonPreviousSong = findViewById(R.id.buttonPreviousSong);
        buttonPauseSong = findViewById(R.id.buttonPauseSong);
        buttonNextSong = findViewById(R.id.buttonNextSong);

        textViewFileNameMusic = findViewById(R.id.textViewFileNameMusic);
        textViewProgressStart = findViewById(R.id.textViewProgressStart);
        textViewProgressEnd = findViewById(R.id.textViewProgressEnd);

        seekBarVolume = findViewById(R.id.seekBarVolume);
        seekBarMusic = findViewById(R.id.seekBarMusic);

        animation = AnimationUtils.loadAnimation(MusicActivity.this, R.anim.translate_name_animation);
        textViewFileNameMusic.setAnimation(animation);

        title = getIntent().getStringExtra("title");
        path = getIntent().getStringExtra("path");

        position = getIntent().getIntExtra("position", 0);

        list = getIntent().getStringArrayListExtra("list");

        textViewFileNameMusic.setText(title);

        mediaPlayer = new MediaPlayer();


        try {

            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        };

        MusicActivity.this.getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        buttonPreviousSong.setOnClickListener(v -> {
            mediaPlayer.reset();
            if(position == 0)
            {
                position = list.size() - 1;
            }else{
                position--;
            }

            String pathPrevious = list.get(position);

            try {
                mediaPlayer.setDataSource(pathPrevious);
                mediaPlayer.prepare();
                mediaPlayer.start();

                buttonPauseSong.setBackgroundResource(R.drawable.pause);

                String title = pathPrevious.substring(pathPrevious.lastIndexOf("/") + 1);
                textViewFileNameMusic.setText(title);

                textViewFileNameMusic.clearAnimation();
                textViewFileNameMusic.setAnimation(animation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        buttonPauseSong.setOnClickListener(v -> {

            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                buttonPauseSong.setBackgroundResource(R.drawable.play);
            }else{
                mediaPlayer.start();
                buttonPauseSong.setBackgroundResource(R.drawable.pause);
            }
        });

        buttonNextSong.setOnClickListener(v -> {
            playNext();
        });

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekBarVolume.setProgress(progress);
                    float volume = seekBarVolume.getProgress() / 100f;
                    mediaPlayer.setVolume(volume, volume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                    seekBarMusic.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                totalTime = mediaPlayer.getDuration();
                seekBarMusic.setMax(totalTime);

                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBarMusic.setProgress(currentPosition);
                handler.postDelayed(runnable, 1000);

                String currentTime = createTimeLabel(currentPosition);
                String songDurationTime = createTimeLabel(totalTime);

                textViewProgressStart.setText(currentTime);
                textViewProgressEnd.setText(songDurationTime);

               if(currentTime.equals(songDurationTime)){
                  playNext();
               }
            }
        };

        handler.post(runnable);
    }

    private String createTimeLabel(int currentPosition){

        String timeLabel;
        int minute, second;

        minute = currentPosition / 1000 / 60;
        second = currentPosition / 1000 % 60;

        if(second < 10){
            timeLabel = minute + ":0" + second;
        }else{
            timeLabel = minute + ":" + second;
        }
        return timeLabel;
    }

    private void playNext(){
        mediaPlayer.reset();
        if(position == list.size() - 1)
        {
            position = 0;
        }else{
            position++;
        }

        String pathPrevious = list.get(position);

        try {
            mediaPlayer.setDataSource(pathPrevious);
            mediaPlayer.prepare();
            mediaPlayer.start();

            buttonPauseSong.setBackgroundResource(R.drawable.pause);

            String title = pathPrevious.substring(pathPrevious.lastIndexOf("/") + 1);
            textViewFileNameMusic.setText(title);

            textViewFileNameMusic.clearAnimation();
            textViewFileNameMusic.setAnimation(animation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



}