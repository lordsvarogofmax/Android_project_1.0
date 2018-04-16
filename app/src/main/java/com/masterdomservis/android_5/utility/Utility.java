package com.masterdomservis.android_5.utility;

import com.google.gson.Gson;
import com.masterdomservis.android_5.MainActivity;
import com.masterdomservis.android_5.MyService;
import com.masterdomservis.android_5.R;
import com.masterdomservis.android_5.pojo.Declaration;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.masterdomservis.android_5.pojo.Declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Telephony.Mms.Part.FILENAME;


public class Utility {
    public static final String passwordDir = "passwordDir";
    public static final String login = "logindDir";
    public static final String HASHDir = "HASHDir";
    public static final String listDir = "listDir";
    public static final String HOST = "194.67.207.41";
    public static final String PORT = "45678";
    public static final String MD5 = "d55561f495b46e262733602ae825465d";


    public static CopyOnWriteArrayList<Declaration> GetDeclaration(String HASH, String MD5, String HOST, String PORT) {
        Type listType = new TypeToken<CopyOnWriteArrayList<Declaration>>() {}.getType();

        String query = String.format("http://%s:%s/Android/%s/get/declarations/list/%s", HOST, PORT, HASH, MD5);
        try {
            String json = Post(query, true);
            Gson gson = new Gson();
            CopyOnWriteArrayList<Declaration> list = (CopyOnWriteArrayList<Declaration>) gson.fromJson(json, listType);
            return list;
        } catch (Exception ex)

        {
            ex.printStackTrace();
        }
        return null;
    }

    public static String Post(String url, boolean end){
        HttpURLConnection connection = null;

        String query = url;

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
                    if(end){
                        sb.append("\n");
                    }
                }

                return sb.toString();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return "error";
    }

    public static String getPasswordService(String pass, String login){
        String query = String.format("http://%s:%s/Android/%s/%s/getHASH/item/%s", HOST, PORT, login, pass, MD5);
        return Post(query, false);
    }

    public static String setStatusDeclaration(String hash, String declID, String sostzakazID){
        String query = String.format("http://%s:%s/Android/%s/%s/%s/setSOSTZAKAZID/item/%s", HOST, PORT, hash, declID, sostzakazID, MD5);
        return Post(query, false);
    }

    public static void write(Context context, String fileName, String content)
    {
        if( content == null )	content = "";

        try
        {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write( content.getBytes() );

            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String read( Context context, String fileName )
    {
        try
        {
            File f = new File(context.getFilesDir() + "/" + fileName);
            if(f.exists()){
                Log.i("123", "Найден файл: " + fileName);
                FileInputStream in = context.openFileInput(fileName);
                return readInStream(in);
            }
            else {
                Log.i("123", "Не найден файл: " + fileName);
                return "";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    private static String readInStream(FileInputStream inStream)
    {
        try
        {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length = -1;
            while((length = inStream.read(buffer)) != -1 )
            {
                outStream.write(buffer, 0, length);
            }

            outStream.close();
            inStream.close();
            return outStream.toString();
        }
        catch (IOException e)
        {
            Log.i("FileTest", e.getMessage());
        }
        return null;
    }

    public static File createFile( String folderPath, String fileName )
    {
        File destDir = new File(folderPath);
        if (!destDir.exists())
        {
            destDir.mkdirs();
        }
        return new File(folderPath,  fileName + fileName );
    }
}
