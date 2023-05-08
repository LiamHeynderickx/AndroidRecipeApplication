package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeDisplayer extends AppCompatActivity {

    TextView lblDisplayRecipeName;
    TextView lblInformationType;
    TextView txtInformation;

    private String name;
    private String recipeID;
    private String ingredients;
    private String steps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_displayer);

        recipeID =  getIntent().getStringExtra("ID");
        name =  getIntent().getStringExtra("NAME");
        setRecipeSteps(recipeID);


//        try {
//            Thread.sleep(2000); // 2 seconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



        lblDisplayRecipeName = (TextView) findViewById(R.id.lblDisplayRecipeName);
        lblDisplayRecipeName.setText(name);
        lblInformationType = (TextView) findViewById(R.id.lblInformationType);
        txtInformation = (TextView) findViewById(R.id.txtRecipeInformation);

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




    private void setRecipeSteps(String ID) { //apiConnection

        Log.d("************************************************   ", ID);

//        ArrayList<RecipeInformation> recipeList = new ArrayList<>();

        String url = "https://api.spoonacular.com/recipes/"+ID+"/ingredientWidget.json";
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
//                                recipeInformation.setRecipeName(recipeObj.getString("title"));
//                                recipeInformation.setRecipeID(recipeObj.getString("id"));
//                                Log.d("ID ********** ", recipeInformation.getRecipeID());
//                                JSONArray ingredientArr = recipeObj.getJSONArray("missedIngredients");

                                ingredients = ingredients+recipeObj.getString("name");
                                Log.d("ing ********** ", ingredients);

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

    public void onBtnIngredients_Clicked(View caller){

        lblInformationType.setText("Ingredients:");
        txtInformation.setText(ingredients);

    }

    public void onBtnRecipeSteps_Clicked(View caller){

    }


}