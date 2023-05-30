package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class UML extends AppCompatActivity {

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        path = intent.getStringExtra("PATH");
    }

    @Override //copy to each new activity
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menue_recipe_displayer, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private Intent menuOption; // copy to each activity
    @Override // copy to each activity
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: // Handle the back button press
                switch (path){
                    case "FAVORITES":
                        menuOption = new Intent(this,Favorites.class);
                        startActivity(menuOption);
                        finish();
                        return true;
                    case "SEARCH":
                        menuOption = new Intent(this,SearchRecipes.class);
                        startActivity(menuOption);
                        finish();
                        return true;
                    case "INGREDIENTS":
                        menuOption = new Intent(this, SearchByIngredients.class);
                        startActivity(menuOption);
                        finish();
                        return true;
                    case "MAIN":
                        menuOption = new Intent(this, MainActivity.class);
                        startActivity(menuOption);
                        finish();
                        return true;
                    case "MINIGAME":
                        menuOption = new Intent(this, memoryGame.class);
                        startActivity(menuOption);
                        finish();
                        return true;
                    default:
                        break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}