package izakiel81.develop.music_player_diploma.file_readers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class FileReader {
    private final Context context;
    private ArrayList<String> songs = new ArrayList<>();

    private final static String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath()+"/";

    public FileReader(Context context) {
        this.context = context;
    }

    private void getAllAudioFilesFromDownload(){
        if(MEDIA_PATH.endsWith("/Download")){
            File mainFile = new File(MEDIA_PATH);
            File[] fileList = mainFile.listFiles();


            for (File file: fileList) {


                if(file.isDirectory()){

                    scanFiles(file);
                }else{
                    String path = file.getAbsolutePath();

                    if(path.endsWith(".mp3")){
                        Log.e("Audio list ------>", path);
                        songs.add(path);
                    }
                }
            }

        }

    }


    private void getAllAudioFilesFromMusic(){
        if(MEDIA_PATH.endsWith("/Music")){
            File mainFile = new File(MEDIA_PATH);
            File[] fileList = mainFile.listFiles();

            for (File file: fileList) {

                if(file.isDirectory()){

                    scanFiles(file);
                }else{
                    String path = file.getAbsolutePath();
                    if(path.endsWith(".mp3")){
                        Log.e("Audio list ------>", path);
                        songs.add(path);
                    }
                }
            }

        }

    }

    private void scanFiles(File directory){
        if(directory != null){
            File[] fileList = directory.listFiles();

            for (File file: fileList) {

                if(file.isDirectory()){
                    scanFiles(directory);
                }else{

                    String path = file.getAbsolutePath();
                    if(path.endsWith(".mp3")){
                        Log.e("Audio list ------>", path);
                        songs.add(path);

                    }
                }
            }

        }

    }

    public ArrayList<String> getAllSongs(){
        getAllAudioFilesFromDownload();
        getAllAudioFilesFromMusic();
        return songs;
    }


}
