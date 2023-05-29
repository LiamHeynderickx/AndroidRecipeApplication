package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
    private Drawable drawable;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            case R.id.menuProgramFlow:
                menuOption = new Intent(this, ProgramFlow.class);
                menuOption.putExtra("PATH", "MAIN");
                startActivity(menuOption);
                finish();
                return true;
            case R.id.menuUMLDiagram:
                menuOption = new Intent(this, UML.class);
                menuOption.putExtra("PATH", "MAIN");
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBtnSearchRecipes_Clicked(View Caller){
        Intent intent = new Intent(this, SearchRecipes.class);
        startActivity(intent);
        finish();
    }

    public void onBtnFavorites_clicked(View Caller){
        Intent intent = new Intent(this, Favorites.class);
        startActivity(intent);
        finish();
    }

    public void onBtnSearchByIngredients_clicked(View Caller){
        Intent intent = new Intent(this, SearchByIngredients.class);
        startActivity(intent);
        finish();
    }

    public void onBtnAddRecipe_clicked(View Caller){
        Intent intent = new Intent(this, memoryGame.class);
        startActivity(intent);
        finish();
    }
}