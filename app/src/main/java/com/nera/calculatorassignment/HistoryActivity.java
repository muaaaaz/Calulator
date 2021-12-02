package com.nera.calculatorassignment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryActivity extends AppCompatActivity {
    public static final String TAG = "HistoryActivity";
    RecyclerView historyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyRecyclerView = findViewById(R.id.recyclerView_history);
        historyRecyclerView.setAdapter(new CustomAdapter(getApplicationContext()));
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_clear_history:
                (new HistoryFileManagement(getApplicationContext())).clearHistory();
                recreate();
                // TODO: 13/11/2021 Reload activity instead to recreating.
        }
        return super.onOptionsItemSelected(item);
    }
}