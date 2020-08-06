package com.example.recipedatabaseoffline;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class SQLiteDatabaseClass extends SQLiteOpenHelper {

    // Database Name
    public static final String DATABASE_NAME = "Recipes.db";
    // Table Name
    public static final String TABLE_NAME = "recipe";
    // Field Name
    public static final String COL1 = "foodname";
    public static final String COL2 = "cuisinetype";
    public static final String COL3 = "description";
    public static final String COL4 = "preptime";
    public static final String COL5 = "cooktime";
    public static final String COL6 = "totaltime";
    public static final String COL7 = "recipeyield";
    public static final String COL8 = "mealtype";
    public static final String COL9 = "ingredients";
    public static final String COL10 = "instructions";
    public static final String COL11 = "foodcategory";


    public SQLiteDatabaseClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
        // Create SQLite Database named Recipes.db
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create Table Query
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " text, " + COL2 + " text, " + COL3 + " text, " + COL4 + " text, " + COL5 + " text, " + COL6 + " text, " + COL7 + " text, " + COL8 + " text, " + COL9 + " text, " + COL10 + " text, " + COL11 + " text);";
        Log.e("DATABASE QUERY", "onCreate: " + query );
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void insertData(Activity activity, String foodName, String cuisineType, String desc, String prepTime, String cookTime, String totalTime, String yield, String mealType, String ing, String ins, String category) {
        SQLiteDatabase database = this.getWritableDatabase();
        // Data Insertion
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, foodName);
        contentValues.put(COL2, cuisineType);
        contentValues.put(COL3, desc);
        contentValues.put(COL4, prepTime);
        contentValues.put(COL5, cookTime);
        contentValues.put(COL6, totalTime);
        contentValues.put(COL7, yield);
        contentValues.put(COL8, mealType);
        contentValues.put(COL9, ing);
        contentValues.put(COL10, ins);
        contentValues.put(COL11, category);

        long rowInserted = database.insert(TABLE_NAME, null, contentValues);

        if (rowInserted != -1) {
            Log.e(foodName + " " + cuisineType, "insertData: Inserted" );
        } else {
            Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
            Log.e(foodName, "insertDataError: Error at Some Point");
        }

        database.close();
    }

    public Cursor getFoodDetailsByKeyWord(String keyWord, String[] cuisineArr, boolean isVeg) {
        Cursor cursor = null;
        SQLiteDatabase database = this.getReadableDatabase();

        // Inner Query for Fetching Food Details by Selected Cuisine
        String cuisine_Query = cuisineQuery(cuisineArr, isVeg);

        // Fetch Food Details by Keyword
        String query = "SELECT * FROM ( " + cuisine_Query + " ) WHERE " + COL1 + " LIKE '%" + keyWord + "%'" + " OR " + COL3 + " LIKE '%" + keyWord + "%'"  + " OR " + COL9 + " LIKE '%" + keyWord + "%'" + " OR " + COL10 + " LIKE '%" + keyWord + "%';";

        Log.e("Execute Query", "getFoodDetailsByKeyWord: " + query);
        cursor = database.rawQuery(query, null);
        return cursor;
    }

    private String cuisineQuery(String[] cuisineArr, boolean isVeg) {
        String isVegQuery = categoryQuery(isVeg);
        String innerQuery = null;

        if (isVegQuery == null) {
            innerQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = \"" + cuisineArr[0] + "\"";
        } else {
            innerQuery = "SELECT * FROM ( " + isVegQuery + " ) WHERE " + COL2 + " = \"" + cuisineArr[0] + "\"";
        }

        for (int cuisineIter = 1; cuisineIter < cuisineArr.length; cuisineIter++) {
            innerQuery = innerQuery.concat(" OR " + COL2 + " = \"" + cuisineArr[cuisineIter] + "\"");
        }

        return innerQuery;
    }

    private String categoryQuery(boolean isVeg) {
        String innerQuery = null;

        if (isVeg) {
            innerQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL11 + " = \"" + "Veg" + "\"";
        }

        return innerQuery;
    }

    public Cursor getFoodDetailsByMealType(String meal, String[] cuisineArr, boolean isVeg) {
        Cursor cursor = null;
        SQLiteDatabase database = this.getReadableDatabase();

        String cuisine_Query = cuisineQuery(cuisineArr, isVeg);

        String query = "SELECT * FROM ( " + cuisine_Query + " ) WHERE " + COL8 + " =\"" +  meal + "\";";
        cursor = database.rawQuery(query, null);

        return cursor;
    }

    public Cursor getFoodDetailsByFoodName(String foodName) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = \"" + foodName + "\";";
        Cursor cursor = database.rawQuery(selectQuery, null);
        return cursor;
    }

    public boolean isDatabaseExist() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cur = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return  cur.getCount() == 0;
    }
}
