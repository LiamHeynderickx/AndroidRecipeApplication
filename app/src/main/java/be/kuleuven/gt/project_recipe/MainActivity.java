package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private ImageButton btnSearchRecipe;
    private ImageButton btnFavorites;
    private ImageButton btnSearchByIngredients;
    private ImageButton btnAddRecipe;


    private String username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setTitle("Home");

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");

        btnSearchRecipe = (ImageButton) findViewById(R.id.btnSearchRecipes);
        btnFavorites = (ImageButton) findViewById(R.id.btnFavorites);
        btnSearchByIngredients = (ImageButton) findViewById(R.id.btnSearchByIngredients);
        btnAddRecipe = (ImageButton) findViewById(R.id.btnAddRecipe);



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
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();

    }

    public void onBtnFavorites_clicked(View Caller){
        Intent intent = new Intent(this, Favorites.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }

    public void onBtnSearchByIngredients_clicked(View Caller){
        Intent intent = new Intent(this, SearchByIngredients.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
        finish();
    }

    public void onBtnAddRecipe_clicked(View Caller){
        Intent intent = new Intent(this, AddRecipe.class);
        startActivity(intent);
        finish();
    }
}