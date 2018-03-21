package com.masterdomservis.android_5.utility;

import com.google.gson.Gson;
import com.masterdomservis.android_5.MainActivity;
import com.masterdomservis.android_5.MyService;
import com.masterdomservis.android_5.R;
import com.masterdomservis.android_5.pojo.Declaration;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.masterdomservis.android_5.pojo.Declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


public class Utility {

    public CopyOnWriteArrayList<Declaration> GetDeclaration(String HASH, String MD5, String HOST, String PORT) {
        Type listType = new TypeToken<CopyOnWriteArrayList<Declaration>>(){}.getType();

        HttpURLConnection connection = null;
        //String query = String.format("http://%s:%s/Android/%s/update/declarations/list/%s", HOST, PORT, HASH, MD5);
        String query = "http://194.67.207.41:45678/declaration/root/get/list/d55561f495b46e262733602ae825465d";
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(4000);

            connection.connect();

            StringBuffer sb = new StringBuffer();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

                Gson gson = new Gson();
                CopyOnWriteArrayList<Declaration> list = (CopyOnWriteArrayList<Declaration>) gson.fromJson(sb.toString(), listType);
                return list;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

}
