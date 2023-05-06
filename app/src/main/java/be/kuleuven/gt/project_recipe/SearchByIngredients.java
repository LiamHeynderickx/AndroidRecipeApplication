package be.kuleuven.gt.project_recipe;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SearchByIngredients extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<String> listIngredients = new ArrayList<>();
    private ArrayList<RecipeInformation> recipeList = new ArrayList<>();
    private ApiManager api = new ApiManager();
    private MyAdapter myAdapter = new MyAdapter(this, recipeList);

    private Button btnAdd;
    private ChipGroup chipGroup;
    private TextView txtNewIngredient;
    private String newIngredient;
    private RecyclerView recyclerView2;
    private String fullIngredientsQrl="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_ingredients);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerViewByIngredients);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        btnAdd = (Button) findViewById(R.id.btnAddIngredient);
        chipGroup = (ChipGroup) findViewById(R.id.cgIngredients);
        txtNewIngredient = (TextView)findViewById(R.id.txtIngredient);

    }


    // ON CLICK METHODS:
    public void onBtnSearchByIngredient_Clicked(View caller){

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        recyclerView2.removeAllViews();
//        getRecipes();
        ArrayList<RecipeInformation> tempRecipeList = recipeList;

        recyclerView2.setAdapter(new MyAdapter(getApplicationContext(),tempRecipeList));

    }

    public void onBtnAdd_clicked(View Caller){

        newIngredient = txtNewIngredient.getText().toString();

        if(!newIngredient.isEmpty()){
            Chip chip = new Chip(this);
            chip.setText(newIngredient);
            chip.setCloseIconVisible(true);
            chip.setId(ViewCompat.generateViewId());
            chip.setOnClickListener(this);
            chip.setVisibility(View.VISIBLE);
            chipGroup.addView(chip);

            recipeList.clear();
            if(fullIngredientsQrl == "" )
            {
                fullIngredientsQrl += newIngredient;
            }
            else {

                fullIngredientsQrl += ",+" + newIngredient;
            }

            getRecipesUsingIngredients(fullIngredientsQrl);
        }
        txtNewIngredient.setText("");


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




    public void defineButtonIngValues() throws FileNotFoundException { //instatiation of array list
        Ingredients ingredients = new Ingredients();
        listIngredients = ingredients.getIngredientsList();
    }
    public ArrayList<String> filterArray() //for ingredients search
    {
        EditText ingredientText = (EditText) findViewById(R.id.txtIngredient);
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


    @Override
    public void onClick(View v) {
        chipGroup.removeView(v);
    }
    private void getRecipesUsingIngredients(String name) { //apiConnection

//        ArrayList<RecipeInformation> recipeList = new ArrayList<>();

        String url = "https://api.spoonacular.com/recipes/search?ingredients="+name+"&number=10&apiKey=7387ffbb93ed451ea993a30591711fdc";
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
//                                JSONArray ingredientArr = recipeObj.getJSONArray("missedIngredients");

                                // Add the Recipe object to the recipeList ArrayList
                                recipeList.add(recipeInformation);
                            }
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



}