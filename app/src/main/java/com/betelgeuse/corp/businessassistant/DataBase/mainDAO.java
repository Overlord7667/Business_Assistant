package com.betelgeuse.corp.businessassistant.DataBase;

import com.betelgeuse.corp.businessassistant.notes.Notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface mainDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (Notes notes);

    @Query ("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAll();

    @Query ("UPDATE notes SET title = :title, notes = :notes WHERE ID = :id")
    void update(int id, String title, String notes);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    LiveData<List<Notes>> getAllNotes(); // LiveData используется для автоматического обновления пользовательского интерфейса

    @Delete
    void delete (Notes notes);
}
