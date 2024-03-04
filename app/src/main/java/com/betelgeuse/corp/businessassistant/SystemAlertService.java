package com.betelgeuse.corp.businessassistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SystemAlertService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Код для отображения всплывающего окна
        return START_NOT_STICKY;
    }
}