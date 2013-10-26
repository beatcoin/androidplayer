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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readMusic(Environment.getExternalStorageDirectory().getPath() + "/Music");
        prepareMediaPlayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        musicList = mp3Files;
    }

    public void prepareMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Music/Sexy Boy.mp3"));
    }

    public void pauseSong(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public void playSong(View view) {
        mediaPlayer.start();
    }

    public void stopSong(View view) {
        mediaPlayer.stop();
        prepareMediaPlayer();
    }

}
