package com.betelgeuse.corp.businessassistant;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.Manifest;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class MyService extends Service {
    private static final String TAG = "MyService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service started");

        // Проверяем разрешения и выполняем соответствующие действия
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM) == PackageManager.PERMISSION_GRANTED) {
            // Выполняем действия для управления будильниками
            Log.d(TAG, "Permission to set alarm granted");
        } else {
            Log.d(TAG, "Permission to set alarm not granted");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Выполняем действия для работы с местоположением
            Log.d(TAG, "Permission for location access granted");
        } else {
            Log.d(TAG, "Permission for location access not granted");
        }

        // Вернем START_STICKY, чтобы сервис перезапускался после завершения
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}