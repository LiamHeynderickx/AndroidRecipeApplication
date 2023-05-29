package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ProgramFlow extends AppCompatActivity {

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_flow);
        Intent intent = getIntent();
        path = intent.getStringExtra("PATH");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                menuOption = new Intent(this, ProgramFlow.class);
                menuOption.putExtra("PATH", path);
                startActivity(menuOption);
                finish();
                return true;
            case R.id.menuHelp:
                menuOption = new Intent(this, Help.class);
                menuOption.putExtra("PATH", path);
                startActivity(menuOption);
                finish();
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