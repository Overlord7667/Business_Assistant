package com.betelgeuse.corp.businessassistant;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {
    // Определение CHANNEL_ID
    public static final String CHANNEL_ID = "my_channel_id";
    @Override
    public void onCreate() {
        super.onCreate();

        // Создание канала уведомлений
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Зарегистрируем канал в системе
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Разместите ваш код для выполнения фоновых задач здесь
        // Этот метод вызывается при каждом запуске службы

        // Отображение уведомления пользователю о работе службы
        Notification notification = createNotification();
        startForeground(1, notification);

        // Верните флаг, указывающий, как следует вести с службой после ее завершения
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Метод onBind() обязателен, но в данном случае служба не связана с активностью
        return null;
    }

    private Notification createNotification() {
        // Создайте уведомление о работе службы
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("MyForegroundService")
                .setContentText("Служба работает в фоновом режиме")
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder.build();
    }

}
