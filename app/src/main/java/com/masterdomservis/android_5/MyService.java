package com.masterdomservis.android_5;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.masterdomservis.android_5.utility.Utility;
import com.masterdomservis.android_5.pojo.Declaration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


public class MyService extends Service{
    private Utility utility = new Utility();
    public static CopyOnWriteArrayList<Declaration> list = new CopyOnWriteArrayList<Declaration>();
    private MyThreadDeclaration myThread;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("12345", "In onStartCommand, thread id: " + Thread.currentThread().getId());

        myThread = new MyThreadDeclaration(list);
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

        Log.i("12345", "In onDestroy, thread id: " + Thread.currentThread().getId());

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
    private long sleeping = 5000;
    private boolean stop = false;
    public MyThreadDeclaration(CopyOnWriteArrayList<Declaration> list) {
        this.listing = null;
    }

    public void run(){
        while (true){
            Log.i("12345", "Start RUN");
            if(stop){
                try {
                    sleep(sleeping);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stop = false;
            }
            listing = utility.GetDeclaration("1", "2", "3", "4");
            if (listing != null){
                Log.i("12345", "size " + String.valueOf(listing.size()));
                MyService.list = listing;
            }
            else {
                Log.i("12345", "size 0");
            }
            try {
                sleep(5000);
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
