package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private ImageButton btnSearchRecipe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearchRecipe = (ImageButton) findViewById(R.id.btnSearchRecipes);
    }

    @Override //copy to each new activity
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private Intent menuOption; // copy to each activity
    @Override // copy to each activity
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuSettings:
                menuOption = new Intent(this, Settings.class);
                startActivity(menuOption);
                return true;
            case R.id.menuHelp:
                menuOption = new Intent(this, Help.class);
                startActivity(menuOption);
                return true;
            case R.id.menuLogout:
                menuOption = new Intent(this, Login.class);
                startActivity(menuOption);
                finish();
                return true;
            case R.id.menuHome:
                menuOption = new Intent(this, MainActivity.class);
                startActivity(menuOption);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBtnSearchRecipes_Clicked(View Caller){

        Intent intent = new Intent(this, SearchRecipes.class);
        startActivity(intent);
        finish();

    }


}