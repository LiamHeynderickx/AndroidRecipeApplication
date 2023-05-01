package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SearchRecipes extends AppCompatActivity {
    private ArrayList<String> listIngredients = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);

    }

    @Override //copy to each new activity
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menue_search_screen, menu);
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
            case R.id.menuHome:
                menuOption = new Intent(this, MainActivity.class);
                startActivity(menuOption);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void defineButtonIngValues() throws FileNotFoundException { //instatiation of array list must move with ing
        Ingredients ingredients = new Ingredients();
        ingredients.populateList();
        listIngredients=ingredients.getIngredientsList();

    }
    public ArrayList<String> filterArray() //for ingredients search
    {
        EditText ingredientText = (EditText) findViewById(R.id.editIngredient);
        String ingredientWritten = ingredientText.getText().toString();

        String filter = ingredientWritten;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return listIngredients.stream()
                    .filter(listIngredients -> listIngredients.startsWith(filter))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }
        else{
            return null;
        }
    }





}