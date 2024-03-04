package com.betelgeuse.corp.businessassistant;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.betelgeuse.corp.businessassistant.notes.Notes;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.Manifest;

public class NotesTakerActivity extends AppCompatActivity {
    EditText editText_title, editText_notes;
    ImageView imageView_save;
    Notes notes;
    Button alarmSwitcher;
    boolean isOldNote = false;
    SimpleDateFormat sdf;
    Calendar calendar;

    ImageButton sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);
        Intent serviceIntent = new Intent(this, SystemAlertService.class);
        Intent notificationIntent = new Intent(this, NotificationService.class);
        Intent myIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
        startService(notificationIntent);
        startService(myIntent);

        editText_title = findViewById(R.id.editText_title);
        editText_notes = findViewById(R.id.editText_notes);
        imageView_save = findViewById(R.id.imageView_save);
        alarmSwitcher = findViewById(R.id.alarmSwitcher);
        sendBtn = findViewById(R.id.sendBtn);

        sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        calendar = Calendar.getInstance();

        if (alarmSwitcher != null) {
            alarmSwitcher.setOnClickListener(v -> {
                MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Выберите время для будильника")
                        .build();

                materialTimePicker.addOnPositiveButtonClickListener(view -> {
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                    calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getAlarmActionPendingIntent());
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), getAlarmActionPendingIntent());
                    Toast.makeText(this, "Будильник установлен на: " + sdf.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
                });
                materialTimePicker.show(getSupportFragmentManager(), "tag_picker");
            });
        } else {
            // Обработка случая, когда alarmSwitcher равно null
            Log.e("NotesTakerActivity", "alarmSwitcher is null");
        }

        notes = new Notes();
        try {
            Notes oldNote = (Notes) getIntent().getSerializableExtra("old_note");
            if (oldNote != null) {
                notes = oldNote;
                editText_title.setText(notes.getTitle());
                editText_notes.setText(notes.getNotes());
                isOldNote = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Please enter description ", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                Date date = new Date();


                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });

        sendBtn.setOnClickListener(v -> {
            String title = editText_title.getText().toString();
            String notes = editText_notes.getText().toString();

            // Создаем текст сообщения с заголовком и заметками
            String message = "Title: " + title + "\nNotes: " + notes;

            // Создаем Intent для отправки текстового сообщения
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);

            // Создаем диалоговое окно для выбора приложения
            Intent chooserIntent = Intent.createChooser(sendIntent, "Выберите приложение:");

            // Проверяем, есть ли приложения, которые могут обработать наш Intent
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                // Если есть, запускаем диалоговое окно выбора приложения
                startActivity(chooserIntent);
            } else {
                // Если нет, выводим сообщение об ошибке
                Toast.makeText(NotesTakerActivity.this, "Нет приложений для отправки сообщения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  PendingIntent getAlarmActionPendingIntent(){
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
