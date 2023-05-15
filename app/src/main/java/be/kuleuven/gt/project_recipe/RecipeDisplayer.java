package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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

import java.util.ArrayList;

public class RecipeDisplayer extends AppCompatActivity {

    TextView lblDisplayRecipeName;
    TextView lblInformationType;
    TextView txtInformation;

    ImageButton btnToggleFavorites;

    private String name;
    private String recipeID;
    private String ingredients;
    private ArrayList<String> recipeIngredients = new ArrayList<>();
    private ArrayList<String>recipeQuantities = new ArrayList<>();
    private ArrayList<String>quantitiesUnit = new ArrayList<>();

    private ArrayList<String>recipeSteps = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_displayer);
        setTitle("Recipe");

        recipeID =  getIntent().getStringExtra("ID");
        name =  getIntent().getStringExtra("NAME");
        //setRecipeSteps(recipeID);
        setIngrdientsNames(recipeID);
        setRecipeSteps(recipeID);

//        checkIfFavorite();


//        try {
//            Thread.sleep(2000); // 2 seconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



        lblDisplayRecipeName = (TextView) findViewById(R.id.lblDisplayRecipeName);
        lblDisplayRecipeName.setText(name);
        lblInformationType = (TextView) findViewById(R.id.lblInformationType);
        txtInformation = (TextView) findViewById(R.id.txtRecipeInformation);
        txtInformation.setMovementMethod(new ScrollingMovementMethod());
        btnToggleFavorites = (ImageButton) findViewById(R.id.btnToggleFavorites);

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
            case R.id.btn_back:
                menuOption = new Intent(this,MainActivity.class);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void setRecipeSteps(String ID) { //apiConnection

//        Log.d("************************************************   ", ID);

//        ArrayList<RecipeInformation> recipeList = new ArrayList<>();

        String url = "https://api.spoonacular.com/recipes/"+ID+"/analyzedInstructions?apiKey=a97f080d485740608c87a17ef0957691";

//        Log.d(url, "****************");

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject results = response.getJSONObject(0);
                            JSONArray steps = results.getJSONArray("steps");
                            for(int i =0;i<steps.length();i++)
                            {
                                JSONObject getStuff =steps.getJSONObject(i);
                                String step = getStuff.getString("number")+":\n"+getStuff.getString("step")+"\n\n";
                                recipeSteps.add(step);

                               // Log.d("step ********** ", step);

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

                            // Create a new Recipe object and populate its properties
                            recipeIngredients.add(recipeObj.getString("name"));
                            JSONObject amount = recipeObj.getJSONObject("amount");
                            JSONObject metric = amount.getJSONObject("metric");

                            recipeQuantities.add(String.valueOf(metric.getDouble("value")));
                            quantitiesUnit.add(metric.getString("unit"));

//                            Log.d("abc",recipeObj.getString("name") + metric.getString("unit"));


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // Handle API error
                });

        // Add the API request to the request queue

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
//        Log.d("YALLLLLLLLLLLLLAA", s );


    }

    public void onBtnRecipeSteps_Clicked(View caller){

        String txt = "";
        lblInformationType.setText("Steps:");
        for(int loop = 0; loop<recipeSteps.size(); loop++){
            txt += recipeSteps.get(loop);
        }
        txtInformation.setText(txt);
    }

    public void onBtnToggleFavorites_Clicked(View caller){



    }


}