package sg.edu.rp.c346.id22012867.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ListView lv;
    EditText etTask, etDate;
    ArrayAdapter<Task> aaTasks;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lv = findViewById(R.id.lv);
        etTask = findViewById(R.id.editTask);
        etDate = findViewById(R.id.editDate);

        DBHelper Db = new DBHelper(MainActivity.this);
        tasks = Db.getTasks();
        aaTasks = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, tasks);
        lv.setAdapter(aaTasks);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                String newTask = etTask.getText().toString();
                String newDate = etDate.getText().toString();
                db.insertTask(newTask, newDate);

            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<String> data = db.getTaskContent();
                db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i + ". " + data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResults.setText(txt);
                updateTaskList();
            }
        });
    }

    private void updateTaskList() {
        DBHelper Db = new DBHelper(MainActivity.this);
        tasks.clear();
        tasks.addAll(Db.getTasks());
        aaTasks.notifyDataSetChanged();
    }
}
