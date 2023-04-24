package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private ImageButton btnSearchRecipe;


    //help and settings
    private ImageButton btnhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("319f95"));
//        getSupportActionBar().setBackgroundDrawable(colorDrawable);  //couldnt find a colour i like
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);

        setContentView(R.layout.activity_main);

        btnSearchRecipe = (ImageButton) findViewById(R.id.btnSearchRecipes);
    }

    public void onBtnSearchRecipes_Clicked(View Caller){

        Intent intent = new Intent(this, SearchRecipes.class);
        startActivity(intent);
        finish();

    }


}