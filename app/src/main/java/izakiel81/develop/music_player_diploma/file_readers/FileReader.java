package izakiel81.develop.music_player_diploma.file_readers;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class FileReader {
    private static final String TAG = "FileReader";
    private final Context context;

    public FileReader(Context context) {
        this.context = context;
    }

    public ArrayList<String> getAllFiles() {
        ArrayList<String> audioLists = new ArrayList<>();

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA
        };

        try (Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
        )) {
            if (cursor != null) {
                int dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);

                while (cursor.moveToNext()) {
                    String filePath = cursor.getString(dataIndex);
                    Log.d(TAG, "Found audio file: " + filePath);
                    audioLists.add(filePath);
                }
            } else {
                Log.e(TAG, "Cursor is null. Query to MediaStore returned no results.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception while querying MediaStore", e);
        }

        return audioLists;
    }
}
