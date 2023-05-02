package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SearchRecipes extends AppCompatActivity {

    private ArrayList<RecipeInformation> tempRecipeList = new ArrayList<>();
    private TextView lblRecipeName;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);

        tempRecipeList.add(new RecipeInformation("pasta"));
        tempRecipeList.add(new RecipeInformation("rice"));
        tempRecipeList.add(new RecipeInformation("soup"));
        tempRecipeList.add(new RecipeInformation("pie"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("salad"));
        tempRecipeList.add(new RecipeInformation("wrap"));
        tempRecipeList.add(new RecipeInformation("steak"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("burger"));
        tempRecipeList.add(new RecipeInformation("burger"));



        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }


    public void onBtnSearchByName_Clicked(View caller){

            recyclerView.setAdapter(new MyAdapter(getApplicationContext(),tempRecipeList));

//            Log.d("SEARCH BUTTON SELECTED", "onBtnSearchByName_Clicked: "+tempRecipeList.get(count).getRecipeName());

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








}