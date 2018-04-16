package com.masterdomservis.android_5;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.masterdomservis.android_5.pojo.Declaration;
import com.masterdomservis.android_5.utility.Utility;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Intent serviceIntent;
    private MyService myService;
    private ServiceConnection serviceConnection;
    private boolean isServiceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView main_info = (TextView) findViewById(R.id.main_info); // ЗАГОЛОВОК
        ImageButton settins_button = (ImageButton) findViewById(R.id.settins_button);

        ImageView master = (ImageView) findViewById(R.id.master_button);
        ImageView zayavka = (ImageView) findViewById(R.id.zayavka_button);
        ImageView adress = (ImageView) findViewById(R.id.adress_button);
        ImageView help = (ImageView) findViewById(R.id.help_button);

        settins_button.setOnClickListener(this);

        master.setOnClickListener(this);
        zayavka.setOnClickListener(this);
        adress.setOnClickListener(this);
        help.setOnClickListener(this);

        serviceIntent = new Intent(getApplicationContext(), MyService.class);
        startService(serviceIntent);
        bindService();


        Runnable runnable = new Runnable() {
            public void run() {
                int count;
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();

                String listStr = Utility.read(getApplicationContext(), Utility.listDir);

                if (!listStr.equals("")) {
                    Type listType = new TypeToken<CopyOnWriteArrayList<Declaration>>() {
                    }.getType();

                    Gson gson = new Gson();
                    CopyOnWriteArrayList<Declaration> list = (CopyOnWriteArrayList<Declaration>) gson.fromJson(listStr, listType);
                    count = list.size();
                    bundle.putString("Key", String.valueOf(count));
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }

                while (true){
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bundle = new Bundle();
                    count = getCountDeclaration();
                    bundle.putString("Key", String.valueOf(count));
                    msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.settins_button:
                intent = new Intent("android.intent.action.PREF");
                startActivity(intent);
                break;
            case R.id.master_button:
                intent = new Intent("android.intent.action.MASTER");
                startActivity(intent);
                break;
            case R.id.zayavka_button:
                intent = new Intent("android.intent.action.ZAYAVKA");

                List<Declaration> listing = getDeclaration();
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) listing);
                intent.getParcelableArrayListExtra(String.valueOf(getDeclaration()));

                startActivity(intent);
                break;
            case R.id.adress_button:
                intent = new Intent("android.intent.action.KARTA");
                startActivity(intent);
                break;
            case R.id.help_button:
                intent = new Intent("android.intent.action.HELP");
                startActivity(intent);
                break;
        }
    }

    private void bindService() {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {

                    MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder) service;
                    myService = myServiceBinder.getService();
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound = false;
                }
            };
        }

        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    private void unbindService() {
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    private int getCountDeclaration() {

        if (isServiceBound) {
            CopyOnWriteArrayList<Declaration> list = myService.getDeclaration();
            if(list != null && list.size() > 0){
                return list.size();
            }
            else{
                return 0;
            }

        } else {
            return 0;
        }
    }

    private ArrayList<Declaration> getDeclaration() {
        CopyOnWriteArrayList<Declaration> list;
        if (isServiceBound) {
            list = myService.getDeclaration();
            return new ArrayList<Declaration>(list);
        } else {
            return new ArrayList<Declaration>();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TextView main_info = (TextView) findViewById(R.id.main_info); // ЗАГОЛОВОК
            Bundle bundle = msg.getData();
            String date = bundle.getString("Key");
            String count = date;
            if (count == null || count.equals("") || count.equals("0")) {
                main_info.setText("Заявок нет");
            } else {
                main_info.setText(String.format("Количество заявок %s", count));
            }
        }

    };
}
