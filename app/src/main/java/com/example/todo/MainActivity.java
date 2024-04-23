package com.example.todo;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText todoEditText;
    private Button addButton;
    private ListView todoListView;
    private ArrayList<String> todoList = new ArrayList<>();
    private ArrayAdapter<String> todoAdapter;

    private ListPersistence listPersistence = new ListPersistence();
    private String TAG;
    private String path;
    private TextView leastMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        todoEditText = findViewById(R.id.todoEditText);
        addButton = findViewById(R.id.addButton);
        todoListView = findViewById(R.id.todoListView);


        todoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoList);
        todoListView.setAdapter(todoAdapter);



        System.out.println("启动");
        String fileName = "list.ser";
        // 内部存储私有空间
        String directory = getFilesDir().toString();
        path = directory + File.separatorChar + fileName;
        //
        System.out.println(path);
        ArrayList<String> todo_from_file = listPersistence.deserializeList(path);
        for (String item :
                todo_from_file) {
            todoList.add(item);
            //
            System.out.println(item);
        }
        todoAdapter.notifyDataSetChanged();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = todoEditText.getText().toString();
                todo=todo.trim().replaceAll("(\r\n|\r|\n)$", "");
                if (todo.trim().isEmpty()) {
                    todoEditText.setText("");
                } else if (!todo.isEmpty()) {
                    todoList.add(todo);
                    listPersistence.serializeList(todoList, path);
                    todoAdapter.notifyDataSetChanged();
                    todoEditText.setText("");
                }

            }
        });


       todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               // 在长按事件发生时执行删除操作
               String selectedItem = (String) parent.getItemAtPosition(position);
               todoList.remove(selectedItem);
               todoAdapter.notifyDataSetChanged();
               return true;
           }
       });

    }

}
