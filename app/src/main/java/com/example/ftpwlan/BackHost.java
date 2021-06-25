package com.example.ftpwlan;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

public class BackHost extends AsyncTask<Void,Void,Void> {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(Void... voids) {
        try {

                new Server();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
