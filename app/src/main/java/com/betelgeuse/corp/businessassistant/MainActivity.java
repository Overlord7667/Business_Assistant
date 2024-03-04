package com.betelgeuse.corp.businessassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW = 123456;
    private static final int REQUEST_SYSTEM_ALERT_WINDOW = 1234;
    private static final int MY_PERMISSIONS_REQUEST_SET_ALARM = 423;
    private static final int MY_PERMISSIONS_REQUEST_DISABLE_KEYGUARD = 45345;
    private static final int MY_PERMISSIONS_REQUEST_MANAGE_OVERLAY_PERMISSION = 5635634;
    private static final int REQUEST_BACKGROUND_PERMISSION = 333;
    ImageButton noteButton;
    ImageButton weatherButton;
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1234;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 12345;
    private static final int REQUEST_IGNORE_BATTERY_OPTIMIZATIONS = 9876;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent serviceIntent = new Intent(this, SystemAlertService.class);
        Intent notificationIntent = new Intent(this, NotificationService.class);
        Intent myIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
        startService(notificationIntent);
        startService(myIntent);

        noteButton = findViewById(R.id.note_btn).findViewById(R.id.imageButton);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        weatherButton = findViewById(R.id.weatherButton).findViewById(R.id.img_weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent2);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SET_ALARM,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SYSTEM_ALERT_WINDOW}, 0);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW}, MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW);
        }

        // Запрашиваем разрешение для отображения на экране блокировки
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.DISABLE_KEYGUARD) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.DISABLE_KEYGUARD}, MY_PERMISSIONS_REQUEST_DISABLE_KEYGUARD);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            // Открываем настройки для предоставления разрешения
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, MY_PERMISSIONS_REQUEST_MANAGE_OVERLAY_PERMISSION);
        }

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (!powerManager.isIgnoringBatteryOptimizations(getPackageName())) {
            // Если разрешение не предоставлено, запрашиваем его у пользователя
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        }

        // Проверяем, предоставлено ли разрешение
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !hasBackgroundPermission()) {
            // Если разрешение не предоставлено, показываем диалоговое окно с запросом разрешения
            requestBackgroundPermission();
        }

        // Создаем интент для запуска MyForegroundService
        Intent myForegroundServiceIntent = new Intent(this, MyForegroundService.class);
        // Запускаем службу
        startService(myForegroundServiceIntent);
    }
    private boolean hasBackgroundPermission() {
        // Проверяем, предоставлено ли разрешение
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
    private void requestBackgroundPermission() {
        // Запрашиваем разрешение на работу в фоне
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                REQUEST_BACKGROUND_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SET_ALARM:
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
            case MY_PERMISSIONS_REQUEST_SYSTEM_ALERT_WINDOW:
            case MY_PERMISSIONS_REQUEST_DISABLE_KEYGUARD:
                // Проверяем, было ли предоставлено разрешение
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Разрешение предоставлено, выполните необходимые действия
                    // Например, установите будильник или покажите всплывающее окно
                } else {
                    // Разрешение не предоставлено, возможно, нужно сообщить пользователю об этом
                }
                if (requestCode == MY_PERMISSIONS_REQUEST_MANAGE_OVERLAY_PERMISSION) {
                    // Проверяем, было ли предоставлено разрешение
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                        // Разрешение предоставлено, выполните необходимые действия
                    } else {
                        // Разрешение не предоставлено, возможно, нужно сообщить пользователю об этом
                    }
                }
                if (requestCode == REQUEST_BACKGROUND_PERMISSION) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Пользователь предоставил разрешение, можно продолжать работу в фоне
                    } else {
                        // Пользователь не предоставил разрешение, приложение будет ограничено в работе в фоне
                        Toast.makeText(this, "Без разрешения на работу в фоне приложение будет ограничено", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SYSTEM_ALERT_WINDOW) {
            if (Settings.canDrawOverlays(this)) {
                // Разрешение для отображения всплывающих окон было предоставлено
            } else {
                // Разрешение для отображения всплывающих окон не было предоставлено
            }
            if (requestCode == REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) {
                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                if (powerManager.isIgnoringBatteryOptimizations(getPackageName())) {
                    // Разрешение на игнорирование оптимизации батареи было предоставлено
                } else {
                    // Разрешение на игнорирование оптимизации батареи не было предоставлено
                }
            }
            if (requestCode == MY_PERMISSIONS_REQUEST_MANAGE_OVERLAY_PERMISSION) {
                // Проверяем, было ли предоставлено разрешение
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                    // Разрешение предоставлено, выполните необходимые действия
                } else {
                    // Разрешение не предоставлено, возможно, нужно сообщить пользователю об этом
                }
            }
        }
    }
}