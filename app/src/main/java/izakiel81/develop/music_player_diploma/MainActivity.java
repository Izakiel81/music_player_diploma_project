package izakiel81.develop.music_player_diploma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;

import izakiel81.develop.music_player_diploma.file_readers.FileReader;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FileReader fileReader;

    private final static String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";

    private MusicAdapter adapter;

    private ArrayList<String> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("Media path", MEDIA_PATH);

        recyclerView = findViewById(R.id.recyclerViewSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* Перевірка на наявність доступу до файлової системи користувача.

            * Якщо доступу немає, додаток надсилає запит на отримання доступу користувачу
            * Якщо доступ є, додаток збирає всі аудіо файли

        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_MEDIA_AUDIO)
                    != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_MEDIA_AUDIO},
                            1);

                }else{
                    fileReader = new FileReader(MainActivity.this);
                    songs = fileReader.getAllFiles();
                    adapter = new MusicAdapter(songs, MainActivity.this);

                    recyclerView.setAdapter(adapter);
                }

        }
       else {
           if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
        }else{
               fileReader = new FileReader(MainActivity.this);
               songs = fileReader.getAllFiles();
               adapter = new MusicAdapter(songs, MainActivity.this);

               recyclerView.setAdapter(adapter);
           }

    }

}

    // Метод, що перевіряє результат запиту на отримання доступу до файлової системи користувача
    // Якщо користувач надав доступ до файлової системи, додаток збирає всі аудіо файли
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                songs = fileReader.getAllFiles();
        }
    }
}