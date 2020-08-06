package com.example.recipedatabaseoffline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    SQLiteDatabaseClass databaseClass;

    String name;
    String cuisineType;
    String foodDescription;
    String prepTime;
    String cookTime;
    String totalTime;
    String recipeYield;
    String mealType;
    String ingredients;
    String instructions;
    String foodCatgory;

    String[] cuisineArr = {
            "Chinese",
            "Indian",
            "Japanese",
            "Italian",
            "Mexican",
            "Greek",
            "Thai",
            "Lebanese",
            "Spanish",
            "Caribbean",
            "German",
            "Moroccan",
            "French",
            "South Korean",
            "Mediterranean",
            "Seafood",
            "American",
            "Russian"
    };

    Button btnExecQuery;
    EditText edt_cuisine, edt_keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnExecQuery = findViewById(R.id.execQuery);
        edt_cuisine = findViewById(R.id.edt_cuisine);
        edt_keyword = findViewById(R.id.edt_keyword);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();

        databaseClass = new SQLiteDatabaseClass(MainActivity.this);

        if (databaseClass.isDatabaseExist()) {
            // Read All the Data from Firebase and Store in SQLite Database
            for (String cuisine : cuisineArr) {
                mRef.child(cuisine).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            RecipeModelData modelData = dataSnapshot.getValue(RecipeModelData.class);
                            name = modelData.getFoodName();
                            cuisineType = modelData.getCuisineType();
                            foodDescription = modelData.getFoodDescription();
                            prepTime = modelData.getPrepTime();
                            cookTime = modelData.getCookTime();
                            totalTime = modelData.getTotalTime();
                            recipeYield = modelData.getRecipeYield();
                            mealType = modelData.getMealType();
                            foodCatgory = modelData.getFoodCategory();
                            ingredients = "";
                            instructions = "";

                            for (DataSnapshot ingredientsSnap : dataSnapshot.child("ingredients").getChildren()) {
                                String val = ingredientsSnap.getValue(String.class);
                                ingredients = ingredients.concat(val).concat(":");
                            }

                            for (DataSnapshot instructionSnap : dataSnapshot.child("instructions").getChildren()) {
                                String val = instructionSnap.getValue(String.class);
                                instructions = instructions.concat(val);
                            }

                            databaseClass.insertData(MainActivity.this, name, cuisineType, foodDescription, prepTime, cookTime, totalTime, recipeYield, mealType, ingredients, instructions, foodCatgory);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("onCancelled: ", error.getMessage());
                    }
                });
            }
        }

        btnExecQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                // Fetch Query by Keyword
                String[] arr = edt_cuisine.getText().toString().split(" ");
                String keyWord = edt_keyword.getText().toString();

                Cursor cursor = databaseClass.getFoodDetailsByKeyWord(keyWord, arr, true);
                RecipeModelData modelData = new RecipeModelData();
                Log.e("Rows Count", "onClick: " + cursor.getCount() );
                cursor.moveToFirst();

                for (int iter = 0; iter < cursor.getCount(); iter++) {
                    modelData.setFoodName(cursor.getString(0));
                    modelData.setCuisineType(cursor.getString(1));
                    Log.e("Fetch Data by Keyword", "onClick: " + modelData.getFoodName() + " " + modelData.getCuisineType());
                    cursor.moveToNext();
                }
                */

                /*
                // Fetch Query by Food Name
                Cursor cursor = databaseClass.getFoodDetailsByFoodName("Fruit Dip");
                RecipeModelData modelData = new RecipeModelData();
                cursor.moveToFirst();
                for (int iter = 0; iter < cursor.getCount(); iter++) {
                    modelData.setFoodName(cursor.getString(0));
                    modelData.setCuisineType(cursor.getString(1));
                    cursor.moveToNext();
                }

                Log.e("Fetch Data", "onCreate: " + modelData.getFoodName() + " " + modelData.getCuisineType());
                */


                // Fetch Query by Meal Type
                String[] arr = edt_cuisine.getText().toString().split(" ");
                String mealKeyword = edt_keyword.getText().toString();

                Cursor cursor = databaseClass.getFoodDetailsByMealType(mealKeyword, arr, true);
                RecipeModelData modelData = new RecipeModelData();
                Log.e("Rows Count", "onClick: " + cursor.getCount() );
                cursor.moveToFirst();
                for (int iter = 0; iter < cursor.getCount(); iter++) {
                    modelData.setFoodName(cursor.getString(0));
                    modelData.setCuisineType(cursor.getString(1));
                    Log.e("Fetch Data by Meal", "onClick: " + modelData.getFoodName() + " " + modelData.getCuisineType());
                    cursor.moveToNext();
                }


                // End.....
            }
        });
    }
}