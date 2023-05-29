package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchByIngredients extends AppCompatActivity implements View.OnClickListener, RecyclerViewInterface {


    private ArrayList<String> listIngredients = new ArrayList<>();
    private ArrayList<RecipeInformation> recipeList = new ArrayList<>();

    private MyAdapter myAdapter = new MyAdapter(this, recipeList, this);
    private Button btnAdd;
    private ChipGroup chipGroup;
    private TextView txtNewIngredient;
    private String newIngredient;
    private RecyclerView recyclerView2;
    private String fullIngredientsQrl="";
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_ingredients);
        setTitle("Search By Ingredients");
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        username = prefs.getString("USERNAME", "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        ArrayList<RecipeInformation> tempRecipeList = recipeList;

        recyclerView2.setAdapter(new MyAdapter(getApplicationContext(),tempRecipeList, this));

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


            if(fullIngredientsQrl == "" )
            {
                fullIngredientsQrl += newIngredient;
            }
            else {

                fullIngredientsQrl += ",+" + newIngredient;
            }


        }
        txtNewIngredient.setText("");
        recipeList.clear();
        getRecipesUsingIngredients(fullIngredientsQrl);

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
                menuOption = new Intent(this, MainActivity.class);
                startActivity(menuOption);
                //onBackPressed();
                return true;
            case R.id.menuProgramFlow:
                menuOption = new Intent(this, ProgramFlow.class);
                menuOption.putExtra("PATH", "INGREDIENTS");
                startActivity(menuOption);
                finish();
                return true;
            case R.id.menuUMLDiagram:
                menuOption = new Intent(this, UML.class);
                menuOption.putExtra("PATH", "INGREDIENTS");
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

    @Override
    public void onClick(View v) {
        String nameRemove = ((Chip) v).getText().toString();
        while(fullIngredientsQrl.contains(nameRemove)){
            fullIngredientsQrl = fullIngredientsQrl.replaceAll(nameRemove, "");
            if(fullIngredientsQrl.contains(",+")){
                fullIngredientsQrl = fullIngredientsQrl.replaceAll(",+", "");
            }
        }
        recipeList.clear();
        getRecipesUsingIngredients(fullIngredientsQrl);
        chipGroup.removeView(v);
    }


    private void getRecipesUsingIngredients(String name) { //apiConnection

        String url = "https://api.spoonacular.com/recipes/complexSearch?apiKey=a97f080d485740608c87a17ef0957691&includeIngredients="+name;
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
                                // Add the Recipe object to the recipeList ArrayList
                                recipeList.add(recipeInformation);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
                    // Handle API error
                });
        // Add the API request to the request queue
        queue.add(jsonObjectRequest);
    }


    @Override
    public void onRecyclerViewItemClick(int position) {

        Intent intent = new Intent(this, RecipeDisplayer.class);

        intent.putExtra("ID", recipeList.get(position).getRecipeID());
        intent.putExtra("NAME", recipeList.get(position).getRecipeName());
        intent.putExtra("USERNAME",username);
        intent.putExtra("PATH", "INGREDIENTS");

        startActivity(intent);

    }
}