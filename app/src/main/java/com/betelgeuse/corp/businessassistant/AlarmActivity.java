package com.betelgeuse.corp.businessassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {

    Ringtone ringtone;
    Button btnOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        btnOff = findViewById(R.id.offBtn);

        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, notificationUri);
        if (ringtone == null){
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(this, notificationUri);
        }
        if (ringtone != null){
            ringtone.play();
        }
        btnOff.setOnClickListener(v -> {
            stopAlarm();
            Toast.makeText(AlarmActivity.this, "Выключили", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        if (ringtone != null && ringtone.isPlaying()){
            ringtone.stop();
        }
        super.onDestroy();
    }

    private void stopAlarm() {
        if (ringtone != null && ringtone.isPlaying()){
            ringtone.stop();
        }
    }
}