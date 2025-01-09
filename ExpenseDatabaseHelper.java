package com.example.expensetracker; import android.content.ContentValues; import android.content.Context; import android.database.Cursor; import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteOpenHelper; 
 
public class ExpenseDatabaseHelper extends SQLiteOpenHelper { 
 
    private static final String DATABASE_NAME = "ExpenseTracker.db";     private static final int DATABASE_VERSION = 1; 
 
    private static final String TABLE_NAME = "expenses";     private static final String COLUMN_ID = "id"; 
    private static final String COLUMN_DETAIL = "detail";     private static final String COLUMN_AMOUNT = "amount"; 
 
    public ExpenseDatabaseHelper(Context context) { 
        super(context, DATABASE_NAME, null, DATABASE_VERSION); 
    } 
 
    @Override 
    public void onCreate(SQLiteDatabase db) { 
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" 
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
                + COLUMN_DETAIL + " TEXT, "                 + COLUMN_AMOUNT + " REAL)"; 
        db.execSQL(CREATE_TABLE); 
    } 
 
    @Override 
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);         onCreate(db); 
    } 
 
    public void addExpense(String detail, double amount) { 
        SQLiteDatabase db = this.getWritableDatabase();         ContentValues values = new ContentValues();         values.put(COLUMN_DETAIL, detail); 
        values.put(COLUMN_AMOUNT, amount); 
 
        db.insert(TABLE_NAME, null, values);         db.close(); 
    } 
 
    public void updateExpense(int id, String newDetail, double newAmount) { 
        SQLiteDatabase db = this.getWritableDatabase();         ContentValues values = new ContentValues();         values.put(COLUMN_DETAIL, newDetail); 
        values.put(COLUMN_AMOUNT, newAmount); 
 
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new 
String[]{String.valueOf(id)});         db.close(); 
    } 
 
    public void deleteExpense(int id) { 
        SQLiteDatabase db = this.getWritableDatabase(); 
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});         db.close(); 
    } 
 
    public Cursor getAllExpenses() { 
        SQLiteDatabase db = this.getReadableDatabase(); 
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null); 
    } } 
