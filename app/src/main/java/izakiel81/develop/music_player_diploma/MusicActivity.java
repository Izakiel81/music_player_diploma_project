package izakiel81.develop.music_player_diploma;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }
}