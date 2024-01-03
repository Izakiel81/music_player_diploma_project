package izakiel81.develop.music_player_diploma.file_readers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class FileReaderAPIBelow33 {

    private final Context context;

    public FileReaderAPIBelow33(Context context) {
        this.context = context;
    }

    public ArrayList<String> getAllAudioFiles() {
        ArrayList<String> audioFiles = new ArrayList<>();

        // Set up the content resolver
        ContentResolver contentResolver = context.getContentResolver();
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // Define the columns you want to retrieve
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA
        };

        // Specify the sorting order
        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";

        // Perform the query
        Cursor cursor = contentResolver.query(audioUri, projection, null, null, sortOrder);

        if (cursor != null) {
            try {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
                int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idColumn);
                    String name = cursor.getString(nameColumn);
                    String data = cursor.getString(dataColumn);

                    Uri contentUri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                    );

                    Log.d("FileReaderAPIBelow33", "ID: " + id + ", Name: " + name + ", Data: " + data + ", Uri: " + contentUri);

                    // Add the file path or other details to the list
                    audioFiles.add(name);
                }
            } finally {
                cursor.close();
            }
        }

        return audioFiles;
    }
}