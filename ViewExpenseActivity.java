package com.example.expensetracker; 
import android.app.AlertDialog; 
import android.database.Cursor; 
import android.os.Bundle; 
import android.text.InputType; 
import android.util.Log; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.LinearLayout; 
import android.widget.TextView; 
import android.widget.Toast; 
 
import androidx.appcompat.app.AppCompatActivity; 
 
public class ViewExpensesActivity extends AppCompatActivity { 
 
    private ExpenseDatabaseHelper dbHelper; 
    private LinearLayout expenseListLayout; 
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) {         
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_view_expenses); 
 
        dbHelper = new ExpenseDatabaseHelper(this); 
        expenseListLayout = findViewById(R.id.expense_list_layout); 
 
        loadExpenses(); 
    } 
 
    private void loadExpenses() { 
        Cursor cursor = dbHelper.getAllExpenses(); 
        Log.d("ViewExpensesActivity", "Number of records fetched: " + cursor.getCount()); 
 
        if (cursor.getCount() == 0) { 
            TextView noDataView = new TextView(this);             
            noDataView.setText("No expenses found.");             
            noDataView.setTextSize(18);             
            expenseListLayout.addView(noDataView);             
            return; 
        } 
 
        while (cursor.moveToNext()) { 
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id")); 
            String detail = cursor.getString(cursor.getColumnIndexOrThrow("detail"));             
            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount")); 
 
            LinearLayout expenseRow = new LinearLayout(this);             
            expenseRow.setOrientation(LinearLayout.HORIZONTAL);             
            expenseRow.setPadding(10, 10, 10, 10); 
 
            TextView expenseView = new TextView(this);             
            expenseView.setText(detail + " - $" + amount); 
            expenseView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));             
            expenseRow.addView(expenseView); 
 
            Button updateButton = new Button(this); 
            updateButton.setText("Update"); 
            updateButton.setOnClickListener(v -> showUpdateDialog(id, detail, amount));             
            expenseRow.addView(updateButton); 
 
            Button deleteButton = new Button(this);             
            deleteButton.setText("Delete");             
            deleteButton.setOnClickListener(v -> {                 
                dbHelper.deleteExpense(id); 
                expenseListLayout.removeView(expenseRow); 
                Toast.makeText(this, "Expense deleted!", Toast.LENGTH_SHORT).show(); 
            }); 
            expenseRow.addView(deleteButton); 
 
            expenseListLayout.addView(expenseRow); 
        } 
        cursor.close(); 
    } 
 
    private void showUpdateDialog(int id, String oldDetail, double oldAmount) { 
        AlertDialog.Builder builder = new AlertDialog.Builder(this);         
        builder.setTitle("Update Expense"); 
 
        LinearLayout dialogLayout = new LinearLayout(this);         
        dialogLayout.setOrientation(LinearLayout.VERTICAL); 
 
        EditText detailInput = new EditText(this);         
        detailInput.setHint("Expense Detail");         
        detailInput.setText(oldDetail); 
        dialogLayout.addView(detailInput); 
 
        EditText amountInput = new EditText(this);         
        amountInput.setHint("Amount"); 
        amountInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL); 
        amountInput.setText(String.valueOf(oldAmount));         
        dialogLayout.addView(amountInput); 
 
        builder.setView(dialogLayout); 
 
        builder.setPositiveButton("Update", (dialog, which) -> {             
            String newDetail = detailInput.getText().toString(); 
            double newAmount = Double.parseDouble(amountInput.getText().toString()); 
 
            dbHelper.updateExpense(id, newDetail, newAmount); 
            Toast.makeText(this, "Expense updated!", Toast.LENGTH_SHORT).show();             
            recreate(); 
        }); 
 
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());         
        builder.show(); 
    } } 
