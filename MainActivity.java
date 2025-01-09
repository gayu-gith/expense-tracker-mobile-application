package com.example.expensetracker; 
import android.content.Intent; 
import android.os.Bundle; 
import android.widget.Button; 
import androidx.appcompat.app.AppCompatActivity; 
 
public class MainActivity extends AppCompatActivity { 
    @Override 
    protected void onCreate(Bundle savedInstanceState) {         
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main); 
 
        Button trackExpensesButton = findViewById(R.id.track_expenses_button);         
        trackExpensesButton.setOnClickListener(view -> { 
            Intent intent = new Intent(MainActivity.this, ExpensesActivity.class);             
            startActivity(intent); 
        }); 
        findViewById(R.id.view_expenses_button).setOnClickListener(view -> {             
            Intent intent = new Intent(MainActivity.this, ViewExpensesActivity.class);             
            startActivity(intent); 
        }); 
 
    } } 
