package com.example.ugd4_d_0299_100.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ugd4_d_0299_100.Model.Todo;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo")
    List<Todo> getAll();

    @Insert
    void insertTodo(Todo todo);

    @Query("UPDATE todo Set title=:text WHERE ID=:id")
    void updateTodo(String text, int id);

    @Delete
    void deleteTodo(Todo todo);
}
