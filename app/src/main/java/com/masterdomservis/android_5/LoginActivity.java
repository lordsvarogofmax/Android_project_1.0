package com.masterdomservis.android_5;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.masterdomservis.android_5.utility.Utility;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        // Клик
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                final String pass = mPasswordView.getText().toString();
                final String login = mEmailView.getText().toString();

                MyTask myTask = new MyTask();
                myTask.execute(pass, login, "");

            }
        });
    }


    class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            int maxItem = 3;

            try {
                for(int i = 0; i < maxItem; i++){
                    String tempHASH = Utility.getPasswordService(params[0], params[1]);


                    if(tempHASH == null || tempHASH.equals("") || tempHASH.equals("\n")){
                        if(i + 1 == maxItem){
                            return "Ошибка Авторизации";
                        }
                    }
                    else if(tempHASH.equals("error") || tempHASH.equals("error\n")){
                        if(i + 1 == maxItem){
                            return "Ошибка Авторизации 1";
                        }
                    }
                    else if(tempHASH.equals("error login, password") || tempHASH.equals("error login, password\n")){

                        if(i == 0){
                            Utility.write(getApplicationContext(), Utility.HASHDir, "");
                        }
                        else if(i + 1 == maxItem){
                            Utility.write(getApplicationContext(), Utility.HASHDir, "");
                            return "Не верный логин или пароль";
                        }
                    }
                    else if(tempHASH.length() == 33 || tempHASH.length() == 32){
                        Utility.write(getApplicationContext(), Utility.HASHDir, tempHASH);
                        return "Авторизация прошла успешно.";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Eroor Ошибка Авторизации !!!";
            }
            return "Ошибка Авторизации !!!";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            if(result.equals("Авторизация прошла успешно.")){
                Intent intent;
                intent = new Intent("android.intent.action.HELP");
                startActivity(intent);
            }

        }
    }
}

