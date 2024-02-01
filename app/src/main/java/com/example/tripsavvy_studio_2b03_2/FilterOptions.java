package com.example.tripsavvy_studio_2b03_2;
//Thet Htar San p2235077
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class FilterOptions extends AppCompatActivity {

    private List<String> selectedCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filteroptions);
        selectedCategories = new ArrayList<>();


        Button hilly = findViewById(R.id.hilly);
        Button beach = findViewById(R.id.beach);
        Button attraction = findViewById(R.id.attraction);
        Button island = findViewById(R.id.island);
        Button countryside = findViewById(R.id.countryside);
        Button next = findViewById(R.id.nextbutton);
        Button back = findViewById(R.id.backbutton);
  // Assuming you have some logic to determine the selected category


        hilly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCategory("Hilly");
            }
        });

        beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCategory("Beach");
            }
        });

        island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCategory("Island");
            }
        });

        attraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCategory("Attraction");
            }
        });
        countryside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCategory("Countryside");
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("selectedCategories", (ArrayList<String>) selectedCategories);
                Log.d("In Filter Options", "Filter options: " + selectedCategories.toString());

                setResult(RESULT_OK, resultIntent);

                finish();
            }
        });
    }
    private void toggleCategory(String category) {
        Button clickedButton = null;

        switch (category) {

            case "Hilly":
                clickedButton = findViewById(R.id.hilly);
                break;
            case "Beach":
                clickedButton = findViewById(R.id.beach);
                break;
            case "Island":
                clickedButton = findViewById(R.id.island);
                break;
            case "Attraction":
                clickedButton = findViewById(R.id.attraction);
                break;
            case "Countryside":
                clickedButton = findViewById(R.id.countryside);
                break;
        }

        if (clickedButton != null) {
            // Toggle the color based on the current state
            boolean isButtonSelected = Boolean.TRUE.equals(clickedButton.getTag());
            int colorResId = isButtonSelected ? R.color.grey : R.color.your_selected_color;
            int color = ContextCompat.getColor(this, colorResId);

            clickedButton.setBackgroundTintList(ColorStateList.valueOf(color));

            // Toggle the selection state by toggling the tag value (true to false or false to true)
            clickedButton.setTag(!isButtonSelected);
            if (isButtonSelected) {
                // If the button is being unselected, remove the category from the list
                selectedCategories.remove(category);
            } else {
                // If the button is being selected, add the category to the list
                selectedCategories.add(category);
            }
        }
    }




}
