package org.beatcoin.player.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWebpageTask extends AsyncTask<String, Void, String> {

    protected MainActivity activity;

    public DownloadWebpageTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Download of URL failed";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        String songToPlay = null;
        try {
            JSONObject object = (JSONObject) new JSONTokener(result).nextValue();
            String status = object.getString("status");
            JSONArray items = object.getJSONArray("items");
            JSONObject song = items.getJSONObject(0);
            songToPlay = song.getString("song_identifier");
        } catch (JSONException e) {
            Log.e("onPostExecute", "Could not parse JSON");
        }
        if (songToPlay != null) {
            activity.playSongByName(songToPlay);
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        int len = 50000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("downloadUrl", "HTTP response code: " + response);
            is = conn.getInputStream();

            return convertStreamToString(is);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
