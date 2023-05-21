package be.kuleuven.gt.project_recipe;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SearchRecipes extends AppCompatActivity implements RecyclerViewInterface{

    private ArrayList<RecipeInformation> recipeList = new ArrayList<>();
    private ApiManager api = new ApiManager();
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
//    private ArrayList<String> recipeNames= new ArrayList<>();
//    private ArrayList<String> recipeIDs = new ArrayList<>();


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
    // Override onOptionsItemSelected to handle the back button press





    public void onBtnSearchByName_Clicked(View caller){

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        recipeList.clear();
        String recipeName = txtRecipeName.getText().toString();

        getRecipes(recipeName, diet);


//        recyclerView.removeAllViews();
////        getRecipes();
//        ArrayList<RecipeInformation> tempRecipeList = recipeList;
//
//        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),tempRecipeList, this));

    }

    public void onBtnApplySearchParameters_Clicked(View caller){

//        recipeList = api.getRecipes(recipeName);
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



    private void getRecipes(String name, String diet) { //apiConnection

//        ArrayList<RecipeInformation> recipeList = new ArrayList<>();

        String url = "https://api.spoonacular.com/recipes/search?query="+name+"&diet="+diet+"&number=10&apiKey=a97f080d485740608c87a17ef0957691";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject recipeObj = results.getJSONObject(i);

                                // Create a new Recipe object and populate its properties
                                RecipeInformation recipeInformation = new RecipeInformation();
                                recipeInformation.setRecipeName(recipeObj.getString("title"));
                                recipeInformation.setRecipeID(recipeObj.getString("id"));
//                                Log.d("ID ********** ", recipeInformation.getRecipeID());
//                                JSONArray ingredientArr = recipeObj.getJSONArray("missedIngredients");

                                // Add the Recipe object to the recipeList ArrayList
                                recipeList.add(recipeInformation);
                            }
                            recyclerView.setAdapter(new MyAdapter(getApplicationContext(),recipeList, SearchRecipes.this));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle API error
                    }
                });

        // Add the API request to the request queue

        queue.add(jsonObjectRequest);
    }


    @Override
    public void onRecyclerViewItemClick(int position) {
        //intent for new activity
        Intent intent = new Intent(this, RecipeDisplayer.class);

        intent.putExtra("ID", recipeList.get(position).getRecipeID());
        intent.putExtra("NAME", recipeList.get(position).getRecipeName());
        intent.putExtra("USERNAME",username);

        startActivity(intent);

    }


}