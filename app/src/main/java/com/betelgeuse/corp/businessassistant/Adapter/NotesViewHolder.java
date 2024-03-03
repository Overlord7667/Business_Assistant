package com.betelgeuse.corp.businessassistant.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.betelgeuse.corp.businessassistant.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notes_container;
    TextView textView_title, textView_notes, textView_date, alarmText; // alarmText должен быть объявлен здесь

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_container = itemView.findViewById(R.id.notes_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_notes = itemView.findViewById(R.id.textView_notes);
        textView_date = itemView.findViewById(R.id.textView_date);
//        alarmText = itemView.findViewById(R.id.alarmText); // Привязка поля alarmText
    }
}
