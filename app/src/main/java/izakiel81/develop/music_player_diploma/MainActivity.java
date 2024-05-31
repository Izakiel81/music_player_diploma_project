package izakiel81.develop.music_player_diploma;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import izakiel81.develop.music_player_diploma.file_readers.FileReader;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private FileReader fileReader;
    private MusicAdapter adapter;
    private ArrayList<String> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ініціалізація RecyclerView і встановлення LayoutManager
        recyclerView = findViewById(R.id.recyclerViewSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Перевірка дозволів та завантаження аудіофайлів
        checkPermissionsAndLoadFiles();
    }

    // Метод для перевірки дозволів та завантаження файлів
    private void checkPermissionsAndLoadFiles() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Перевірка дозволу для Android 13 (TIRAMISU) і вище
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_AUDIO},
                        PERMISSION_REQUEST_CODE);
            } else {
                loadAudioFiles();
            }
        } else {
            // Перевірка дозволу для попередніх версій Android
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            } else {
                loadAudioFiles();
            }
        }
    }

    // Метод для завантаження аудіофайлів
    private void loadAudioFiles() {
        fileReader = new FileReader(this);
        songs = fileReader.getAllFiles();
        if (songs.isEmpty()) {
            // Відображення повідомлення, якщо аудіофайли не знайдені
            Toast.makeText(this, "No audio files found", Toast.LENGTH_SHORT).show();
        } else {
            // Встановлення адаптера для RecyclerView
            adapter = new MusicAdapter(songs, this);
            recyclerView.setAdapter(adapter);
        }
    }

    // Метод для обробки результату запиту на дозвіл
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Дозвіл надано, завантажуємо аудіофайли
                loadAudioFiles();
            } else {
                // Дозвіл відхилено, відображаємо повідомлення
                Toast.makeText(this, "Permission denied to read your external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
