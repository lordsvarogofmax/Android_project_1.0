package com.masterdomservis.android_5;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.masterdomservis.android_5.utility.Utility;
import com.masterdomservis.android_5.pojo.Declaration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import android.content.Context;
import com.google.gson.Gson;

public class MyService extends Service{
    private Utility utility = new Utility();

    public static CopyOnWriteArrayList<Declaration> list = new CopyOnWriteArrayList<Declaration>();
    private MyThreadDeclaration myThread;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("12345", "In onStartCommand, thread id: " + Thread.currentThread().getId());

        myThread = new MyThreadDeclaration(list, getBaseContext());
        myThread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {

        if(list != null && list.size() > 0){
            Type listType = new TypeToken<CopyOnWriteArrayList<Declaration>>(){}.getType();
            Gson gson = new Gson();
            String json = gson.toJson(list, listType);

            Utility.write(getBaseContext(), Utility.listDir, json);

            Log.i("12345", "Запись LIST: " + Thread.currentThread().getId());
        }


        super.onDestroy();
    }

    public class MyServiceBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    private IBinder mBinder = new MyServiceBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("12345", "In onBind, thread id: " + Thread.currentThread().getId());

        return mBinder;
    }

    public CopyOnWriteArrayList<Declaration> getDeclaration(){
        myThread.Sleep(10000);
        CopyOnWriteArrayList<Declaration> listing;
        if(list == null){
            listing = new CopyOnWriteArrayList<Declaration>();
        }
        else {
            listing = new CopyOnWriteArrayList<Declaration>(list);
        }
        return listing;
    }
}

class MyThreadDeclaration extends Thread{
    private Utility utility = new Utility();
    private CopyOnWriteArrayList<Declaration> listing;
    Context context;
    private long sleeping = 5000;
    private long sleepingMain = 5000;
    private boolean stop = false;
    public MyThreadDeclaration(CopyOnWriteArrayList<Declaration> list, Context context) {
        this.listing = null;
        this.context = context;
    }

    public void run(){
        String HASH = Utility.read(context, Utility.HASHDir);
        CopyOnWriteArrayList<Declaration> nullList = new CopyOnWriteArrayList<>();
        while (true){
            Log.i("12345", "Start RUN Service");
            if(stop){
                try {
                    sleep(sleeping);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stop = false;
            }


            HASH = Utility.read(context, Utility.HASHDir);

            if(!HASH.equals("")){
                listing = utility.GetDeclaration(HASH, Utility.MD5, Utility.HOST, Utility.PORT);
                if (listing != null){
                    Log.i("12345", "size " + String.valueOf(listing.size()));
                    MyService.list = listing;

                    if(listing != null && listing.size() > 0){
                        Type listType = new TypeToken<CopyOnWriteArrayList<Declaration>>(){}.getType();
                        Gson gson = new Gson();
                        String json = gson.toJson(listing, listType);

                        Utility.write(context, Utility.listDir, json);

                        Log.i("12345", "Запись LIST: " + Thread.currentThread().getId());
                    }

                }
                else {
                    MyService.list = nullList;
                    Utility.write(context, Utility.listDir, "");
                    Log.i("12345", "size 0");
                }
            }
            else {
                MyService.list = nullList;
                Utility.write(context, Utility.listDir, "");
            }

            try {
                sleep(sleepingMain);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void Sleep(long m){
        sleeping = m;
        stop = true;
    }

}
