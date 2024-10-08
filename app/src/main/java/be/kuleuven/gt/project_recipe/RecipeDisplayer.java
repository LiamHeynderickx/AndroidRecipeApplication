package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeDisplayer extends AppCompatActivity {

    TextView lblDisplayRecipeName;
    TextView lblInformationType;
    TextView txtInformation;

    ImageButton btnAddToFavorites;
    ImageButton btnRemoveFromFavorites;
    private String recipeName;
    private String recipeID;
    private String username;
    private String ingredients;
    private ArrayList<String> recipeIngredients = new ArrayList<>();
    private ArrayList<String>recipeQuantities = new ArrayList<>();
    private ArrayList<String>quantitiesUnit = new ArrayList<>();

    private ArrayList<String>recipeSteps = new ArrayList<>();
    private ArrayList<String>favoritesIDs = new ArrayList<>();
    private boolean isFav = false;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_displayer);
        setTitle("Recipe");
        Intent intent = getIntent();
        path = intent.getStringExtra("PATH");
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        username = prefs.getString("USERNAME", "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipeID =  getIntent().getStringExtra("ID");
        recipeName =  getIntent().getStringExtra("NAME");
        setIngrdientsNames(recipeID);
        setRecipeSteps(recipeID);

        getFavoritesID();

        lblDisplayRecipeName = (TextView) findViewById(R.id.lblDisplayRecipeName);
        lblDisplayRecipeName.setText(recipeName);
        lblInformationType = (TextView) findViewById(R.id.lblInformationType);
        txtInformation = (TextView) findViewById(R.id.txtRecipeInformation);
        txtInformation.setMovementMethod(new ScrollingMovementMethod());
        btnAddToFavorites = (ImageButton) findViewById(R.id.btnAddToFavorites);
        btnRemoveFromFavorites = (ImageButton) findViewById(R.id.btnRemoveFromFavorites);

    }


    private Intent menuOption;
    @Override //copy to each new activity
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menue_recipe_displayer, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // copy to each activity
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
                    default:
                        break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void setRecipeSteps(String ID) { //apiConnection


        String url = "https://api.spoonacular.com/recipes/"+ID+"/analyzedInstructions?apiKey=a97f080d485740608c87a17ef0957691";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONObject results = response.getJSONObject(0);
                        JSONArray steps = results.getJSONArray("steps");
                        for(int i =0;i<steps.length();i++)
                        {
                            JSONObject getStuff =steps.getJSONObject(i);
                            String step = getStuff.getString("number")+":\n"+getStuff.getString("step")+"\n\n";
                            recipeSteps.add(step);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                });
        queue.add(jsonArrayRequest);
    }
    private void setIngrdientsNames(String ID) { //apiConnection


        String url = "https://api.spoonacular.com/recipes/"+ID+"/ingredientWidget.json?apiKey=a97f080d485740608c87a17ef0957691";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray results = response.getJSONArray("ingredients");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject recipeObj = results.getJSONObject(i);

                            recipeIngredients.add(recipeObj.getString("name"));
                            JSONObject amount = recipeObj.getJSONObject("amount");
                            JSONObject metric = amount.getJSONObject("metric");

                            recipeQuantities.add(String.valueOf(metric.getDouble("value")));
                            quantitiesUnit.add(metric.getString("unit"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                });

        queue.add(jsonObjectRequest);
    }

    public void onBtnIngredients_Clicked(View caller){
        String s= "";
        lblInformationType.setText("Ingredients:");
        for(int i=0;i<recipeIngredients.size();i++)
        {
            s+= recipeIngredients.get(i)+ ", Quantity = "+recipeQuantities.get(i)+" "+quantitiesUnit.get(i) +"\n";
        }
        txtInformation.setText(s);
    }

    public void onBtnRecipeSteps_Clicked(View caller){

        String txt = "";
        lblInformationType.setText("Steps:");
        for(int loop = 0; loop<recipeSteps.size(); loop++){
            txt += recipeSteps.get(loop);
        }
        txtInformation.setText(txt);
    }

    public void onBtnAddToFavorites_Clicked(View caller){
        btnAddToFavorites.setVisibility(View.GONE);
        btnRemoveFromFavorites.setVisibility(View.VISIBLE);
        String Que_URL = "https://studev.groept.be/api/a22pt409/insertFavorites/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest submitRequest = new StringRequest(
                Request.Method.POST,
                Que_URL,
                response -> Toast.makeText(
                        RecipeDisplayer.this,
                        "Added to Favorites",
                        Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(
                        RecipeDisplayer.this,
                        "Unable to place the order" + error,
                        Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                return getPostParameters();
            }
        };
        requestQueue.add(submitRequest);
    }

    private Map<String, String> getPostParameters() {

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("recipeid", recipeID);
        params.put("recipename", recipeName);

        return params;
    }


    public void onBtnRemoveFromFavorites_Clicked(View caller){
        btnRemoveFromFavorites.setVisibility(View.GONE);
        btnAddToFavorites.setVisibility(View.VISIBLE);
        String url = "https://studev.groept.be/api/a22pt409/removeFavorites/";
        url += username + "/"+recipeID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> Toast.makeText(
                        RecipeDisplayer.this,
                        "Removed from favorites",
                        Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(
                        RecipeDisplayer.this,
                        "Unable to communicate with the server",
                        Toast.LENGTH_LONG).show());
        requestQueue.add(queueRequest);

    }

    private void getFavoritesID(){
        //connect to database of favorites
        String QUEUE_URL = "https://studev.groept.be/api/a22pt409/getFavorites/"+username;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                QUEUE_URL,
                null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject curObject = response.getJSONObject(i);
                            String currRecipeID = curObject.getString("recipeId");
                            favoritesIDs.add(currRecipeID);
                        }
                        checkIfInFavorites(favoritesIDs);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(
                        RecipeDisplayer.this,
                        "Unable to communicate with the server",
                        Toast.LENGTH_LONG).show());
        requestQueue.add(queueRequest);
    }

    private void checkIfInFavorites(ArrayList<String> favoritesIDs) {
        if(favoritesIDs.contains(recipeID)){
            btnRemoveFromFavorites.setVisibility(View.VISIBLE);
        }
        else {
            btnAddToFavorites.setVisibility(View.VISIBLE);
        }
    }


}