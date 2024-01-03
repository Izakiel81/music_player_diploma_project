package izakiel81.develop.music_player_diploma.file_readers;


import android.content.Context;

import android.database.Cursor;
import android.provider.MediaStore;


import java.util.ArrayList;

public class FileReader {
    private final Context context;

    public FileReader(Context context) {
        this.context = context;
    }

    public ArrayList<String> getAllFiles(){
        ArrayList<String> audioLists = new ArrayList<>();

        String[] strings = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME};// Can include more data for more details and check it.

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, strings, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int audioIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);

                    audioLists.add(cursor.getString(audioIndex));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();

        return audioLists;
    }

}
