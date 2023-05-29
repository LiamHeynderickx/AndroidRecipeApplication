package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchRecipes extends AppCompatActivity implements RecyclerViewInterface{

    private ArrayList<RecipeInformation> recipeList = new ArrayList<>();
    private MyAdapter myAdapter = new MyAdapter(this, recipeList, this);

    private ImageButton btnRecipeSelector;
    String diet = "";
    private TextView txtRecipeName;
    private RadioGroup rg;
    private RadioButton rbtnVegetarian;
    private RadioButton rbtnVegan;
    private RecyclerView recyclerView;

    private ImageButton btnSelectedRecipe;
    private String username;

    private GestureDetector gestureDetector;

    private String API_URL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=7387ffbb93ed451ea993a30591711fdc&";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);
        setTitle("Search Recipes");
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        username = prefs.getString("USERNAME", "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myAdapter);

        txtRecipeName = (TextView) findViewById(R.id.txtRecipeName);
        rg = (RadioGroup) findViewById(R.id.rgDiet);
        rbtnVegan = (RadioButton) findViewById(R.id.rbtnIsVegan);
        rbtnVegetarian = (RadioButton) findViewById(R.id.rbtnIsVegetarian);
    }

    public void onBtnSearchByName_Clicked(View caller){

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        recipeList.clear();
        String recipeName = txtRecipeName.getText().toString();

        getRecipes(recipeName, diet);

    }

    int countVegetarian = 0;
    public void onRbtnVegetarian_Clicked(View caller){
        if(countVegetarian == 0){
            diet = "vegetarian";
            countVegetarian++;
            countVegan = 0;
        }
        else{
            rg.clearCheck();
            diet = "";
            countVegetarian = 0;
        }
    }

    int countVegan = 0;
    public void onRbtnVegan_Clicked(View caller){
        if(countVegan == 0){
            diet = "vegan";
            countVegan++;
            countVegetarian = 0;
        }
        else{
            rg.clearCheck();
            diet = "";
            countVegan = 0;
        }
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
            case android.R.id.home: // Handle the back button press
                menuOption = new Intent(this,MainActivity.class);
                startActivity(menuOption);
                //onBackPressed();
                return true;
            case R.id.menuProgramFlow:
                menuOption = new Intent(this, ProgramFlow.class);
                menuOption.putExtra("PATH", "SEARCH");
                startActivity(menuOption);
                finish();
                return true;
            case R.id.menuUMLDiagram:
                menuOption = new Intent(this, UML.class);
                menuOption.putExtra("PATH", "SEARCH");
                startActivity(menuOption);
                finish();
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



    private void getRecipes(String name, String diet) { //apiConnection

        String url = "https://api.spoonacular.com/recipes/search?query="+name+"&diet="+diet+"&number=10&apiKey=a97f080d485740608c87a17ef0957691";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject recipeObj = results.getJSONObject(i);

                            RecipeInformation recipeInformation = new RecipeInformation();
                            recipeInformation.setRecipeName(recipeObj.getString("title"));
                            recipeInformation.setRecipeID(recipeObj.getString("id"));

                            recipeList.add(recipeInformation);
                        }
                        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),recipeList, SearchRecipes.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // Handle API error
                });


        queue.add(jsonObjectRequest);
    }


    @Override
    public void onRecyclerViewItemClick(int position) {
        Intent intent = new Intent(this, RecipeDisplayer.class);

        intent.putExtra("ID", recipeList.get(position).getRecipeID());
        intent.putExtra("NAME", recipeList.get(position).getRecipeName());
        intent.putExtra("USERNAME",username);
        intent.putExtra("PATH", "SEARCH");

        startActivity(intent);

    }


}