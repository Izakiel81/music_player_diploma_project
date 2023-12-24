package izakiel81.develop.music_player_diploma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicActivity extends AppCompatActivity {

    private Button buttonPreviousSong, buttonNextSong, buttonPauseSong;
    private TextView textViewFileNameMusic, textViewProgressStart, textViewProgressEnd;
    private SeekBar seekBarVolume, seekBarMusic;

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



    }
}