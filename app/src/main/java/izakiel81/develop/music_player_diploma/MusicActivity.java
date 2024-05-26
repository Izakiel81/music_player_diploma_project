package izakiel81.develop.music_player_diploma;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {

    private static final String TAG = "MusicActivity";

    private Button buttonPreviousSong, buttonNextSong, buttonPauseSong;
    private TextView textViewFileNameMusic, textViewProgressStart, textViewProgressEnd;
    private SeekBar seekBarVolume, seekBarMusic;
    private String title, path;
    private int position;
    private ArrayList<String> list;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private Handler handler;
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
        handler = new Handler();

        setupMediaPlayer(path);

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                releaseMediaPlayer();
                finish();
            }
        };

        MusicActivity.this.getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        buttonPreviousSong.setOnClickListener(v -> playPrevious());
        buttonPauseSong.setOnClickListener(v -> togglePlayPause());
        buttonNextSong.setOnClickListener(v -> playNext());

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float volume = progress / 100f;
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
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBarMusic.setProgress(currentPosition);

                    textViewProgressStart.setText(createTimeLabel(currentPosition));
                    textViewProgressEnd.setText(createTimeLabel(mediaPlayer.getDuration()));

                    handler.postDelayed(this, 1000);

                    if (!mediaPlayer.isPlaying() && currentPosition >= mediaPlayer.getDuration() - 100) {
                        playNext();
                    }
                }
            }
        };

        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(runnable);
    }

    private void togglePlayPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            buttonPauseSong.setBackgroundResource(R.drawable.play);
        } else {
            mediaPlayer.start();
            buttonPauseSong.setBackgroundResource(R.drawable.pause);
        }
    }

    private void playPrevious() {
        mediaPlayer.reset();
        if (position == 0) {
            position = list.size() - 1;
        } else {
            position--;
        }
        playSongAtPosition(position);
    }

    private void playNext() {
        mediaPlayer.reset();
        if (position == list.size() - 1) {
            position = 0;
        } else {
            position++;
        }
        playSongAtPosition(position);
    }

    private void playSongAtPosition(int position) {
        String pathNext = list.get(position);
        setupMediaPlayer(pathNext);
    }

    private void setupMediaPlayer(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                Log.d(TAG, "Setting data source to: " + path);
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
                seekBarMusic.setMax(mediaPlayer.getDuration());

                buttonPauseSong.setBackgroundResource(R.drawable.pause);

                String titleNext = path.substring(path.lastIndexOf("/") + 1);
                textViewFileNameMusic.setText(titleNext);
                textViewFileNameMusic.clearAnimation();
                textViewFileNameMusic.setAnimation(animation);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error playing the song", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error setting up media player", e);
            }
        } else {
            Toast.makeText(this, "File not found: " + path, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "File not found: " + path);
        }
    }

    private String createTimeLabel(int time) {
        String timeLabel;
        int minute = time / 1000 / 60;
        int second = time / 1000 % 60;

        if (second < 10) {
            timeLabel = minute + ":0" + second;
        } else {
            timeLabel = minute + ":" + second;
        }
        return timeLabel;
    }
}
