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

public class SearchRecipes extends AppCompatActivity {

    private ArrayList<RecipeInformation> recipeList = new ArrayList<>();
    private ApiManager api = new ApiManager();


    private TextView txtRecipeName;
    private RecyclerView recyclerView;

    private String API_URL = "https://api.spoonacular.com/recipes/complexSearch?apiKey=7387ffbb93ed451ea993a30591711fdc&";
    private ArrayList<String> recipeNames= new ArrayList<>();
    private ArrayList<String> recipeIDs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipes);

//        tempRecipeList.add(new RecipeInformation("pasta"));
//        tempRecipeList.add(new RecipeInformation("rice"));
//        tempRecipeList.add(new RecipeInformation("soup"));
//        tempRecipeList.add(new RecipeInformation("pie"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("salad"));
//        tempRecipeList.add(new RecipeInformation("wrap"));
//        tempRecipeList.add(new RecipeInformation("steak"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("burger"));
//        tempRecipeList.add(new RecipeInformation("burger"));


//        getRecipes();



        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtRecipeName = (TextView) findViewById(R.id.txtRecipeName);


    }


    public void onBtnSearchByName_Clicked(View caller){

//        getRecipes();
        ArrayList<RecipeInformation> tempRecipeList = recipeList;


        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),tempRecipeList));

    }

    public void onBtnApplySearchParameters_Clicked(View caller){
        String recipeName = txtRecipeName.getText().toString();
        getRecipes(recipeName);
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




//    public void getRecipeByName() {
//        String url = API_URL+"query=chicken";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Handle the API response
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle API error
//                        Toast.makeText(
//                                SearchRecipes.this,
//                                "Unable to communicate with the server",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//
//        // Add the API request to the request queue
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(jsonObjectRequest);
//    }



    private void getRecipes(String name) {

//        ArrayList<RecipeInformation> recipeList = new ArrayList<>();

        String url = "https://api.spoonacular.com/recipes/search?query="+name+"&number=10&apiKey=7387ffbb93ed451ea993a30591711fdc";
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