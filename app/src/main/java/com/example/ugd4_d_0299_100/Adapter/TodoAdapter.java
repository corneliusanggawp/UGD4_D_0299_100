package com.example.ugd4_d_0299_100.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ugd4_d_0299_100.Database.DatabaseTodo;
import com.example.ugd4_d_0299_100.Model.Todo;
import com.example.ugd4_d_0299_100.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> todoList;
    private Context context;
    private DatabaseTodo dtd;

    public TodoAdapter(List<Todo> todoList, Context context) {
        this.todoList = todoList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.tvTitle.setText(todo.getTitle());
        dtd = DatabaseTodo.getInstance(context);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo del = todoList.get(holder.getAdapterPosition());
                dtd.getDatabase().todoDao().deleteTodo(del);

                int position = holder.getAdapterPosition();
                todoList.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, todoList.size());
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo edit = todoList.get(holder.getAdapterPosition());
                int id = edit.getId();
                String text = edit.getTitle();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.update_dialog);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width, height);
                dialog.show();

                EditText et = dialog.findViewById(R.id.et);
                Button btn = dialog.findViewById(R.id.btn);

                et.setText(text);
                btn.setOnClickListener(view1 -> {
                    dialog.dismiss();

                    String update = et.getText().toString().trim();
                    dtd.getDatabase().todoDao().updateTodo(update, id);

                    todoList.clear();
                    todoList.addAll(dtd.getDatabase().todoDao().getAll());

                    notifyDataSetChanged();
                });
            }
        });
    }

    @Override
    public int getItemCount() { return todoList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageButton btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}