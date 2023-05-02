package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SearchByIngredients extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<String> listIngredients = new ArrayList<>();

    private Button btnAdd;
    private ChipGroup chipGroup;
    private TextView txtNewIngredient;
    private String newIngredient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_ingredients);
        btnAdd = (Button) findViewById(R.id.btnAddIngredient);
        chipGroup = (ChipGroup) findViewById(R.id.cgIngredients);
        txtNewIngredient = (TextView)findViewById(R.id.txtIngredient);

    }

    // ON CLICK METHODS:

    public void onBtnAdd_clicked(View Caller){

        newIngredient = txtNewIngredient.getText().toString();

        if(!newIngredient.isEmpty()){
            Chip chip = new Chip(this);
            chip.setText(newIngredient);
            chip.setCloseIconVisible(true);
            chip.setId(ViewCompat.generateViewId());
            chip.setOnClickListener(this);
            chipGroup.addView(chip);
            txtNewIngredient.setText("");
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


}