package com.example.ugd4_d_0299_100;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugd4_d_0299_100.Adapter.TodoAdapter;
import com.example.ugd4_d_0299_100.Database.DatabaseTodo;
import com.example.ugd4_d_0299_100.Model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    private EditText edt_todo;
    private Button btnReset, btnAdd;
    private RecyclerView rv_todoList;
    private List<Todo> todoList;
    private TodoAdapter todoAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edt_todo = view.findViewById(R.id.edt_todo);
        btnReset = view.findViewById(R.id.btnReset);
        btnAdd = view.findViewById(R.id.btnAdd);
        rv_todoList = view.findViewById(R.id.rv_todoList);

        rv_todoList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_todo.getText().toString().isEmpty()) {
                    addTodo();
                } else {
                    Toast.makeText(TodoFragment.this.getContext(), "Data Belum Diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_todo.setText("");
            }
        });

        getTodos();
        todoList = new ArrayList<>();
    }

    private void addTodo() {
        final String title = edt_todo.getText().toString();

        class AddTodo extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Todo todo = new Todo();
                todo.setTitle(title);

                DatabaseTodo.getInstance(getActivity().getApplicationContext()).getDatabase().todoDao().insertTodo(todo);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);

                Toast.makeText(TodoFragment.this.getContext(), "Berhasil menambahkan data", Toast.LENGTH_SHORT).show();
                edt_todo.setText("");
                getTodos();
            }
        }

        AddTodo addTodo = new AddTodo();
        addTodo.execute();
    }

    private void getTodos() {
        class GetTodos extends AsyncTask<Void, Void, List<Todo>> {
            @Override
            protected List<Todo> doInBackground(Void... voids) {
                List<Todo> todoList = DatabaseTodo.getInstance(getActivity().getApplicationContext()).getDatabase().todoDao().getAll();
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                super.onPostExecute(todos);

                todoAdapter = new TodoAdapter(todos, TodoFragment.this.getContext());
                rv_todoList.setAdapter(todoAdapter);
            }
        }

        GetTodos getTodos = new GetTodos();
        getTodos.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }
}

