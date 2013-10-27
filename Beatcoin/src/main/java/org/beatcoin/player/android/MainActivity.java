package org.beatcoin.player.android;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    protected MediaPlayer mediaPlayer;

    protected List<String> musicList;

    protected String musicFolder = Environment.getExternalStorageDirectory().getPath() + "/Music/";

    protected String apiUrl = "http://engine.beatcoin.org/jukebox/526c687e1889080387b0911c/play";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readMusic(musicFolder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void readMusic(String path) {
        File f = new File(path);
        File[] files = f.listFiles();
        Arrays.sort(files);
        List<String> mp3Files = new ArrayList<String>();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {
                mp3Files.add(file.getName());
            }
        }
        musicList = mp3Files.subList(0, 20);
    }

    public void playSong(View view) {
        new DownloadWebpageTask(this).execute(apiUrl);
    }

    public void playSongByName(String name) {
        Log.d("playSongByName", "Playing song \"" + name + "\"");
        if(mediaPlayer == null) {
            try {
                mediaPlayer = MediaPlayer.create(this, Uri.parse(musicFolder + name));
                mediaPlayer.start();
            } catch (Exception e) {
                Log.d("playSongByName", "Playback failed");
            }
        }
    }

    public void stopSong(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
