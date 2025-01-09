package com.example.expensetracker; 
import android.os.Bundle; 
import android.text.Editable; 
import android.text.TextWatcher; 
import android.util.Log; 
import android.view.View; 
import android.widget.EditText; 
import android.widget.LinearLayout; 
import android.widget.TextView; 
import android.widget.Toast; 
import androidx.appcompat.app.AppCompatActivity; 
public class ExpensesActivity extends AppCompatActivity {     
    private LinearLayout tableLayout;     
    private TextView totalSum;     
    private ExpenseDatabaseHelper dbHelper; 
    @Override     
    protected void onCreate(Bundle savedInstanceState) {         
        super.onCreate(savedInstanceState);         
        setContentView(R.layout.activity_expenses);         
        tableLayout = findViewById(R.id.table_layout);         
        totalSum = findViewById(R.id.total_sum);         
        dbHelper = new ExpenseDatabaseHelper(this);         
        findViewById(R.id.add_row_button).setOnClickListener(view -> addNewRow());         
        findViewById(R.id.save_button).setOnClickListener(view -> {             
            saveExpensesToDatabase(); 
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show(); 
            finish(); 
        }); 
    } 
    private void addNewRow() { 
        View row = getLayoutInflater().inflate(R.layout.row_expense, null);         
        EditText amountField = row.findViewById(R.id.amount_field);         
        amountField.addTextChangedListener(new TextWatcher() { 
            @Override             
            public void afterTextChanged(Editable s) {                 
                calculateTotal(); 
            } 
            @Override             
            public void beforeTextChanged(CharSequence s, int start, int count, int after){} 
            @Override             
            public void onTextChanged(CharSequence s, int start, int before, int count){} 
        }); 
        tableLayout.addView(row); 
    } 
 private void calculateTotal() {         
    double total = 0;         
    for (int i = 0; i < tableLayout.getChildCount(); i++) { 
            View row = tableLayout.getChildAt(i); 
            EditText amountField = row.findViewById(R.id.amount_field); 
            String amountText = amountField.getText().toString();             
            double amount = amountText.isEmpty() ? 0 : Double.parseDouble(amountText); 
            total += amount; 
        } 
        totalSum.setText(String.format(": %.2f", total)); 
    } 
    private void saveExpensesToDatabase() {         
        for (int i = 0; i < tableLayout.getChildCount(); i++) { 
            View row = tableLayout.getChildAt(i); 
            EditText detailField = row.findViewById(R.id.expense_detail); 
            EditText amountField = row.findViewById(R.id.amount_field); 
            String detail = detailField.getText().toString();             
            String amountText = amountField.getText().toString();             
            double amount = amountText.isEmpty() ? 0 : Double.parseDouble(amountText);             
            if (!detail.isEmpty() && amount > 0) {                 
                dbHelper.addExpense(detail, amount); 
                Log.d("ExpensesActivity", "Saved expense: " + detail + " - " + amount); 
            } 
        } 
    } 
} 
