package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity implements RecyclerViewInterface{


    private ArrayList<RecipeInformation> recipeList = new ArrayList<>();
    private ArrayList<String>recipeIdList = new ArrayList<>();
    private ApiManager api = new ApiManager();
    private MyAdapter myAdapter = new MyAdapter(this, recipeList, this);
    private RecyclerView recyclerView;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        setTitle("Favorites");
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myAdapter);
        getFavorites();
      //  recyclerView.setAdapter(new MyAdapter(getApplicationContext(),recipeList, this));

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

    public void getFavorites(){
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
                            String currRecipeName = curObject.getString("recipeName");
                            RecipeInformation ri = new RecipeInformation();
                            ri.setRecipeName(currRecipeName);
                            ri.setRecipeID(currRecipeID);
                            recipeList.add(ri);
                        }
                        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),recipeList, this));

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(
                        Favorites.this,
                        "Unable to communicate with the server",
                        Toast.LENGTH_LONG).show());
        requestQueue.add(queueRequest);
        //temporary code:
//        RecipeInformation ri = new RecipeInformation();
//        ri.setRecipeName("Easy Homemade Rice and Beans");
//        ri.setRecipeID("716627");
//        recipeList.add(ri);
    }

    @Override
    public void onRecyclerViewItemClick(int position) {

        Intent intent = new Intent(this, RecipeDisplayer.class);

        intent.putExtra("ID", recipeList.get(position).getRecipeID());
        intent.putExtra("NAME", recipeList.get(position).getRecipeName());

        startActivity(intent);

    }
}