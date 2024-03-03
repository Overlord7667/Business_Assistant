package com.betelgeuse.corp.businessassistant;

import androidx.cardview.widget.CardView;

import com.betelgeuse.corp.businessassistant.notes.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);

}
