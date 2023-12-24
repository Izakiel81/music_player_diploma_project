package izakiel81.develop.music_player_diploma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewSongs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* Перевірка на наявність доступу до файлової системи користувача.

            * Якщо доступу немає, додаток надсилає запит на отримання доступу користувачу
            * Якщо доступ є, додаток збирає всі аудіо файли

        */
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)

        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }else
        {
            getAllAudioFiles();
        }
    }

    private void getAllAudioFiles()
    {

    }


    // Метод, що перевіряє результат запиту на отримання доступу до файлової системи користувача
    // Якщо користувач надав доступ до файлової системи, додаток збирає всі аудіо файли
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getAllAudioFiles();
        }
    }
}